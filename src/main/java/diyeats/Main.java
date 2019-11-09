package diyeats;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.logic.commands.Command;
import diyeats.logic.commands.UserSetup;
import diyeats.logic.parsers.Parser;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.TransactionList;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;
import diyeats.ui.Ui;

import java.util.Scanner;

/**
 * Main is a public class that contains the main function to drive the program.
 * It encapsulates a Storage object, a MealList object, and an Ui object.
 */
public class Main {
    private Storage storage;
    private MealList meals = new MealList();
    private Ui ui;
    private Scanner in = new Scanner(System.in);
    private User user;
    private Autocorrect autocorrect;
    private TransactionList transactions = new TransactionList();
    private UserSetup setup;
    private Wallet wallet;

    /**
     * This is a constructor of DIYeats to start the program.
     */
    public Main() {
        ui = new Ui();
        user = new User();
        autocorrect = new Autocorrect();
        wallet = new Wallet();
        storage = new Storage();
        while (!storage.getMealIsDone()) {
            try {
                storage.loadMealInfo(meals);
            } catch (ProgramException e) {
                ui.showMessage(e.getMessage());
            }
        }
        try {
            user = storage.loadUser(); //load user info
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
            ui.showUserLoadingError();
        }
        setup = new UserSetup(user);
        try {
            storage.loadWord(autocorrect);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        try {
            storage.loadTransactions(wallet);
        } catch (ProgramException e) {
            ui.showLoadingTransactionError();
        }
    }

    /**
     *  Run is a function that generate the flow of DIYeats program from beginning until the end.
     */
    public void run() {
        setup.start();
        while (!setup.getIsDone()) { //setup user profile if it's empty
            String info = in.nextLine();
            setup.initialise(info);
        }
        user = setup.getUser();
        try {
            storage.updateUser(user);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        boolean isExit = false;
        Parser userParser = new Parser(autocorrect);
        while (!isExit) {
            try {
                String fullCommand = in.nextLine();
                Command c = userParser.parse(fullCommand);
                if (c.isFail()) {
                    c.failure();
                } else {
                    c.execute(meals, storage, user, wallet);
                    while (!c.isDone()) {
                        String word = in.nextLine();
                        c.setResponseStr(word);
                        c.execute(meals, storage, user, wallet);
                    }
                }
                isExit = c.isExit();
            } catch (ProgramException e) {
                ui.showMessage(e.getMessage());
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