package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class SnoozeCommand extends Command {
    private List<String> words;

    public SnoozeCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        try {
            int taskNo = Integer.parseInt(words.get(0)) - 1;
            Task task = store.getTaskList().get(taskNo);

            task.snooze();
            ui.showSnoozedTask(task);
            storage.save(store);

            storage.setPreviousUndoFalse();
        } catch (NumberFormatException e) {
            throw new DuchessException("Please supply a number. Eg: done 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException("Please supply a valid number.");
        }
    }
}
