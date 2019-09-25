package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.storage.task.TaskList;
import duchess.storage.task.Todo;
import duchess.storage.task.Task;
import duchess.logic.commands.exceptions.DukeException;
import duchess.ui.Ui;

import java.util.List;

public class AddTodoCommand extends Command {
    private List<String> words;

    public AddTodoCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task task = new Todo(words.subList(0, words.size()));
        taskList.add(task);
        ui.showTaskAdded(taskList.getTasks(), task);
        storage.save(taskList);
    }
}
