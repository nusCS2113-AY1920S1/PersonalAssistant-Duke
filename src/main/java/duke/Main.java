package duke;

import duke.logic.commands.Command;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.UserSetup;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.model.meal.MealList;
import duke.ui.Ui;
import duke.logic.parsers.Parser;
import duke.model.user.User;
import duke.logic.autocorrect.Autocorrect;

import java.util.Scanner;

/**
 * Main is a public class that contains the main function to drive the program.
 * It encapsulates a Storage object, a MealList object, and an Ui object.
 */
public class Main {

    private Storage storage;
    private MealList tasks = new MealList();
    private Ui ui;
    private Scanner in = new Scanner(System.in);
    private User user;
    private Autocorrect autocorrect;
    private TransactionList transactions = new TransactionList();
    private UserSetup setup;
    private Wallet wallet;

    /**
     * This is a constructor of Duke to start the program.
     */
    public Main() {
        ui = new Ui();
        storage = new Storage();
        user = new User();
        setup = new UserSetup(user);
        autocorrect = new Autocorrect();
        wallet = new Wallet();
        try {
            storage.load(tasks);
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new MealList();
        }
        try {
            user = storage.loadUser(); //load user inf
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
            ui.showUserLoadingError();
        }
        setup = new UserSetup(user);
        try {
            storage.loadWord(autocorrect);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        try {
            //TODO: Implement in different function
        storage.loadTransactions(transactions, wallet);
        } catch (DukeException e) {
            ui.showLoadinngTransactionError();
        }
    }

    /**
     *  Run is a function that generate the flow of duke program from beginning until the end.
     */
    public void run() {
        setup.start();
        while (!setup.getIsDone()) { //setup user profile if it's empty
            String info = in.nextLine();
            setup.initialise(info);
        }
        user = setup.getUser();
        boolean isExit = false;
        Parser userParser = new Parser(autocorrect);
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(in);
                ui.showLine();
                Command c = userParser.parse(fullCommand);
                c.execute(tasks, storage, user, wallet);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * This is the main function.
     */
    public static void main(String[] args) {
        new Main().run();
    }
}