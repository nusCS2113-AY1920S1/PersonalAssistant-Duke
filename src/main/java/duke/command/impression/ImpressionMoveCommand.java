package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.ArrayList;
import java.util.List;

public class ImpressionMoveCommand extends ImpressionCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionMoveSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        // TODO: query user for correct impression if no impression is given
        Impression impression = getImpression(core);
        String newImpressionName = getSwitchVal("impression");
        Impression newImpression;
        if ("".equals(newImpressionName)) {
            // ask user to pick
            newImpression = null;
        } else {
            // TODO: proper search
            List<Impression> newImpressionList = ((Patient) impression.getParent())
                    .findImpressionsByName(newImpressionName);
            if (newImpressionList.size() == 0) {
                throw new DukeException("Can't find an impression with that name!");
            }
            newImpression = newImpressionList.get(0);
        }

        DukeData moveData = findDataByName(getArg(), getSwitchVal("evidence"),
                getSwitchVal("treatment"), getImpression(core));

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
    }
}
