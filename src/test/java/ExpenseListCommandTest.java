//@@author e0323290
import gazeeebo.commands.expenses.ExpenseListCommand;
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

public class ExpenseListCommandTest {
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
    void testExpenseListCommand() {
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

        new ExpenseListCommand(expenses);
        assertEquals("Here is the list of your expenses:\r\n"
        + "1. bread, $2.50 | bought on 2019-04-04\r\n"
        + "2. book, $3 | bought on 2019-09-09\r\n", output.toString());
    }
}
