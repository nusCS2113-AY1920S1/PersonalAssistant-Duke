package wallet.model.record;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.logic.parser.ExpenseParser;
import wallet.model.Wallet;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseParserTest {
    private static Wallet testWallet;

    @BeforeAll
    public static void setUp() {
        testWallet = new Wallet();
    }

    @Test
    public void getRecurringRecords_populatedList_success() {
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense(new Expense("Lunch", LocalDate.now(), 5, Category.FOOD, false, null));
        expenseList.addExpense(new Expense("Dinner", LocalDate.now(), 10, Category.FOOD, false, null));
        expenseList.addExpense(new Expense("Breakfast", LocalDate.now(), 3, Category.FOOD, true, "WEEKLY"));

        for (Expense e : ExpenseParser.getRecurringRecords(expenseList)) {
            assertEquals("Breakfast", e.getDescription());
            assertEquals(LocalDate.now(), e.getDate());
            assertEquals(3.0, e.getAmount());
            assertEquals(Category.FOOD, e.getCategory());
            assertEquals(true, e.isRecurring());
            assertEquals("WEEKLY", e.getRecFrequency());
        }
    }

    @Test
    public void updateRecurringRecords_dailyRecurring_success() {
        ExpenseParser.updateRecurringRecords(testWallet);
        LocalDate currentDate = LocalDate.now();
        LocalDate expenseDate = currentDate.minusDays(5);
        ExpenseList expenseList = testWallet.getExpenseList();
        expenseList.addExpense(new Expense("Breakfast", expenseDate, 3, Category.FOOD, true, "DAILY"));

        for (int i = 0; i < expenseList.getSize(); i++) {
            Expense e = expenseList.getExpense(i);
            if (i != expenseList.getSize() - 1) {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals(Category.FOOD, e.getCategory());
                assertEquals(false, e.isRecurring());
                assertEquals(null, e.getRecFrequency());
            } else {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals(Category.FOOD, e.getCategory());
                assertEquals(true, e.isRecurring());
                assertEquals("DAILY", e.getRecFrequency());
            }
            expenseDate = expenseDate.plusDays(1);
        }
    }
}
