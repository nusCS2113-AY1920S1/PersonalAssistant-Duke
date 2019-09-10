package duke.command;

import duke.task.TaskList;
import duke.task.Task;
import duke.task.Deadline;
import duke.dukeexception.DukeException;
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
