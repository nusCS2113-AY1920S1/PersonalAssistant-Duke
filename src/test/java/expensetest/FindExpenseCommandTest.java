//@@author e0323290

package expensetest;


import gazeeebo.ui.Ui;
import gazeeebo.commands.expenses.FindExpenseCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindExpenseCommandTest {
    private static final String WRONG_DATE_FORMAT_ERROR_MESSAGE = "Wrong date format\r\n";
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
    void testFindExpenseCommand() {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ArrayList<String> itemAndPriceList1 = new ArrayList<>();
        ArrayList<String> itemAndPriceList2 = new ArrayList<>();

        itemAndPriceList1.add("book, $3");
        itemAndPriceList2.add("bread, $2.50");
        String bookDate = "2019-09-09";
        String breadDate = "2019-04-04";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookDateOfPurchase = LocalDate.parse(bookDate, fmt);
        LocalDate breadDateOfPurchase = LocalDate.parse(breadDate, fmt);
        expenses.put(bookDateOfPurchase, itemAndPriceList1);
        expenses.put(breadDateOfPurchase, itemAndPriceList2);
        ui.fullCommand = "find 2019-09-09";
        FindExpenseCommand test = new FindExpenseCommand(ui, expenses);
        assertEquals("1.book, $3\r\n", output.toString());

    }

    @Test
    void testUnableFindExpenseCommand() {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ArrayList<String> itemAndPriceList1 = new ArrayList<>();
        ArrayList<String> itemAndPriceList2 = new ArrayList<>();

        itemAndPriceList1.add("book, $3");
        itemAndPriceList2.add("bread, $2.50");
        String bookDate = "2019-09-09";
        String breadDate = "2019-04-04";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookDateOfPurchase = LocalDate.parse(bookDate, fmt);
        LocalDate breadDateOfPurchase = LocalDate.parse(breadDate, fmt);
        expenses.put(bookDateOfPurchase, itemAndPriceList1);
        expenses.put(breadDateOfPurchase, itemAndPriceList2);
        ui.fullCommand = "find 2018-03-09";
        new FindExpenseCommand(ui, expenses);
        assertEquals("2018-03-09 is not found in the list.\r\n", output.toString());
    }

    @Test
    void testIncorrectDateFormatFindExpenseCommand() {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ArrayList<String> itemAndPriceList1 = new ArrayList<>();
        ArrayList<String> itemAndPriceList2 = new ArrayList<>();

        itemAndPriceList1.add("book, $3");
        itemAndPriceList2.add("bread, $2.50");
        String bookDate = "2019-09-09";
        String breadDate = "2019-04-04";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookDateOfPurchase = LocalDate.parse(bookDate, fmt);
        LocalDate breadDateOfPurchase = LocalDate.parse(breadDate, fmt);
        expenses.put(bookDateOfPurchase, itemAndPriceList1);
        expenses.put(breadDateOfPurchase, itemAndPriceList2);
        ui.fullCommand = "find 2019-9-9";
        new FindExpenseCommand(ui, expenses);
        assertWrongDateFormatErrorMessageDisplayed();
    }

    @Test
    void testIncorrectFormatFindExpenseCommand() {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ArrayList<String> itemAndPriceList1 = new ArrayList<>();
        ArrayList<String> itemAndPriceList2 = new ArrayList<>();

        itemAndPriceList1.add("book, $3");
        itemAndPriceList2.add("bread, $2.50");
        String bookDate = "2019-09-09";
        String breadDate = "2019-04-04";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookDateOfPurchase = LocalDate.parse(bookDate, fmt);
        LocalDate breadDateOfPurchase = LocalDate.parse(breadDate, fmt);
        expenses.put(bookDateOfPurchase, itemAndPriceList1);
        expenses.put(breadDateOfPurchase, itemAndPriceList2);
        ui.fullCommand = "find";
        new FindExpenseCommand(ui, expenses);
        assertIncorrectFormatErrorMessageDisplayed();

    }

    private void assertWrongDateFormatErrorMessageDisplayed() {
        assertEquals(WRONG_DATE_FORMAT_ERROR_MESSAGE, output.toString());
    }

    private void assertIncorrectFormatErrorMessageDisplayed() {
        assertEquals(INCORRECT_FORMAT_ERROR_MESSAGE, output.toString());
    }
}
