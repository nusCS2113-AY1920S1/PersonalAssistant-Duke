package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;
import duke.components.SongList;
import duke.components.UndoRedoStack;

/**
 * An abstract class used to represent a command interpreted from an input message. Extended by
 * <ul>
 *     <li>AddCommand</li>
 *     <li>ListCommand</li>
 *     <li>DoneCommand</li>
 *     <li>DeleteCommand</li>
 *     <li>FindCommand</li>
 *     <li>ByeCommand</li>
 *     <li>NewCommand</li>
 *     <li>HelpCommand</li>
 *     <li>ViewCommand</li>
 *     <li>AddBarCommand</li>
 * </ul>
 */
public abstract class Command<T> {

    String message;

    /**
     * Executes the command by performing the necessary changes to the task list and the .txt
     * file used as persistent storage, and returns a formatted String to be displayed by the
     * command line interface / GUI.
     *
     * @param object the duke.TaskList or duke.components.SongList object that contains the task list in use
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the formatted String to be displayed
     * @throws DukeException in the case of either parsing or IO errors
     */
    public abstract String execute(T object, Ui ui, Storage storage) throws DukeException;

    public String execute(SongList songs, Ui ui, Storage storage, UndoRedoStack undoRedoStack) throws DukeException {
        return null;
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    public abstract boolean isExit();

}
