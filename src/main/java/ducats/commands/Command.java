package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.SongList;
import ducats.components.UndoRedoStack;

/**
 * An abstract class used to represent a command interpreted from an input message. Extended by
 * <ul>
 *     <li>AddBarCommand</li>
 *     <li>AddOverlayCommand</li>
 *     <li>AsciiCommand</li>
 *     <li>ByeCommand</li>
 *     <li>CopyCommand</li>
 *     <li>DeleteBarCommand</li>
 *     <li>DeleteCommand</li>
 *     <li>EditBarCommand</li>
 *     <li>GroupCommand</li>
 *     <li>HelpCommand</li>
 *     <li>InsertBarCommand</li>
 *     <li>ListCommand</li>
 *     <li>ListGroupCommand</li>
 *     <li>MetronomeCommand</li>
 *     <li>NewCommand</li>
 *     <li>OpenCommand</li>
 *     <li>OverlayBarGroupCommand</li>
 *     <li>OverlayBarSongCommand</li>
 *     <li>OverlayGroupGroupCommand</li>
 *     <li>RedoCommand</li>
 *     <li>SwapBarCommand</li>
 *     <li>UndoCommand</li>
 *     <li>ViewCommand</li>
 * </ul>
 */
public abstract class Command {

    String message;

    /**
     * Executes the command by performing the necessary changes to the task list and the .txt
     * file used as persistent storage, and returns a formatted String to be displayed by the
     * command line interface / GUI.
     *
     * @param songList the ducats.components.SongList object that contains the song list in use
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the formatted String to be displayed
     * @throws DucatsException in the case of either parsing or IO errors
     */
    public abstract String execute(SongList songList, Ui ui, Storage storage) throws DucatsException;

    /**
     * Used for undo command and redo command only.
     * This method passes in a UndoRedoStack object which stores all the version of the SongList object
     *
     * @param songs the SongList object that is passed in as a version
     * @param ui the Ui object responsible for the reading of user input
     *           and the display of the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @param undoRedoStack the UndoRedoStack object that stores different versions of
     *                      the SongList object
     * @return the formatted String to be displayed
     * @throws DucatsException in the case of either parsing or IO errors
     */
    public String execute(SongList songs, Ui ui, Storage storage, UndoRedoStack undoRedoStack) throws DucatsException {
        return null;
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * ducats.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    public abstract boolean isExit();

    /**
     * Returns an integer corresponding to the duration, tempo and time signature if the command starts a metronome.
     * Else, returns an array containing -1.
     *
     * @return the integer array corresponding to the parameters of the Metronome class
     */
    public abstract int[] startMetronome();

}
