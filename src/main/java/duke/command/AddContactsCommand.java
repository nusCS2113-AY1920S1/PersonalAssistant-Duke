package duke.command;

import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.Contacts;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

public class AddContactsCommand extends Command {
    protected Contacts contacts;

    /**
     * Creates a command with the specified contact.
     *
     * @param contacts The contacts to be added.
     */
    public AddContactsCommand(Contacts contacts) {
        this.contacts = contacts;
    }

    /**
     * Executes a command that adds the task into task list and outputs the result.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param ui To tell the user that it is added successfully.
     */
    public void execute(ContactList contactList, Ui ui) {
        contactList.add(contacts);
        ui.showAddedContact(contactList);
    }

    @Override
    public void execute(TaskList items, Ui ui) {
        
    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {

    }
}
