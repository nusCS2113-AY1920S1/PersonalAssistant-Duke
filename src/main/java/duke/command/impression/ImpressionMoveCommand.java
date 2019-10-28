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

        String evArg = getSwitchVal("evidence");
        String treatArg = getSwitchVal("treatment");
        DukeData moveData;
        if (!"".equals(getArg()) && "".equals(evArg) && "".equals(treatArg)) {
            List<DukeData> moveList = impression.findByName(getArg());
            if (moveList.size() != 0) {
                moveData = moveList.get(0);
            } else {
                throw new DukeException("Can't find any data item with that name!");
            }
        } else if ("".equals(getArg()) && !"".equals(evArg) && "".equals(treatArg)) {
            List<Evidence> moveList = impression.findEvidencesByName(evArg);
            if (moveList.size() != 0) {
                moveData = moveList.get(0);
            } else {
                throw new DukeException("Can't find any evidences with that name!");
            }
        } else if ("".equals(getArg()) && "".equals(evArg) && !"".equals(treatArg)) {
            List<Treatment> moveList = impression.findTreatmentsByName(treatArg);
            if (moveList.size() != 0) {
                moveData = moveList.get(0);
            } else {
                throw new DukeException("Can't find any treatments with that name!");
            }
        } else {
            throw new DukeHelpException("I don't know what you want me to look for!", this);
        }
        moveData.setParent(newImpression);
    }
}
