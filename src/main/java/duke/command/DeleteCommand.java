package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

public class DeleteCommand extends ArgCommand {

    public DeleteCommand() {
        emptyArgMsg = "You didn't tell me which task to delete!";
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        String delStr = ctx.taskList.deleteTask(arg);
        ctx.storage.writeTaskFile(ctx.taskList.getFileStr());
        ctx.ui.print(ctx.taskList.getDelReport(System.lineSeparator() + "  " + delStr, 1));
    }
}
