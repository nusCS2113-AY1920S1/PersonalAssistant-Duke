package duke.command;

import duke.storage.ContactStorage;
import duke.storage.Storage;
import duke.task.ContactList;
import duke.task.Contacts;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Representing a command that adds contacts.
 */
//@@author e0318465
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
     * Executes a command that adds the tasks into task list and outputs the result.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
    }

    /**
     * Adds the user input to a list of contacts.
     *
     * @param items The task list that contains a list of tasks.
     * @param contactList The list of contacts.
     * @param ui To tell the user that it is executed successfully.
     */
    public void execute(TaskList items, ContactList contactList, Ui ui) {
        contactList.add(contactObj);
        ui.showAddedContact(contactList);
    }

    /**
     * Executes a command that adds the task into task list and outputs the result (GUI).
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is added successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    /**
     * Executes a command that adds the contact into contact list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param contactList The list of contacts.
     * @param ui To tell the user that it is executed successfully.
     * @return A string value to be output to GUI.
     */
    public String executeGui(TaskList items, ContactList contactList, Ui ui) {
        contactList.add(contactObj);
        String str = ui.showAddedContactGui(contactList);
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
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {
    }

    /**
     * Executes a command that overwrites existing storage with the updated contact list.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param contactStorage Contacts stored in storage.
     * @param contactList The list of contacts.
     * @throws IOException If there is an error reading the file.
     */
    public void executeStorage(TaskList items, Ui ui, ContactStorage contactStorage,
                               ContactList contactList) throws IOException {
        contactStorage.write(contactList);
    }
}