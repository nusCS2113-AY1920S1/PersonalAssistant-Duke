package duke.command;

import duke.dukeobject.ExpenseList;
import duke.parser.CommandParams;
import duke.ui.Ui;
import javafx.application.Platform;

/**
 * Represents a specified command as ExitCommand by extending the {@code Command} class.
 * Terminates the loop in {@code main} method of Duke.
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
     *  @param expensesList The ExpenseList of Duke.
     * @param ui           The ui of Duke.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui) {
        ui.println("Bye. Hope to see you again soon!");
        Platform.exit();
    }
}
