package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

public class ListCommand extends Command {

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        String listStr = "Here are the tasks in your list:";
        ctx.ui.print(listStr +  ctx.taskList.listTasks());
    }
}
