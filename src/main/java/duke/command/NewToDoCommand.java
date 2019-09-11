package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;
import duke.task.ToDoTask;

public class NewToDoCommand extends ArgCommand {
    public NewToDoCommand() {
        emptyArgMsg = "Task description cannot be empty!";
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        super.execute(ctx);
        String addStr = ctx.taskList.addTask(new ToDoTask(arg));
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.print(addStr);
    }
}
