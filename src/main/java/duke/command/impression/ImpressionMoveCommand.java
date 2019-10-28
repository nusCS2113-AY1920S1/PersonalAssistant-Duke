package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgSpec;
import duke.data.Impression;
import duke.data.Patient;
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
        String evArg = getSwitchVal("evidence");
        String treatArg = getSwitchVal("treatment");
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
                throw new DukeException("Can't find an impression of that name!");
            }
            newImpression = newImpressionList.get(0);
        }

        if (getArg() != null && evArg == null && treatArg == null) {

        } else if (getArg() == null && evArg != null && treatArg == null) {

        } else if (getArg() == null && evArg == null && treatArg != null) {

        } else {
            throw new DukeHelpException("I don't know what you want me to look for!", this);
        }
    }
}
