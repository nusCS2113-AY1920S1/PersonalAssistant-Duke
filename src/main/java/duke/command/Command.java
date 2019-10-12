package duke.command;

import duke.storage.ContactStorage;
import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

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
     * Executes a command with task list and ui (GUI).
     *  @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @return
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

    public void execute(TaskList items, ContactList contactList, Ui ui) {
    }
    public void executeStorage(TaskList items, Ui ui, ContactStorage contactStorage, ContactList contactList) throws IOException {
    }
}

