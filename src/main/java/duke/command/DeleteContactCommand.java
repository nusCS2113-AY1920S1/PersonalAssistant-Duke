package duke.command;

import duke.storage.ContactStorage;
import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Representing a command that deletes a contact.
 */
public class DeleteContactCommand extends Command {
    protected int indexOfContactToDelete;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * To delete a contact by the index of the contact.
     *
     * @param indexOfContactToDelete The index of the contact to be deleted.
     */
    public DeleteContactCommand(int indexOfContactToDelete) {
        this.indexOfContactToDelete = indexOfContactToDelete;
    }

    /**
     * Executes a command that deletes the task from the task list and outputs the result.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is deleted successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
    }

    /**
     * Executes a command that deletes the contact from the contact list.
     *
     * @param items The task list that contains a list of tasks.
     * @param contactList The list of contacts.
     * @param ui To tell the user that it is executed successfully.
     */
    @Override
    public void execute(TaskList items, ContactList contactList, Ui ui) {
        if (indexOfContactToDelete >= ZERO && indexOfContactToDelete + ONE <= contactList.size()) {
            String deletedContact = contactList.get(indexOfContactToDelete).toString();
            contactList.remove(indexOfContactToDelete);
            ui.showContactDeleted(contactList, deletedContact);
        } else if (contactList.size() == ZERO) {
            ui.showErrorMsgGui("No contacts to be deleted!");
        } else {
            ui.showErrorMsg("Invalid index! Please choose 1 "
                    + ((contactList.size() == ONE) ? "" : "to " + contactList.size()));
        }
    }

    /**
     * Executes a command that deletes the task from the task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @return String to be output to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    /**
     * Executes a command that deletes the task from the task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is deleted successfully.
     * @return String to be output to the user.
     */
    @Override
    public String executeGui(TaskList items, ContactList contactList, Ui ui) {
        String str;
        if (indexOfContactToDelete >= ZERO && indexOfContactToDelete + ONE <= contactList.size()) {
            String deletedContact = contactList.get(indexOfContactToDelete).toString();
            contactList.remove(indexOfContactToDelete);
            str = ui.showContactDeletedGui(contactList, deletedContact);
        } else if (contactList.size() == ZERO) {
            str = ui.showErrorMsgGui("No contacts to be deleted!");
        } else {
            str = ui.showErrorMsgGui("Invalid index! Please choose 1 "
                                    + ((contactList.size() == ONE) ? "" : "to " + contactList.size()));
        }
        return str;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     * @throws IOException If there is an error reading the file.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {
    }

    /**
     * Executes a command that overwrites existing storage with the updated contact list.
     *
     * @param items The task list that contains a list of tasks..
     * @param ui To tell the user that it is executed successfully.
     * @param contactStorage The storage to be overwritten.
     * @throws IOException If there is an error reading the file.
     */
    public void executeStorage(TaskList items, Ui ui, ContactStorage contactStorage,
                               ContactList contactList) throws IOException {
        contactStorage.write(contactList);
    }
}