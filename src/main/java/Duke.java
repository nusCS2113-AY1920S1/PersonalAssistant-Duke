import list.ExpenseList;
import parser.CommandParams;
import storage.Storage;
import ui.Ui;
import exception.DukeException;
import command.Command;
import parser.Parser;

import java.io.File;
import java.util.StringJoiner;

/**
 * Represents our Duke and contains the main program of Duke.
 */
public class Duke {
    private final static String USER_DIR = "data" + File.separator + "duke";

    private Storage storage = null;
    private ExpenseList expenseList;
    private Ui ui;

    /**
     * Constructs the Duke with the filePath of storage.txt
     * If errors occur during the loading process, an empty taskList will be initialized instead.
     *
     * @param userDirectory The user directory to store all the files associated with Duke.
     */
    public Duke(File userDirectory) {
        ui = new Ui();
        expenseList = new ExpenseList(userDirectory);
    }

    /**
     * Runs the Duke.
     * This terminates when the user typed in "bye" command.
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            String fullCommand = ui.readCommand();
            try {
                CommandParams commandParams = new CommandParams(fullCommand);
                Command command = Parser.getCommand(commandParams.getCommandName());
                command.execute(commandParams, expenseList, ui, storage);
            } catch (DukeException e) {
                ui.showError(e);
            }
        }
    }

    /**
     * Runs the main program of the Duke.
     *
     * @param args additional arguments provided by the user from the command line. Currently unused.
     */
    public static void main(String[] args) {
        File userDirectory = new File(USER_DIR);
        userDirectory.mkdirs();
        new Duke(userDirectory).run();
    }

}
