import controlpanel.*;
import money.Account;
import moneycommands.AutoUpdateInstalmentCommand;
import moneycommands.MoneyCommand;
import moneycommands.UndoCommand;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

/**
 * The main class which controls the overall flow, run the program.
 */
public class Duke{

    private Ui ui;
    private MoneyStorage moneyStorage;
    private Account account;
    private UndoCommandHandler undoCommandHandler;

    /**
     * Duke class acts as a constructor to initialize and setup
     * //@param filePath the path of the moneyAccount.txt which contains the finance of the users
     */
    public Duke() {
        Path moneyDir = Paths.get("data/moneyAccount.txt");
        String moneyFilePath = moneyDir.toAbsolutePath().toString();
        ui = new Ui();
        moneyStorage = new MoneyStorage(moneyFilePath);
        undoCommandHandler = new UndoCommandHandler();
        try {
            account = new Account(moneyStorage.load());//need to load from storage on program init
        } catch (Exception e) {
            ui.showLoadingError();
            account = new Account();
        }
    }

    /**
     * This method prints a line that Duke will print out in the program.
     * @return a line that the program will print out in response to a user's commands
     */
    public String[] getResponse(String input) {
        try {
            ui.clearOutputString();
            ui.appendToOutput(ui.showLine());
            ui.clearGraphContainerString();

            boolean isNewUser = account.isToInitialize();
            MoneyCommand updateCommand = new AutoUpdateInstalmentCommand();
            updateCommand.execute(account, ui, moneyStorage);
            MoneyCommand c = Parser.moneyParse(input, isNewUser);
            if (c.isExit()) {
                moneyStorage.writeToFile(account);
                System.exit(0);
            } else if (!c.getClass().equals(UndoCommand.class)) {
                c.execute(account, ui, moneyStorage);
                undoCommandHandler.updateLastIssuedCommands(c);
            } else {
                c = undoCommandHandler.getLastIssuedCommand();
                c.undo(account, ui, moneyStorage);
            }

        } catch (ParseException | DukeException e) {
            ui.clearOutputString();
            ui.appendToOutput(ui.showError(e.getMessage()));
            ui.clearGraphContainerString();
            ui.appendToGraphContainer(ui.showError(e.getMessage()));
            return new String[]{ui.getOutputString(), ui.getGraphContainerString()};
        } finally {
            ui.appendToOutput(ui.showLine());
        }
        return new String[]{ui.getOutputString(), ui.getGraphContainerString()};
    }

    public Account getAccount() {
        return account;
    }

}
