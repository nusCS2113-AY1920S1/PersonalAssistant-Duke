package wallet.thread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.RecurrenceRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChartThreadTest {
    ArrayList<Expense> expenseList = new ArrayList<>();
    TreeMap<LocalDate, Double> testMap = new TreeMap<>();

    /**
     * Generate test data in expense list.
     */
    @BeforeEach
    public void setUp() {
        Expense e1 = new Expense("Lunch", LocalDate.parse("2019-10-01"), 3,
                Category.FOOD, false, RecurrenceRate.NO);
        Expense e2 = new Expense("Dinner", LocalDate.parse("2019-10-15"), 5,
                Category.FOOD, false, RecurrenceRate.NO);
        Expense e3 = new Expense("Lunch", LocalDate.parse("2019-10-31"), 10,
                Category.FOOD, false, RecurrenceRate.NO);
        Expense e4 = new Expense("Shirt", LocalDate.parse("2019-10-10"), 20,
                Category.SHOPPING, false, RecurrenceRate.NO);
        Expense e5 = new Expense("Train Concession", LocalDate.parse("2019-10-01"), 50,
                Category.TRANSPORT, false, RecurrenceRate.NO);
        Expense e6 = new Expense("Phone Bill", LocalDate.parse("2019-10-25"), 40,
                Category.BILLS, false, RecurrenceRate.NO);
        expenseList.add(e1);
        expenseList.add(e2);
        expenseList.add(e3);
        expenseList.add(e4);
        expenseList.add(e5);
        expenseList.add(e6);
        testMap.put(LocalDate.parse("2019-10-01"), 53.0);
        testMap.put(LocalDate.parse("2019-10-10"), 20.0);
        testMap.put(LocalDate.parse("2019-10-15"), 5.0);
        testMap.put(LocalDate.parse("2019-10-25"), 40.0);
        testMap.put(LocalDate.parse("2019-10-31"), 10.0);
    }

    @Test
    public void generateExpenseMap_populatedExpenseList() {
        TreeMap<LocalDate, Double> expenseMap = ChartThread.generateExpenseMap(expenseList);
        assertTrue(testMap.equals(expenseMap));
    }

    @Test
    public void generateExpenseMap_emptyExpenseList() {
        ArrayList<Expense> emptyList = new ArrayList<>();
        TreeMap<LocalDate, Double> emptyMap = ChartThread.generateExpenseMap(emptyList);
        testMap = new TreeMap<>();
        assertTrue(testMap.equals(emptyMap));

    }
}
