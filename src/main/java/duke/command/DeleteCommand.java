package duke.command;

import duke.dukeobject.ExpenseList;
import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;
import duke.parser.CommandParams;
import duke.ui.Ui;

/**
 * Represents a specified command as DeleteCommand by extending the {@code Command} class.
 * Deletes the task with given index or specific command from the ExpenseList of Duke.
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
     * Lets the ExpenseList of Duke delete the task with the given index(s), or the entire task list and
     * updates content of storage file according to new ExpenseList.
     * Responses the result to user by using ui of Duke.
     *
     * @param expensesList The ExpenseList of Duke.
     * @param ui           The ui of Duke.
     * @throws DukeException If the index given is out of range, invalid, or does not exist.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "index"));
        }

        if (commandParams.getMainParam().equals("all")) {
            expensesList.clear();
        } else if (commandParams.getMainParam().contains("-")) {
            String[] index = commandParams.getMainParam().split("-");
            int difference = Integer.parseInt(index[1])-Integer.parseInt(index[0]);
            int counter = 0;
                for (int i = Integer.parseInt(index[0]); counter <= difference; counter++) {
                    expensesList.remove(i);
                }
            }
        else {
            expensesList.remove(Integer.parseInt(commandParams.getMainParam()));
        }
        expensesList.update();
    }
}
