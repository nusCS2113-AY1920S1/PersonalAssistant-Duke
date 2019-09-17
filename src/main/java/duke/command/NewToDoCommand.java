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
        addStr = "Got it, I've added this task:" + System.lineSeparator() + "  " + addStr + System.lineSeparator();
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.print(addStr);
    }
}
