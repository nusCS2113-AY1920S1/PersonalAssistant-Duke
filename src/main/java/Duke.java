import Money.Account;
import MoneyCommands.MoneyCommand;
import commands.Command;
import controlpanel.DukeException;
import controlpanel.Parser;
import controlpanel.Storage;
import controlpanel.Ui;
import tasks.TaskList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

/**
 * The main class which controls the overall flow, run the program.
 */
public class Duke {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private Account account;

    /**
     * Duke class acts as a constructor to initialize and setup
     * //@param filePath the path of the tasks.txt which contains the data of the tasks' list
     */
    public Duke() {
        Path currentDir = Paths.get("data/tasks.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
            account = new Account();//need to load from storage on program init
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * This method runs the overall program.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit && ui.inputStatus()) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (ParseException | DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * This method prints a line that Duke will print out in the program.
     * @return a line that the program will print out in response to a user's commands
     */
    public String getResponse(String input) {
        try {

            ui.clearOutputString();
            ui.appendToOutput(ui.showLine());
            boolean isNewUser = account.isToInitialize();
            MoneyCommand c = Parser.moneyParse(input, isNewUser);
            c.execute(account, ui, storage);

            if (c.isExit()) {
                System.exit(0);
            }

        } catch (ParseException | DukeException e) {
            ui.clearOutputString();
            ui.appendToOutput(ui.showError(e.getMessage()));
            return ui.getOutputString();
        } finally {
            ui.appendToOutput(ui.showLine());
        }
        return ui.getOutputString();
    }

}
