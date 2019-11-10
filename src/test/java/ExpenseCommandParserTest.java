//@@author e0323290

import gazeeebo.parsers.ExpenseCommandParser;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseCommandParserTest {
    private static final String WELCOME_MESSAGE = "Welcome to your expenses record!"
            + " What would you like to do?\n\n\r\n"
            + "_________________________"
            + "_________________________________\n"
            + "1. Add expenses command: add item, price, date\n"
            + "2. Find expenses on a certain date: "
            + "find yyyy-MM-dd\n"
            + "3. Delete a certain expense: delete OR delete ITEM_NAME\n"
            + "4. See your expense list: list\n"
            + "5. Undo Command: undo\n"
            + "6. List of commands for expenses page: commands\n"
            + "7. Help page: help\n"
            + "8. Exit Expense page: esc\n"
            + "_________________________"
            + "_________________________________"
            + "\r\n"
            + "Going back to Main Menu...\n"
            + "Content Page:\n"
            + "------------------ \n"
            + "1. help\n"
            + "2. contacts\n"
            + "3. expenses\n"
            + "4. places\n"
            + "5. tasks\n"
            + "6. cap\n"
            + "7. spec\n"
            + "8. moduleplanner\n"
            + "9. notes\n"
            + "To exit: bye\n\r\n";
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void testExpenseCommand() throws IOException, ParseException, DukeException {
        ExpenseCommandParser expenseCommandParser = new ExpenseCommandParser();
        ArrayList<Task> list = new ArrayList<>();
        Ui ui = new Ui();
        ArrayList<Task> deletedTask = new ArrayList<>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        Storage storage = new Storage();
        TriviaManager triviaManager = new TriviaManager(storage);
        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
        System.setIn(in);
        expenseCommandParser.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
        assertWelcomeMessageDisplay();
    }

    private void assertWelcomeMessageDisplay() {
        assertEquals(WELCOME_MESSAGE, output.toString());
    }
}

