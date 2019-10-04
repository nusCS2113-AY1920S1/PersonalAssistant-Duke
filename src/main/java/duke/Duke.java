package duke;

import duke.list.ExpenseList;
import duke.parser.CommandParams;
import duke.storage.Storage;
import duke.ui.Ui;
import duke.exception.DukeException;
import duke.command.Command;
import duke.parser.Parser;

import java.io.File;
import java.util.StringJoiner;

/**
 * Represents our duke.Duke and contains the main program of duke.Duke.
 */
public class Duke {

    private Storage storage;
    private ExpenseList expenseList;
    private Ui ui;

    /**
     * Constructs the duke.Duke with the filePath of duke.storage.txt
     * If errors occur during the loading process, an empty taskList will be initialized instead.
     *
     * @param filePath The filePath of duke.storage.txt
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            expenseList = new ExpenseList(storage.load());
        } catch (DukeException e) {
            ui.showError(e);
            expenseList = new ExpenseList();
        }
    }

    /**
     * Runs the duke.Duke.
     * This terminates when the user typed in "bye" duke.command.
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
     * Runs the main program of the duke.Duke.
     *
     * @param args additional arguments provided by the user from the duke.command line. Currently unused.
     */
    public static void main(String[] args) {
        String storageFile = new StringJoiner(File.separator)
                .add(System.getProperty("user.dir"))
                .add("data")
                .add("ExpenseListStorage.txt")
                .toString();
        new Duke(storageFile).run();
    }

}
