package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

public class ListCommand extends Command {

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        String listStr = "Here are the tasks in your list:";
        listStr = (listStr + ctx.taskList.listTasks()).replace(System.lineSeparator(),
                System.lineSeparator() + "  ");
        ctx.ui.print(listStr);
    }
}
