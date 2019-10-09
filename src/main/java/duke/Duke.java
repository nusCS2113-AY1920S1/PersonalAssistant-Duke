package duke;

import duke.dukeobject.ExpenseList;
import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;
import duke.parser.CommandParams;
import duke.ui.Ui;
import duke.command.Command;
import duke.parser.Parser;

import java.io.File;

/**
 * Represents our Duke and contains the main program of Duke.
 */
public class Duke {
    private static final String BACKUP_EXTENSION = ".backup";
    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File EXPENSES_FILE = new File(DEFAULT_USER_DIRECTORY, "expenses.txt");
    private static final File EXPENSES_BACKUP_FILE =
        new File(DEFAULT_USER_DIRECTORY, "expenses.txt" + BACKUP_EXTENSION);

    ExpenseList expenseList;
    private Ui ui;

    /**
     * Constructs the Duke with the filePath of storage.txt
     * If errors occur during the loading process, an empty taskList will be initialized instead.
     *
     * @param userDirectory The user directory to store all the files associated with Duke.
     */
    public Duke(File userDirectory) throws DukeRuntimeException {
        userDirectory.mkdirs();
        ui = new Ui();
        try {
            try {
                expenseList = new ExpenseList(EXPENSES_FILE);
            } catch (DukeException e) {
                ui.showError(e);
                EXPENSES_BACKUP_FILE.delete();
                EXPENSES_FILE.renameTo(EXPENSES_BACKUP_FILE);
                expenseList = new ExpenseList(EXPENSES_FILE);
            }
        } catch (DukeException e) {
            throw new DukeRuntimeException("Could not load from file, nor recover using a new file");
        }
    }

    public Duke() {
        // In case we support changing Duke's directory in the future
        this(DEFAULT_USER_DIRECTORY);
    }

    /**
     * Gets the output from Duke's logic.
     * @param fullCommand String of the full command that the user entered.
     * @return String containing last output message of Duke.
     */
    public String getResponse(String fullCommand) {
        try {
            CommandParams commandParams = new CommandParams(fullCommand);
            Command command = Parser.parseCommand(commandParams.getCommandName());
            command.execute(commandParams, expenseList, ui);
        } catch (DukeException e) {
            ui.showError(e);
        }
        return ui.getMostRecent();
    }
}
