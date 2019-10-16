package duke.command;

import duke.Duke;
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
     * // to add param
     */
    public void execute(CommandParams commandParams, Duke duke) {
        duke.ui.println("Bye. Hope to see you again soon!");
        Platform.exit();
    }
}
