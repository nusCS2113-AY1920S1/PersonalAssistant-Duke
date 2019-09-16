package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

public class DeleteCommand extends ArgCommand {

    public DeleteCommand() {
        emptyArgMsg = "You didn't tell me which task to delete!";
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        String taskStr = ctx.taskList.deleteTask(arg);
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.print(taskStr);
    }
}
