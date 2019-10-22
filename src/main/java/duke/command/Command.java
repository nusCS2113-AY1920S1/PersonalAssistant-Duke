package duke.command;

import duke.storage.BudgetStorage;
import duke.storage.ContactStorage;
import duke.storage.PriorityStorage;
import duke.storage.Storage;
import duke.task.BudgetList;
import duke.task.ContactList;
import duke.task.PriorityList;
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
     * Executes a command with task list and ui.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities.
     * @param ui To tell the user that it is executed successfully.
     */
    public void execute(TaskList items, PriorityList priorities, Ui ui) {
    }

    /**
     * Executes a command with task list, contactList and ui.
     *
     * @param items The task list that contains a list of tasks.
     * @param contactList The list of contacts.
     * @param ui To tell the user that it is executed successfully.
     */
    public void execute(TaskList items, ContactList contactList, Ui ui) {
    }

    /**
     * Executes a command with task list and ui (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    public abstract String executeGui(TaskList items, Ui ui);

    public String executeGui(TaskList items, ContactList contactList,Ui ui) {
        return null;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     * @throws IOException If there is an error reading the file.
     */
    public abstract void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException;

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
    }

    //@@author maxxyx96
    /**
     * Executes a command that overwrites existing storage with the all task list.
     *
     * @param items Task list that contains a list of tasks.
     * @param ui To tell user that it is executed successfully.
     * @param storage Storage that stores and handles taskLists.
     * @param budgetStorage Storage that stores and handles budgets.
     * @param budgetList Budget list that contains the budget information.
     * @param contactStorage Storage that stores and handles contact list.
     * @param contactList Contact list that contains a list of Contacts.
     * @param priorityStorage Storage that stores and handles priority list.
     * @param priorityList Priority list that contains a list of Priorities.
     * @throws IOException If there is an error reading the file.
     */
    public void executeStorage(TaskList items, Ui ui, Storage storage,
                               BudgetStorage budgetStorage, BudgetList budgetList,
                               ContactStorage contactStorage, ContactList contactList,
                               PriorityStorage priorityStorage, PriorityList priorityList) throws IOException {
    }
    //@@author
}

