package duke.command;

import duke.DukeCore;
import duke.exception.DukeFatalException;

public class ByeCommand extends Command {
    @Override
    public void execute(DukeCore core) throws DukeFatalException {
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.stop();
    }
}
