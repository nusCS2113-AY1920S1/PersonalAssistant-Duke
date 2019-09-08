package duke.command;

import duke.task.TaskList;
import duke.task.Task;
import duke.dukeexception.DukeException;

import java.util.List;

public class DeleteCommand extends Command {
    private List<String> words;

    public DeleteCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try {
            int taskNo = Integer.parseInt(words.get(0)) - 1;
            Task toRemove = taskList.get(taskNo);
            taskList.delete(taskNo);
            ui.showDeletedTask(taskList.getTasks(), toRemove);
            storage.save(taskList);
        } catch (NumberFormatException e) {
            throw new DukeException("Please supply a number. Eg: done 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please supply a valid number.");
        }

    }
}
