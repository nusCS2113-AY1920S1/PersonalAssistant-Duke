package duke.functions;

import duke.logic.autocorrect.Autocorrect;
import duke.logic.commands.*;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.Parser;
import duke.storage.Storage;
import duke.model.user.User;
import duke.model.MealList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionTest {
    private Storage storage;
    private MealList tasks = new MealList();
    private Ui ui;
    private Scanner in = new Scanner(System.in);
    private User user;
    private Autocorrect autocorrect = new Autocorrect();

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
        try {
            c = parser.parse("add burger /calorie 100 /sodium 100 /fats 100");
            c.execute(tasks, ui, storage, user, in);
            c = parser.parse("breakfast burger");
            c.execute(tasks, ui, storage, user, in);
            c = parser.parse("breakfast burger /calorie 100");
            c.execute(tasks, ui, storage, user, in);
        } catch (DukeException e) {
            exceptionThrown1 = true;
        }
        try {
            c = parser.parse("breakfast taco");
            c.execute(tasks, ui, storage, user, in);
        } catch (DukeException e) {
            exceptionThrown2 = true;
        }
        assertFalse(exceptionThrown1);
        assertTrue(exceptionThrown2);
    }
}

