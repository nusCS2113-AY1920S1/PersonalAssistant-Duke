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
import duke.task.FilterList;


import java.io.IOException;

//@@author talesrune
/**
 * Represents various kinds of commands using an abstract class.
 */
public abstract class Command {
    //@@author talesrune
    /**
     * Executes a command with Filter list and ui.
     *
     * @param items The task list that contains a list of tasks.
     * @param filterList The list of filtered tasks.
     */
    public void execute(TaskList items, FilterList filterList) {

    }

    /**
     * Executes a command with task list and ui (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    public abstract String executeGui(TaskList items, Ui ui);

    //@@author
    /**
     * Executes a command with task list and ui (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities.
     * @param ui To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    public String executeGui(TaskList items,PriorityList priorities, Ui ui) {
        return null;
    }

    //@@author talesrune
    /**
     * Executes a command that overwrites existing storage with the updated task list.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     * @throws IOException If there is an error reading the file.
     */
    public abstract void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException;

    //@@author

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