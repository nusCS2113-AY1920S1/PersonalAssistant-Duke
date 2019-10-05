package command;

import dukeobjects.ExpenseList;
import parser.CommandParams;
import storage.Storage;
import ui.Ui;

/**
 * Represents a specified command as ListCommand by extending the {@code Command} class.
 * Lists all expensesList in ExpenseList of Duke.
 * Responses with the result.
 */
public class ListCommand extends Command {
    /**
     * Constructs a {@code ListCommand} object.
     */
    public ListCommand() {
        super(null, null, null, null);
    }

    /**
     * Lists all expensesList in ExpenseList of Duke by using ui of Duke.
     *
     * @param expensesList   The ExpenseList of Duke.
     * @param ui      The ui of Duke.
     * @param storage The storage of Duke.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui, Storage storage) {
        if (expensesList.getSize() == 0) {
            ui.println("Ops, you haven't added any task!");
        } else {
            ui.println("Here are the expenses:");
            for (int i = 0; i < expensesList.getSize(); i++) {
                ui.println((i + 1) + ". " + expensesList.getExpense(i).toString());
            }
        }
    }
}
