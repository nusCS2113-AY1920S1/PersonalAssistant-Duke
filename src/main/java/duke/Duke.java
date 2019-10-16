package duke;

import duke.command.Command;
import duke.dukeobject.Budget;
import duke.dukeobject.ExpenseList;
import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;
import duke.command.CommandParams;
import duke.ui.Ui;

import java.io.File;
import java.io.IOException;

/**
 * Represents our Duke and contains the main program of Duke.
 */
public class Duke {
    private static final String BACKUP_EXTENSION = ".backup";
    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File EXPENSES_FILE = new File(DEFAULT_USER_DIRECTORY, "expenses.txt");
    private static final File BUDGET_FILE = new File(DEFAULT_USER_DIRECTORY, "budget.txt");
    private static final File EXPENSES_BACKUP_FILE =
            new File(DEFAULT_USER_DIRECTORY, "expenses.txt" + BACKUP_EXTENSION);
    private static final File BUDGET_BACKUP_FILE =
            new File(DEFAULT_USER_DIRECTORY, "budget.txt" + BACKUP_EXTENSION);
    public Budget budget;
    public ExpenseList expenseList;
    public Ui ui;

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
            throw new DukeRuntimeException("Could not load expenses from file, nor recover using a new file");
        }
        try {
            try {
                budget = new Budget(BUDGET_FILE);
            } catch (DukeException e) {
                ui.showError(e);
                BUDGET_BACKUP_FILE.delete();
                BUDGET_FILE.renameTo(BUDGET_BACKUP_FILE);
                budget = new Budget(BUDGET_FILE);
            } catch (IOException e) {
                throw new DukeRuntimeException("Could not load budget from file, nor recover using a new file");
            }
        } catch (DukeException | IOException e) {
            throw new DukeRuntimeException("Could not load budget from file, nor recover using a new file");
        }
    }

    public Duke() {
        // In case we support changing Duke's directory in the future
        this(DEFAULT_USER_DIRECTORY);
    }

    /**
     * Gets the output from Duke's logic.
     *
     * @param fullCommand String of the full command that the user entered.
     * @return String containing last output message of Duke.
     */
    public String getResponse(String fullCommand) {
        try {
            CommandParams commandParams = new CommandParams(fullCommand);
            Command command = commandParams.getCommand();
            command.execute(commandParams, this);
        } catch (DukeException e) {
            ui.showError(e);
        }
        return ui.getMostRecent();
    }

    public void println(String s) {
        ui.println(s);
    }

}
