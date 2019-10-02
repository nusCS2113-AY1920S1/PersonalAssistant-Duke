package duchess.logic.commands;

import duchess.storage.Storage;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.task.Event;
import duchess.model.task.TaskList;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class AddEventCommand extends Command {
    private List<String> words;

    public AddEventCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DukeException {
        Event task = new Event(words.subList(0, words.size()));
        if (store.isClashing(task)) {
            throw new DukeException("Unable to add event - clash found.");
        }
        store.getTaskList().add(task);
        ui.showTaskAdded(store.getTaskList(), task);
        storage.save(store);
    }
}