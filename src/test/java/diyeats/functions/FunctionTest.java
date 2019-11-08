/*
import diyeats.commons.exceptions.DukeException;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.logic.commands.Command;
import diyeats.logic.parsers.Parser;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.TransactionList;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;
import diyeats.ui.InputHandler;
import diyeats.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionTest {
    private Storage storage;
    private MealList tasks = new MealList();
    private Ui ui;
    private InputHandler in = new InputHandler(System.in);
    private User user;
    private Autocorrect autocorrect = new Autocorrect();
    private Wallet wallet;

    void setup() {
        ui = new Ui();
        storage = new Storage();
        user = new User();
        autocorrect = new Autocorrect();
        try {
            storage.load(tasks);
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new MealList();
        }
        try {
            user = storage.loadUser();
        } catch (DukeException e) {
            ui.showUserLoadingError();
        }
        try {
            storage.loadWord(autocorrect);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
    }

    @Test
    void addCommand() {
        boolean exceptionThrown1 = false;
        boolean exceptionThrown2 = false;
        setup();
        Command c;
        Parser parser = new Parser(autocorrect);
        TransactionList transactionList = new TransactionList();
        try {
            c = parser.parse("add burger /calorie 100 /sodium 100 /fats 100");
            c.execute(tasks, storage, user, wallet);
            c = parser.parse("breakfast burger");
            c.execute(tasks, storage, user, wallet);
            c = parser.parse("breakfast burger /calorie 100");
            c.execute(tasks, storage, user, wallet);
        } catch (DukeException e) {
            exceptionThrown1 = true;
        }
        try {
            c = parser.parse("breakfast taco");
            c.execute(tasks, storage, user, wallet);
        } catch (DukeException e) {
            exceptionThrown2 = true;
        }
        assertFalse(exceptionThrown1);
        assertTrue(exceptionThrown2);
    }
}*/

