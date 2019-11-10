package duke.command;

import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

//@@author e0318465
/**
 * Represents a command that searches through the contact in contact list for the keyword.
 */
public class FindContactCommand extends Command {
    protected String keyword;
    protected Ui ui = new Ui();
    protected ContactList contactList;

    /**
     * Creates a command with the specified keyword.
     *
     * @param keyword The word to be searched in the list of contacts.
     * @param contactList A list of Contacts.
     */
    public FindContactCommand(String keyword, ContactList contactList) {
        this.keyword = keyword;
        this.contactList = contactList;
    }

    /**
     * Executes a command that locates matching contacts in contact list.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the matching tasks based on the keyword.
     * @return A string with contacts found to be output to the GUI.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return ui.showFoundContactsGui(contactList, keyword);
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