package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.SongList;
import ducats.components.UndoRedoStack;

public class RedoCommand extends Command {

    /**
     * A null method that will never be used, the reason for this method is for inheritance.
     *
     * @param object the ducats.TaskList or ducats.components.SongList object that contains the task list in use
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return null
     */
    @Override
    public String execute(SongList object, Ui ui, Storage storage) throws DucatsException {
        return null;
    }

    /**
     * The execute() method checks the redoability of a undoRedoStack, and behave accordingly.
     *  If redo is available, move the undoRedoStack to its next version, and tell the user
     *  how many times are left to redo.
     *  If redo is not available, warn the user that the undoRedoStack cannot be redone anymore.
     *
     * @param songs the SongList object that is passed in as a version
     * @param ui the Ui object responsible for the reading of user input
     *           and the display of the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @param undoRedoStack the UndoRedoStack object that stores different versions of
     *                      the SongList object
     * @return the formatted String to be displayed
     * @throws DucatsException in case of IO error happens
     */
    @Override
    public String execute(SongList songs, Ui ui, Storage storage, UndoRedoStack undoRedoStack) throws DucatsException {
        if (!undoRedoStack.canRedo()) {
            return ui.formatRedo();
        }

        // can redo
        undoRedoStack.redo();
        storage.updateFile(undoRedoStack.getCurrentVersion());
        return ui.formatRedo(undoRedoStack.numOfRedoLeft());
    }

    @Override
    public boolean isExit() {
        return false;
    }

    //@@author rohan-av
    /**
     * Returns an integer corresponding to the duration, tempo and time signature if the command starts a metronome.
     * Else, returns an array containing -1.
     *
     * @return the integer array corresponding to the parameters of the Metronome class
     */
    @Override
    public int[] startMetronome() {
        return new int[]{-1, -1, -1, -1};
    }
}
