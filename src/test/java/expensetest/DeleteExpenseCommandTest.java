//@@author e0323290

package expensetest;


import gazeeebo.ui.Ui;
import gazeeebo.commands.expenses.DeleteExpenseCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing DeleteExpenseCommand for successful deletion
 * of expenses and deletion of nonexistent items.
 */
public class DeleteExpenseCommandTest {
    /**
     * Message shown when user tries to delete nonexistent item.
     */
    private static final String ITEM_NOT_FOUND_MESSAGE = "Item not found!\r\n";
    /**
     * Create UI object that deals with printing things to user.
     */
    private Ui ui = new Ui();
    /**
     * Output stream in which data is written into a byte array.
     */
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    /**
     * Print representation of actual data values.
     */
    private PrintStream mine = new PrintStream(output);
    /**
     * Print representation of original data values.
     */
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

    /**
     * Test when deletion of expense is successful.
     * @throws IOException Catch error when read file fails
     */
    @Test
    void testDeleteExpenseCommand() throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        Stack<Map<LocalDate, ArrayList<String>>> oldExpenses = new Stack<>();
        oldExpenses.push(expenses);
        ArrayList<String> itemAndPriceList1 = new ArrayList<>();
        itemAndPriceList1.add("book, $3");
        String bookDate = "2019-09-09";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookDateOfPurchase = LocalDate.parse(bookDate, fmt);
        expenses.put(bookDateOfPurchase, itemAndPriceList1);
        ui.fullCommand = "delete book";
        new DeleteExpenseCommand(ui, expenses, oldExpenses);
        assertEquals("Successfully deleted: book, $3"
                + " | bought on 2019-09-09\r\n", output.toString());
    }

    /**
     * Test deleting of nonexistent item.
     * @throws IOException Catch error when read file fails
     */
    @Test
    void testDeleteNotInExpenseCommand() throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();

        Stack<Map<LocalDate, ArrayList<String>>> oldExpenses = new Stack<>();
        oldExpenses.push(expenses);
        ArrayList<String> itemAndPriceList1 = new ArrayList<>();
        itemAndPriceList1.add("book, $3");
        String bookDate = "2019-09-09";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookDateOfPurchase = LocalDate.parse(bookDate, fmt);
        expenses.put(bookDateOfPurchase, itemAndPriceList1);
        ui.fullCommand = "delete bread";
        new DeleteExpenseCommand(ui, expenses, oldExpenses);
        assertItemNotFoundErrorMessageDisplayed();
    }

    /**
     * Display assertion of deletion of nonexistent item format error message.
     */
    private void assertItemNotFoundErrorMessageDisplayed() {
        assertEquals(ITEM_NOT_FOUND_MESSAGE, output.toString());
    }
}
