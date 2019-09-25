package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.logic.commands.exceptions.DukeException;
import duchess.storage.task.TaskList;
import duchess.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ui.showTaskList(taskList.getTasks());
    }
}
