package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class UpCommand extends Command {

    @Override
    public void execute(DukeCore core) throws DukeException {
        core.uiContext.moveUpOneContext();
    }
}
