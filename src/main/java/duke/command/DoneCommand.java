package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

public class DoneCommand extends ArgCommand {
    @Override
    public void execute(DukeContext ctx) throws DukeException {
        String taskStr = ctx.taskList.markDone(arg);
        taskStr = "Nice! I've marked this task as done:" + System.lineSeparator() + "  " + taskStr;
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.print(taskStr);
    }
}
