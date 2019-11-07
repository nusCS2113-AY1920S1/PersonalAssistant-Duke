package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Treatment;
import duke.exception.DukeException;

import java.util.List;

public class ImpressionMoveSpec extends ObjSpec {
    private static final ImpressionMoveSpec spec = new ImpressionMoveSpec();

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
        Impression newImpression;
        if ("".equals(targetImpressionName)) {
            // TODO ask user to pick
            newImpression = null;
        } else {
            // TODO: proper search
            List<Impression> newImpressionList = ImpressionUtils.getPatient(impression)
                    .findImpressionsByName(targetImpressionName);
            if (newImpressionList.size() == 0) {
                throw new DukeException("Can't find an impression with that name!");
            }
            newImpression = newImpressionList.get(0);
        }

        DukeData moveData = ImpressionUtils.getData(cmd.getArg(), cmd.getSwitchVal("evidence"),
                cmd.getSwitchVal("treatment"), ImpressionUtils.getImpression(core));

        moveData.setParent(newImpression);
        if (moveData instanceof Evidence) {
            Evidence evidence = (Evidence) moveData;
            newImpression.addNewEvidence(evidence);
            impression.deleteEvidence(evidence.getName());
        } else if (moveData instanceof Treatment) {
            Treatment treatment = (Treatment) moveData;
            newImpression.addNewTreatment(treatment);
            impression.deleteTreatment(treatment.getName());
        }

        core.updateUi("'" + moveData.getName() + "' moved from '" + impression.getName() + "' to '"
                + newImpression.getName() + "'");
    }
}
