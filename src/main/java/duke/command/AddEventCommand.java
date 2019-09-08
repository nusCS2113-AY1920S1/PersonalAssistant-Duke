package duke.command;

import duke.dukeexception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Event;
import java.util.List;

public class AddEventCommand extends Command {
    private List<String> words;

    public AddEventCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Task task = new Event(words.subList(0, words.size()));
        taskList.add(task);
        ui.showTaskAdded(taskList.getTasks(), task);
        storage.save(taskList);
    }
}