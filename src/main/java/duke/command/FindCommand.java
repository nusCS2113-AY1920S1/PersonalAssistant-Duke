package duke.command;

import duke.list.ExpenseList;
import duke.exception.DukeException;
import duke.parser.CommandParams;
import duke.ui.Ui;
import duke.storage.Storage;

/**
 * Represents a specified duke.command as FindCommand by extending the {@code Command} class.
 * Finds all expensesList relevant with the searched keyword.
 * Responses with the result.
 */
public class FindCommand extends Command {

    /**
     * Constructs a {@code FindCommand} object
     * with given searched keyword.
     */
    public FindCommand() {
        super(null, null, null, null);
    }

    /**
     * Finds relevant expensesList from ExpenseList of duke.Duke with the given keyword and
     * responses the result to user by using duke.ui of duke.Duke.
     *
     * @param expensesList The ExpenseList of duke.Duke.
     * @param ui The duke.ui of duke.Duke.
     * @param storage The duke.storage of duke.Duke.
     * @throws DukeException if a search string was not given.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui, Storage storage) {
        /*
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't what to find.");
        }
        ArrayList<Integer> matchedList = expensesList.find(commandParams.getMainParam());
        if (matchedList.size() == 0) {
            duke.ui.println("No results found.");
            return;
        }
        duke.ui.println("Here are the matching expensesList in your duke.list:");
        int count = 1;
        for (int i : matchedList) {
            duke.ui.println(count + "." + expensesList.getTaskInfo(i));
            count++;
        }

         */
    }
}
