package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.model.task.TaskList;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.Task;
import duchess.ui.Ui;

import java.util.List;

public class DoneCommand extends Command {
    private List<String> words;

    public DoneCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try {
            int taskNo = Integer.parseInt(words.get(0)) - 1;
            Task task = taskList.get(taskNo);
            task.markAsDone();
            ui.showDoneTask(task);
            storage.save(taskList);
        } catch (NumberFormatException e) {
            throw new DukeException("Please supply a number. Eg: done 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please supply a valid number.");
        }
    }
}
