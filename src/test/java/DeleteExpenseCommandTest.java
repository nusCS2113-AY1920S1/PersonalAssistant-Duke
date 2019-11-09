import gazeeebo.UI.Ui;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteExpenseCommandTest {
    private static final String ITEM_NOT_FOUND_MESSAGE = "Item not found!\r\n";
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
    void testDeleteExpenseCommand() throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ArrayList<String> itemAndPriceList1 = new ArrayList<>();
        itemAndPriceList1.add("book, $3");
        String bookDate = "2019-09-09";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookDateOfPurchase = LocalDate.parse(bookDate, fmt);
        expenses.put(bookDateOfPurchase, itemAndPriceList1);
        ui.fullCommand = "delete book";
        new DeleteExpenseCommand(ui, expenses);
        assertEquals("Successfully deleted: book, $3 | bought on 2019-09-09\r\n", output.toString());
    }

    @Test
    void testDeleteNotInExpenseCommand() throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = new HashMap<>();
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<>();
        ArrayList<String> itemAndPriceList1 = new ArrayList<>();
        itemAndPriceList1.add("book, $3");
        String bookDate = "2019-09-09";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookDateOfPurchase = LocalDate.parse(bookDate, fmt);
        expenses.put(bookDateOfPurchase, itemAndPriceList1);
        ui.fullCommand = "delete bread";
        new DeleteExpenseCommand(ui, expenses);
        assertItemNotFoundErrorMessageDisplayed();
    }

    private void assertItemNotFoundErrorMessageDisplayed() {
        assertEquals(ITEM_NOT_FOUND_MESSAGE, output.toString());
    }
}
