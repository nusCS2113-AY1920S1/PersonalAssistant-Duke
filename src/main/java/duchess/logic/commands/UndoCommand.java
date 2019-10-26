package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.List;

public class UndoCommand extends Command {
    private int undoCounter;
    private static final String UNDO_USAGE_ERROR_MESSAGE = "Usage: undo [number]";
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
    public UndoCommand(List<String> words) throws DuchessException {
        if (words.size() != 1 && words.size() != 0) {
            throw new DuchessException(UNDO_USAGE_ERROR_MESSAGE);
        } else if (words.size() == 1) {
            try {
                undoCounter = Integer.parseInt(words.get(0));

                if (undoCounter <= 0) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                throw new DuchessException(INVALID_NUMBER_ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                throw new DuchessException(NEGATIVE_NUMBER_ERROR_MESSAGE);
            }

        } else if (words.size() == 0) {
            undoCounter = 1;
        }
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        if (storage.getUndoStack().size() == 1) {
            undoCounter = 0;
        } else if (storage.getUndoStack().size() > 1 && undoCounter > 1) {
            storage.addToRedoStack();
            int tempCounter = undoCounter;
            while (tempCounter > 0 && storage.getUndoStack().size() > 1) {
                setToPreviousStore(store, storage);
                tempCounter--;
            }
        } else if (storage.getUndoStack().size() > 1 && undoCounter == 1) {
            storage.addToRedoStack();
            setToPreviousStore(store, storage);
        }
        // showUndo should only be placed after execution of undo.
        ui.showUndo(undoCounter);
    }

    private void setToPreviousStore(Store store, Storage storage) throws DuchessException {
        storage.getLastSnapshot();
        storage.save(storage.peekUndoStackAsStore());

        // Obtaining store from stack
        Store newStore = storage.load();
        assert (store.equals(newStore));
        store.setTaskList(newStore.getTaskList());
        store.setModuleList(newStore.getModuleList());
        store.setDuchessCalendar(newStore.getDuchessCalendar());
    }
}