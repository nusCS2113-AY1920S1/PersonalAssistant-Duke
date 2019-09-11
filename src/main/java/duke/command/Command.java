package duke.command;

import duke.core.DukeExceptionThrow;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;

/**
 * Represents a command class received from user. It is an abstract
 * class that can not be instantiated, its child class represents different kind
 * of user command
 */
public abstract class Command {

    /**
     * run the command with the respect duke.core.TaskList, UI, and storage.
     * @param tasks The task list where tasks are saved.
     * @param ui The user interface.
     * @param storage object that handles local text file update
     * @throws DukeExceptionThrow throw exception during execution of the
     *      command.
     */
    public abstract void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow;
    /**
     * Decide whether duke should exist.
     * @return A boolean. True if the command tells duke.Duke to exit, false
     *          otherwise.
     */
    public abstract boolean isExit();

}