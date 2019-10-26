package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class RedoCommand extends Command {
    private int redoCounter;
    private static final String REDO_USAGE_ERROR_MESSAGE = "Usage: redo [number]";
    private static final String NEGATIVE_NUMBER_ERROR_MESSAGE
            = "[number] must be a positive integer, i.e. 1, 2, 3, ...";
    private static final String INVALID_NUMBER_ERROR_MESSAGE
            = "You have entered an invalid value.";

    /**
     * Checks if undo command contains additional parameters.
     *
     * @param words additional parameters for undo
     * @throws DuchessException throws exceptions if invalid command
     */
    public RedoCommand(List<String> words) throws DuchessException {
        if (words.size() != 1 && words.size() != 0) {
            throw new DuchessException(REDO_USAGE_ERROR_MESSAGE);
        } else if (words.size() == 1) {
            try {
                redoCounter = Integer.parseInt(words.get(0));

                if (redoCounter <= 0) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                throw new DuchessException(INVALID_NUMBER_ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                throw new DuchessException(NEGATIVE_NUMBER_ERROR_MESSAGE);
            }
        } else if (words.size() == 0) {
            redoCounter = 1;
        }
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        if (storage.getRedoStack().size() == 0) {
            redoCounter = 0;
        } else if (storage.getRedoStack().size() > 0 && redoCounter > 1) {
            int tempCounter = redoCounter;
            while (tempCounter > 0 && storage.getRedoStack().size() > 0) {
                setToNextStore(store, storage);
                tempCounter--;
            }
        } else if (storage.getRedoStack().size() > 0 && redoCounter == 1) {
            setToNextStore(store, storage);
        }
        // showUndo should only be placed after execution of undo.
        ui.showRedo(redoCounter);
    }

    private void setToNextStore(Store store, Storage storage) throws DuchessException {
        // Obtain Store data from duchess.storage Stack
        Store prevStore = storage.getFirstSnapshot();
        storage.save(prevStore);

        // Obtaining store from stack
        Store newStore = storage.load();
        store.setTaskList(newStore.getTaskList());
        store.setModuleList(newStore.getModuleList());
        store.setDuchessCalendar(newStore.getDuchessCalendar());
    }
}
