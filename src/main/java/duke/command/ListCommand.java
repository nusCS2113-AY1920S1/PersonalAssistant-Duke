package duke.command;

import duke.dukeexception.DukeException;
import duke.task.TaskList;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ui.showTaskList(taskList.getTasks());
    }
}
