import DukeObjects.ExpenseList;
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

    private Storage storage;
    private ExpenseList expenseList;
    private Ui ui;


    public Duke() {
        ui = new Ui();
        String filePath = new StringJoiner(File.separator)
                .add(System.getProperty("user.dir"))
                .add("data")
                .add("ExpenseListStorage.txt")
                .toString();
        storage = new Storage(filePath);
        try {
            expenseList = new ExpenseList(storage.load());
        } catch (DukeException e) {
            ui.showError(e);
            expenseList = new ExpenseList();
        }
    }

    /**
     * Constructs the Duke with the filePath of storage.txt
     *
     * @param filePath The filePath of storage.txt
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
     * Runs the Duke.
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

    public String getResponse(String fullCommand){
        try {
            CommandParams commandParams = new CommandParams(fullCommand);
            Command command = Parser.getCommand(commandParams.getCommandName());
            command.execute(commandParams, expenseList, ui, storage);
        } catch (DukeException e) {
            ui.showError(e);
        }

        return ui.getMostRecent();
    }

    public ExpenseList getExpenseList(){
        return expenseList;
    }
    /**
     * Runs the Duke.
     *

    /**
     * Runs the main program of the Duke.
     *
     * @param args additional arguments provided by the user from the command line. Currently unused.
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

