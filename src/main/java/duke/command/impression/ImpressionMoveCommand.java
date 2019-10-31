package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Treatment;
import duke.exception.DukeException;

import java.util.List;

public class ImpressionMoveCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionMoveSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        // TODO: query user for correct impression if no impression is given
        Impression impression = ImpressionUtils.getImpression(core);
        String targetImpressionName = getSwitchVal("impression");
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

        DukeData moveData = ImpressionUtils.getData(getArg(), getSwitchVal("evidence"),
                getSwitchVal("treatment"), ImpressionUtils.getImpression(core));

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
