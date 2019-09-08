package duke.command;

import duke.task.Task;
import duke.dukeexception.DukeException;
import duke.task.TaskList;

import java.util.List;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ui.showTaskList((List<Task>) taskList.getTasks());
    }
}
