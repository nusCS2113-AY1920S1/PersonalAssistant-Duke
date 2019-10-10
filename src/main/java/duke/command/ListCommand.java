package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class ListCommand extends Command {

    @Override
    public void execute(DukeCore core) throws DukeException {
        String listStr = "Here are the tasks in your list:";
        listStr = (listStr + core.taskList.listTasks()).replace(System.lineSeparator(),
                System.lineSeparator() + "  ");
        core.ui.print(listStr);
    }
}
