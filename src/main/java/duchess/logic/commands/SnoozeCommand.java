package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.Task;
import duchess.model.task.TaskList;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class SnoozeCommand extends Command {
    private List<String> words;

    public SnoozeCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DukeException {
        try {
            int taskNo = Integer.parseInt(words.get(0)) - 1;
            Task task = store.getTaskList().get(taskNo);

            task.snooze();
            ui.showSnoozedTask(task);
            storage.save(store);
        } catch (NumberFormatException e) {
            throw new DukeException("Please supply a number. Eg: done 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please supply a valid number.");
        }
    }
}
