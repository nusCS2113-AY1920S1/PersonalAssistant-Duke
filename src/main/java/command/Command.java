package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;

/**
 * The command abstract class is used to ensure that all the classes have
 * implementations of the methods and have the implementation of isExit().
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public abstract class Command {

    protected boolean exit;

    /**
     * This Command constructor defines that exit is false when instantiated to
     * allow the running of the program.
     */
    public Command() {
        exit = false;
    }

    /**
     * This CommandOut function is to set exit to true, in order to terminate the
     * program.
     */
    public void commandOut() {
        exit = true;
    }

    /**
     * Contracts all Command type classes to have their own respective execute
     * methods to ensure that the date is processed and stored properly.
     *
     * @param tasks   The TaskList that was loaded from persistent storage is passed
     *                to the caller to utilise it.
     * @param storage The storage is passed to the user to allow the user to save
     *                the file after updating TaskList.
     * @throws DukeException The DukeException class has all the respective methods
     *                       and messages.
     */
    public abstract void execute(TaskList tasks, Storage storage) throws DukeException;

    /**
     * This isExit function is called by a program to check the status of exit.
     */
    public boolean isExit() {
        return exit;
    }
}