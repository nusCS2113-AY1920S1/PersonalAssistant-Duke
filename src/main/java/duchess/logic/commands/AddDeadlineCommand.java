package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.storage.task.TaskList;
import duchess.storage.task.Task;
import duchess.storage.task.Deadline;
import duchess.logic.commands.exceptions.DukeException;
import duchess.ui.Ui;

import java.util.List;

public class AddDeadlineCommand extends Command {
    /** List containing String objects. */
    private List<String> words;

    public AddDeadlineCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task task = new Deadline(words.subList(0, words.size()));
        taskList.add(task);
        ui.showTaskAdded(taskList.getTasks(), task);
        storage.save(taskList);
    }
}
