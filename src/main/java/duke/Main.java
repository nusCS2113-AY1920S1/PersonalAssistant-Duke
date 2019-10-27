package duke;

import duke.logic.autocorrect.Autocorrect;
import duke.logic.commands.Command;
import duke.commons.exceptions.DukeException;
import duke.model.TransactionList;
import duke.storage.Storage;
import duke.model.MealList;
import duke.ui.InputHandler;
import duke.ui.Ui;
import duke.logic.parsers.Parser;
import duke.model.user.User;

/**
 * Main is a public class that contains the main function to drive the program.
 * It encapsulates a Storage object, a MealList object, and an Ui object.
 */
public class Main {
    private Storage storage;
    private MealList meals = new MealList();
    private Ui ui;
    private InputHandler in = new InputHandler(System.in);
    private User user;
    private Autocorrect autocorrect = new Autocorrect();
    private TransactionList transactions = new TransactionList();

    /**
     * This is a constructor of Duke to start the program.
     */
    public Main() {
        ui = new Ui();
        storage = new Storage();
        user = new User();
        try {
            storage.load(meals);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
            meals = new MealList();
        }
        try {
            user = storage.loadUser(); //load user info
        } catch (DukeException e) {
            ui.showUserLoadingError();
        }
        try {
            storage.loadWord(autocorrect);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        try {
            //TODO: Implement in different function
            storage.loadTransactions(transactions, user);
        } catch (DukeException e) {
            ui.showLoadinngTransactionError();
        }
    }

    /**
     *  Run is a function that generate the flow of duke program from beginning until the end.
     */
    public void run() {
        if (!user.getIsSetup()) {
            ui.showWelcomeNew();
        } else {
            ui.showWelcomeBack(user);
        }
        while (!user.getIsSetup()) { //setup user profile if it's empty
            try {
                user.setup();
                ui.showUserSetupDone(user);
                storage.saveUser(user);
            } catch (DukeException e) {
                ui.showMessage(e.getMessage());
            }
        }
        boolean isExit = false;
        ui.showWelcome();
        Parser userParser = new Parser(autocorrect);
        while (!isExit) {
            try {
                String fullCommand = in.getString();
                ui.showLine();
                Command c = userParser.parse(fullCommand);
                c.execute(meals, ui, storage, user, in, transactions);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showLine();
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