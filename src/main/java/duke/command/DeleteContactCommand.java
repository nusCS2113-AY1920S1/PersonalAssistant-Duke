package duke.command;

import duke.enums.Numbers;
import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Represents a command that deletes a contact.
 */
public class DeleteContactCommand extends Command {
    protected int indexOfContactToDelete;
    protected ContactList contactList;
    protected Ui ui = new Ui();

    /**
     * To delete a contact by the index of the contact.
     *
     * @param indexOfContactToDelete The index of the contact to be deleted.
     * @param contactList The list of contacts.
     */
    public DeleteContactCommand(int indexOfContactToDelete, ContactList contactList) {
        this.indexOfContactToDelete = indexOfContactToDelete;
        this.contactList = contactList;
    }

    /**
     * Executes a command that deletes the contact from the contact list and outputs the deleted contact (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @return String to be output to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String str;
        if (indexOfContactToDelete >= Numbers.ZERO.value && indexOfContactToDelete
                + Numbers.ONE.value <= contactList.size()) {
            String deletedContact = contactList.getAndDisplay(indexOfContactToDelete);
            contactList.remove(indexOfContactToDelete);
            str = ui.showContactDeletedGui(contactList, deletedContact);
        } else if (contactList.size() == Numbers.ZERO.value) {
            str = ui.showErrorMsgGui("No contacts to be deleted!");
        } else {
            str = ui.showErrorMsgGui("Invalid index! Please choose 1 "
                    + ((contactList.size() == Numbers.ONE.value) ? "" : "to " + contactList.size()));
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
}