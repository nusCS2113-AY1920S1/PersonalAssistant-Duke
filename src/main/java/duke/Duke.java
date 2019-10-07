package duke;

import duke.dukeobject.ExpenseList;
import duke.parser.CommandParams;
import duke.ui.Ui;
import duke.exception.DukeException;
import duke.command.Command;
import duke.parser.Parser;

import java.io.File;

/**
 * Represents our Duke and contains the main program of Duke.
 */
public class Duke {
    private static final String DEFAULT_USER_DIR = "data" + File.separator + "duke";

    final ExpenseList expenseList;
    private Ui ui;

    /**
     * Constructs the Duke with the filePath of storage.txt
     * If errors occur during the loading process, an empty taskList will be initialized instead.
     *
     * @param userDirectory The user directory to store all the files associated with Duke.
     */
    public Duke(File userDirectory) {
        userDirectory.mkdirs();
        ui = new Ui();
        expenseList = new ExpenseList(userDirectory);
    }

    public Duke() {
        // In case we support changing Duke's directory in the future
        this(new File(DEFAULT_USER_DIR));
    }

    /**
     * Gets the output from Duke's logic.
     * @param fullCommand String of the full command that the user entered.
     * @return String containing last output message of Duke.
     */
    public String getResponse(String fullCommand) {
        try {
            CommandParams commandParams = new CommandParams(fullCommand);
            Command command = Parser.getCommand(commandParams.getCommandName());
            command.execute(commandParams, expenseList, ui);
        } catch (DukeException e) {
            ui.showError(e);
        }
        return ui.getMostRecent();
    }
}
