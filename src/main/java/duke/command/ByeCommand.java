package duke.command;

import duke.DukeContext;
import duke.exception.DukeFatalException;

public class ByeCommand extends Command {
    @Override
    public void execute(DukeContext ctx) throws DukeFatalException {
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.closeUi();
        System.exit(0);
    }
}
