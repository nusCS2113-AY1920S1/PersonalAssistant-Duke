package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

public class DeleteCommand extends ArgCommand {

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        String taskStr = ctx.taskList.deleteTask(arg);
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.print(taskStr);
    }
}
