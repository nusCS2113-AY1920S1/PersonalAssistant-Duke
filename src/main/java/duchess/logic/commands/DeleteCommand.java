package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.model.task.TaskList;
import duchess.model.task.Task;
import duchess.logic.commands.exceptions.DukeException;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class DeleteCommand extends Command {
    private List<String> words;

    public DeleteCommand(List<String> words) {
        this.words = words;
    }

    /**
     * Deletes a user specified task.
     *
     * @param store the store
     * @param ui Userinterface object
     * @param storage Storage object
     * @throws DukeException Exception thrown when errors besides invalid format and index are found
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DukeException {
        try {
            int taskNo = Integer.parseInt(words.get(0)) - 1;
            Task toRemove = store.getTaskList().get(taskNo);
            store.getTaskList().remove(taskNo);
            ui.showDeletedTask(store.getTaskList(), toRemove);
            storage.save(store);
        } catch (NumberFormatException e) {
            throw new DukeException("Please supply a number. Eg: done 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please supply a valid number.");
        }

    }
}
