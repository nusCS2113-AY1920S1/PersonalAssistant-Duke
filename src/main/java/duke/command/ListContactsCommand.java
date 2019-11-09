package duke.command;

import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents a command that lists all contacts stored.
 */
//@@author e0318465
public class ListContactsCommand extends Command {
    protected ContactList contactList;
    protected Ui ui = new Ui();

    public ListContactsCommand(ContactList contactList) {
        this.contactList = contactList;
    }

    /**
     * Executes a command that gathers all contacts from contact list.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the list of tasks stored in task list.
     * @return A string with the list of contacts to be output to the GUI.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return ui.showContactListGui(contactList);
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