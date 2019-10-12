package duke.command;

import duke.storage.ContactStorage;
import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.Contacts;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

public class AddContactsCommand extends Command {
    protected Contacts contactObj;

    /**
     * Creates a command with the specified contact.
     *
     * @param contactObj The contacts to be added.
     */
    public AddContactsCommand(Contacts contactObj) {
        this.contactObj = contactObj;
    }

    /**
     * Executes a command with task list and ui.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
    }

    /**
     * Executes a command with task list and ui.
     *
     * @param items The task list that contains a list of tasks.
     * @param contactList The contact list that contains a list of contacts.
     * @param ui To tell the user that it is executed successfully.
     */
    public void execute(TaskList items, ContactList contactList, Ui ui) {
        contactList.add(contactObj);
        ui.showAddedContact(contactList);
    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {
    }

    public void executeStorage(TaskList items, Ui ui, ContactStorage contactStorage, ContactList contactList) throws IOException {
        contactStorage.write(contactList);
    }
}