package duke.command;

import duke.storage.PriorityStorage;
import duke.storage.Storage;
import duke.task.PriorityList;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An abstract class that represents various kinds of commands.
 */
public abstract class Command {

    /**
     * Executes a command with task list and ui.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     */
    public abstract void execute(TaskList items, Ui ui);

    /**
     * Executes a command with task list and ui.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities.
     * @param ui To tell the user that it is executed successfully.
     */

    public void execute(TaskList items, PriorityList priorities, Ui ui) {

    }


    /**
     * Executes a command with task list and ui (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    public abstract String executeGui(TaskList items, Ui ui);

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     * @throws IOException  If there is an error reading the file.
     */
    public abstract void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException;

}

