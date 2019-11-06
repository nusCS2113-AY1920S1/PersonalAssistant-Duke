package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

/**
 * Redo feature.
 */
public class RedoCommand extends Command {
    private int redoCounter;
    private static final String REDO_USAGE_ERROR_MESSAGE = "Usage: redo [number]";
    private static final String NEGATIVE_NUMBER_ERROR_MESSAGE
            = "[number] must be a positive integer, i.e. 1, 2, 3, ...";
    private static final String INVALID_NUMBER_ERROR_MESSAGE
            = "You have entered an invalid value.";

    /**
     * Checks if redo command contains additional parameters.
     *
     * @param words additional parameters for redo
     * @throws DuchessException if invalid command
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

    /**
     * Restores previous undo command user executes.
     *
     * @param store store object
     * @param ui user interaction object
     * @param storage storage object
     * @throws DuchessException if redo is unsuccessful
     */
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

    /**
     * Updates data to previous Store values.
     *
     * @param store store object
     * @param storage storage object
     * @throws DuchessException if updating store is unsuccessful
     */
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
