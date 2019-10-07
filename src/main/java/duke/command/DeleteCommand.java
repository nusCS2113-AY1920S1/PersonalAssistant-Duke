package duke.command;

import duke.dukeobject.ExpenseList;
import duke.exception.DukeException;
import duke.parser.CommandParams;
import duke.ui.Ui;

/**
 * Represents a specified command as DeleteCommand by extending the {@code Command} class.
 * Deletes the task with given index from the ExpenseList of Duke.
 * Responses with the result.
 */
public class DeleteCommand extends Command {

    /**
     * Constructs a {@code DeleteCommand} object
     * given the index of the task to be deleted.
     */
    public DeleteCommand() {
        super(null, null, null, null);
    }

    /**
     * Lets the ExpenseList of Duke delete the task with the given index and
     * updates content of storage file according to new ExpenseList.
     * Responses the result to user by using ui of Duke.
     *
     * @param expensesList The ExpenseList of Duke.
     * @param ui           The ui of Duke.
     * @throws DukeException If the index given is out of range, invalid, or does not exist.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui) {
        /*
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't know which task to delete!");
        }
        String expenseDescription;
        try {
            int index = Integer.parseInt(commandParams.getMainParam()) - 1; // 0-based
            expenseDescription = expensesList.getExpense(index).toString();
            expensesList.delete(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("☹ OOPS!!! The index should be in range.");
        } catch (NumberFormatException e) {
            throw new DukeException("☹ OOPS!!! The index be a number.");
        }
        storage.update(expensesList.toStorageStrings());
        ui.println("Noted. I've removed this task:");
        ui.println(taskInfo);
        ui.println("Now you have " + expensesList.getSize() + " expensesList in the list.");

         */
    }
}
