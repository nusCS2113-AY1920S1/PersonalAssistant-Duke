package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgSpec;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

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
        // TODO: proper search
        if (getArg() != null && evArg == null && treatArg == null) {

        } else if (getArg() == null && evArg != null && treatArg == null) {

        } else if (getArg() == null && evArg == null && treatArg != null) {

        } else {
            throw new DukeHelpException("I don't know what you want me to look for!", this);
        }
    }
}
