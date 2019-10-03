package command;

import DukeObjects.ExpenseList;
import exception.DukeException;
import parser.CommandParams;
import ui.Ui;
import storage.Storage;

import java.util.ArrayList;

/**
 * Represents a specified command as FindCommand by extending the {@code Command} class.
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
     * Finds relevant expensesList from ExpenseList of Duke with the given keyword and
     * responses the result to user by using ui of Duke.
     *
     * @param expensesList The ExpenseList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     * @throws DukeException if a search string was not given.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui, Storage storage) {
        /*
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't what to find.");
        }
        ArrayList<Integer> matchedList = expensesList.find(commandParams.getMainParam());
        if (matchedList.size() == 0) {
            ui.println("No results found.");
            return;
        }
        ui.println("Here are the matching expensesList in your list:");
        int count = 1;
        for (int i : matchedList) {
            ui.println(count + "." + expensesList.getTaskInfo(i));
            count++;
        }

         */
    }
}
