package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.task.Task;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class SnoozeCommand extends Command {
    private static final String SUPPLY_NUMBER_PROMPT = "Please supply a number. Eg: done 2";
    private static final String SUPPLY_VALID_NUMBER_PROMPT = "Please supply a valid number.";
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
        } catch (NumberFormatException e) {
            throw new DuchessException(SUPPLY_NUMBER_PROMPT);
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException(SUPPLY_VALID_NUMBER_PROMPT);
        }
    }
}
