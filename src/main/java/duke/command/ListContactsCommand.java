package duke.command;

import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Representing a command that lists all contacts stored.
 */
public class ListContactsCommand extends Command {
    protected ContactList contactList;

    /**
     * Executes a command that locates matching tasks in task list and outputs the result.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the matching tasks based on the keyword.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
    }

    /**
     * Executes a command that gathers all contacts from contact list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param contactList The list of contacts.
     * @param ui To tell the user the list of tasks stored in task list.
     */
    public void execute(TaskList items, ContactList contactList, Ui ui) {
        ui.showContactList(contactList);
    }

    /**
     * Executes a command that gathers all tasks from task list and outputs the result (GUI).
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the list of tasks stored in task list.
     * @return List of tasks.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    /**
     * Executes a command that gathers all tasks from task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the list of tasks stored in task list.
     * @param contactList List of contacts.
     * @return The string which conissts of a list of contacts to display on GUI.
     */
    public String executeGui(TaskList items, ContactList contactList, Ui ui) {
        String str = Ui.showContactListGui(contactList);
        return str;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }
}