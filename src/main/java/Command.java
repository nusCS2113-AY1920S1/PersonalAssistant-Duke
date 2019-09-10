/**
 * Represents a command class received from user. It is an abstract
 * class that can not be instantiated, its child class represents different kind
 * of user command
 */
public abstract class Command {

    /**
     * run the command with the respect TaskList, UI, and storage.
     * @param tasks The task list where tasks are saved.
     * @param ui The user interface.
     * @param storage object that handles local text file update
     * @throws DukeExceptionThrow throw exception during execution of the
     *      command.
     */
    abstract void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow;
    /**
     * Decide whether duke should exist.
     * @return A boolean. True if the command tells Duke to exit, false
     *          otherwise.
     */
    abstract boolean isExit();

}