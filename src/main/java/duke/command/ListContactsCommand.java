package duke.command;

import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Representing a command that lists all contacts stored.
 */
public class ListContactsCommand extends Command {
    /**
     * Executes a command that gathers all tasks from task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the list of tasks stored in task list.
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
     *  @param items The task list that contains a list of tasks.
     * @param ui To tell the user the list of tasks stored in task list.
     * @return
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String str = Ui.showTaskListGui(items);
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
