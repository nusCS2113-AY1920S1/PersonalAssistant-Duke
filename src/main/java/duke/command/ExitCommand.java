package duke.command;

import duke.list.ExpenseList;
import duke.parser.CommandParams;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specified duke.command as ExitCommand by extending the {@code Command} class.
 * Terminates the loop in {@code main} method of duke.Duke.
 * Responses with the result.
 */
public class ExitCommand extends Command {
    /**
     * Constructs an {@code ExitCommand} object.
     */
    public ExitCommand() {
        super(null, null, null, null);
    }

    /**
     * Shows bye to user.
     *
     * @param expensesList   The ExpenseList of duke.Duke.
     * @param ui      The duke.ui of duke.Duke.
     * @param storage The duke.storage of duke.Duke.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui, Storage storage) {
        ui.println("Bye. Hope to see you again soon!");
        System.exit(0);
    }

}
