package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;

/**
 * Ensures that all the classes of command type have implementations of the method execute.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public abstract class Command {

    /**
     * Defines that isExit is false when instantiated to allow program to continue running.
     */
    protected boolean isExit = false;

    /**
     * Terminates the program by setting isExit to true.
     */
    public void commandOut() {
        isExit = true;
    }

    /**
     * Contracts all Command type classes to have their own respective execute
     * methods.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @throws DukeException Throws the exception according to the user-defined list: DukeException.
     */
    public abstract void execute(TaskList tasks, Storage storage) throws DukeException;

    /**
     * This getExit function is called by a program to check the status of exit.
     */
    public boolean getExit() {
        return isExit;
    }
}