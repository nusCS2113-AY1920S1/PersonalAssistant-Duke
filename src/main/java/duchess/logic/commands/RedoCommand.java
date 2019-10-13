package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class RedoCommand extends Command {

    private int redoCounter;

    /**
     * Checks if undo command contains additional parameters.
     *
     * @param words additional parameters for undo
     * @throws DuchessException throws exceptions if invalid command
     */
    public RedoCommand(List<String> words) throws DuchessException {
        if (words.size() != 1 && words.size() != 0) {
            throw new DuchessException("Usage: redo [number]");
        } else if (words.size() == 1) {
            redoCounter = Integer.parseInt(words.get(0));
        } else if (words.size() == 0) {
            redoCounter = 1;
        }
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        if (redoCounter > 1) {
            // storage.getFirstSnapshot();
            while (redoCounter > 0 && storage.getRedoStack().size() > 0) {
                setToNextStore(store, storage);
                redoCounter--;
            }
        } else {
            // storage.getFirstSnapshot();
            setToNextStore(store, storage);
        }
        // showUndo should only be placed after execution of undo.
        ui.showRedo(redoCounter);
    }

    private void setToNextStore(Store store, Storage storage) throws DuchessException {
        // Obtain Store data from storage Stack
        Store prevStore = storage.getFirstSnapshot();
        storage.save(prevStore);

        // Obtaining store from stack
        Store newStore = storage.load();
        store.setTaskList(newStore.getTaskList());
        store.setModuleList(newStore.getModuleList());
        // ui.showTaskList(store.getTaskList());
    }
}
