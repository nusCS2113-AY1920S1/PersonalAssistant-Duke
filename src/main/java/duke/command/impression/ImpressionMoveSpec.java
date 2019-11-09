package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeData;
import duke.data.DukeObject;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.data.Treatment;
import duke.exception.DukeException;

public class ImpressionMoveSpec extends ObjSpec {
    private static final ImpressionMoveSpec spec = new ImpressionMoveSpec();
    private Impression newImpression = null;

    public static ImpressionMoveSpec getSpec() {
        return spec;
    }

    private ImpressionMoveSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t"),
                new Switch("impression", String.class, true, ArgLevel.REQUIRED, "im")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        // TODO: query user for correct impression if no impression is given
        Impression impression = ImpressionUtils.getImpression(core);
        String targetImpressionName = cmd.getSwitchVal("impression");
        Patient patient = impression.getParent();
        SearchResults results = patient.findImpressionsByName(targetImpressionName);
        processResults(core, results);
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        DukeData moveData;
        Impression currImpression = ImpressionUtils.getImpression(core);
        if (newImpression == null) {
            newImpression = (Impression) obj;
            moveData = ImpressionUtils.getDataByIdx(cmd.getArg(), cmd.getSwitchVal("evidence"),
                    cmd.getSwitchVal("treatment"), currImpression);

            if (moveData == null) {
                SearchResults results = ImpressionUtils.searchData(cmd.getArg(), cmd.getSwitchVal("evidence"),
                        cmd.getSwitchVal("treatment"), ImpressionUtils.getImpression(core));
                processResults(core, results);
            }
        } else {
            moveData = (DukeData) obj;
            moveData.setParent(newImpression);
            if (moveData instanceof Evidence) {
                Evidence evidence = (Evidence) moveData;
                newImpression.addNewEvidence(evidence);
                currImpression.deleteEvidence(evidence.getName());
            } else if (moveData instanceof Treatment) {
                Treatment treatment = (Treatment) moveData;
                newImpression.addNewTreatment(treatment);
                currImpression.deleteTreatment(treatment.getName());
            }
            core.writeJsonFile();
            core.updateUi("'" + moveData.getName() + "' moved from '" + currImpression.getName() + "' to '"
                    + newImpression.getName() + "'");
            newImpression = null;
        }
    }
}
