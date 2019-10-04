package duke.command;

import duke.list.ExpenseList;
import duke.parser.CommandParams;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specified duke.command as ListCommand by extending the {@code Command} class.
 * Lists all expensesList in ExpenseList of duke.Duke.
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
     * Lists all expensesList in ExpenseList of duke.Duke by using duke.ui of duke.Duke.
     *
     * @param expensesList   The ExpenseList of duke.Duke.
     * @param ui      The duke.ui of duke.Duke.
     * @param storage The duke.storage of duke.Duke.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui, Storage storage) {
        if (expensesList.getSize() == 0) {
            ui.println("Ops, you haven't added any task!");
        } else {
            ui.println("Here are the expensesList in your duke.list:");
            for (int i = 0; i < expensesList.getSize(); i++) {
                ui.println((i + 1) + ". " + expensesList.getExpense(i).toString());
            }
        }
    }
}
