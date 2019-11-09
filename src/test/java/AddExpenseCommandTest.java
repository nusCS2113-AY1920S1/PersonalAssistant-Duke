import gazeeebo.UI.Ui;
import gazeeebo.commands.expenses.AddExpenseCommand;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddExpenseCommandTest {
    private static final String PRICE_FORMAT_ERROR_MESSAGE = "4\r\nPlease key in the correct format for money: $__\r\n";
    private static final String WRONG_DATE_FORMAT_ERROR_MESSAGE = "$\r\nWrong date format\r\n";
private static final String INCORRECT_FORMAT_ERROR_MESSAGE = "Please input in the correct format\n";
    private Ui ui = new Ui();
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
    void testAddExpenseCommand() throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ui.fullCommand = "add coffee, $4, 2019-09-09";
        new AddExpenseCommand(ui, expenses);
        assertEquals("$\r\nSuccessfully added: \n" + "coffee, $4, bought on 2019-09-09\r\n", output.toString());
    }

    @Test
    void testAddWrongFormatExpenseCommand() throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ui.fullCommand = "add coffee$42019-09-09";
        new AddExpenseCommand(ui, expenses);
        assertIncorrectFormatErrorMessageDisplayed();
    }

    @Test
    void testAddWrongDateTimeFormatExpenseCommand() throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ui.fullCommand = "add coffee,$4,2019-9-09";
        new AddExpenseCommand(ui, expenses);
        assertWrongDateFormatErrorMessageDisplayed();
    }

    @Test
    void testAddWrongPriceFormatExpenseCommand() throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ui.fullCommand = "add coffee,4,2019-09-09";
        new AddExpenseCommand(ui, expenses);
        assertPriceFormatErrorMessageDisplayed();
    }

    private void assertPriceFormatErrorMessageDisplayed() {
        assertEquals(PRICE_FORMAT_ERROR_MESSAGE, output.toString());
    }
    private void assertWrongDateFormatErrorMessageDisplayed() {
        assertEquals(WRONG_DATE_FORMAT_ERROR_MESSAGE, output.toString());
    }
    private void assertIncorrectFormatErrorMessageDisplayed() {
        assertEquals(INCORRECT_FORMAT_ERROR_MESSAGE, output.toString());

    }
}
