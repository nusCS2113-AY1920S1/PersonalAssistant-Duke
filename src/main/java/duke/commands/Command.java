package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Ui;

/**
 * An abstract class used to represent a command interpreted from an input message. Extended by
 * <ul>
 *     <li>AddCommand</li>
 *     <li>ListCommand</li>
 *     <li>DoneCommand</li>
 *     <li>DeleteCommand</li>
 *     <li>FindCommand</li>
 *     <li>ByeCommand</li>
 * </ul>
 */
public abstract class Command {

    String message;

    /**
     * Executes the command by performing the necessary changes to the task list and the .txt
     * file used as persistent storage, and returns a formatted String to be displayed by the
     * command line interface / GUI.
     *
     * @param tasks the duke.TaskList object that contains the task list in use
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the formatted String to be displayed
     * @throws DukeException in the case of either parsing or IO errors
     */
    public abstract String execute(duke.TaskList tasks, Ui ui, Storage storage) throws DukeException;

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    public abstract boolean isExit();

}
