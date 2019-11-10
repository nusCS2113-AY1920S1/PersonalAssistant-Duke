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

import java.util.List;

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
        DukeData data;
        Impression currImpression = ImpressionUtils.getImpression(core);
        if (newImpression == null) { // impression has not been identified
            newImpression = (Impression) obj;
            data = ImpressionUtils.getDataByIdx(cmd.getArg(), cmd.getSwitchVal("evidence"),
                    cmd.getSwitchVal("treatment"), currImpression);

            if (data == null) { // data could not be identified unambiguously
                SearchResults results = ImpressionUtils.searchData(cmd.getArg(), cmd.getSwitchVal("evidence"),
                        cmd.getSwitchVal("treatment"), ImpressionUtils.getImpression(core));
                List<DukeObject> resultList = results.getSearchList();
                int currImpIdx = resultList.indexOf(currImpression);
                if (currImpIdx != -1) { // remove this impression from the list if present
                    resultList.remove(currImpIdx);
                    results = new SearchResults(results.getName(), resultList, results.getParent());
                }
                if (results.getCount() == 0) {
                    throw new DukeException("No results found for '" + results.getName() + "'!");
                } else if (results.getCount() == 1) {
                    executeWithObj(core, results.getResult(0));
                } else {
                    core.search(results, cmd);
                }
                return;
            }
        } else {
            data = (DukeData) obj;
        }

        if (data instanceof Evidence) {
            Evidence evidence = (Evidence) data;
            newImpression.addNewEvidence(evidence);
            currImpression.deleteEvidence(evidence.getName());
        } else if (data instanceof Treatment) {
            Treatment treatment = (Treatment) data;
            newImpression.addNewTreatment(treatment);
            currImpression.deleteTreatment(treatment.getName());
        }
        data.setParent(newImpression);
        core.writeJsonFile();
        core.updateUi("'" + data.getName() + "' moved from '" + currImpression.getName() + "' to '"
                + newImpression.getName() + "'");
        newImpression = null;
    }
}
