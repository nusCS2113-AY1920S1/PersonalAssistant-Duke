package wallet.model.record;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wallet.logic.parser.ExpenseParser;
import wallet.model.Wallet;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseParserTest {
    //@@author kyang96
    private static Wallet testWallet;

    @BeforeEach
    public void setUp() {
        testWallet = new Wallet();
    }

    @Test
    public void getRecurringRecords_populatedList_success() {
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense(new Expense("Lunch", LocalDate.now(), 5, Category.FOOD, false, RecurrenceRate.NO));
        expenseList.addExpense(new Expense("Dinner", LocalDate.now(), 10, Category.FOOD, false, RecurrenceRate.NO));
        expenseList.addExpense(new Expense("Breakfast", LocalDate.now(), 3,
                Category.FOOD, true, RecurrenceRate.WEEKLY));

        for (Expense e : ExpenseParser.getRecurringRecords(expenseList)) {
            assertEquals("Breakfast", e.getDescription());
            assertEquals(LocalDate.now(), e.getDate());
            assertEquals(3.0, e.getAmount());
            assertEquals(Category.FOOD, e.getCategory());
            assertEquals(true, e.isRecurring());
            assertEquals(RecurrenceRate.WEEKLY, e.getRecFrequency());
        }
    }

    @Test
    public void updateRecurringRecords_dailyRecurring_success() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expenseDate = currentDate;
        ArrayList<Expense> testList = new ArrayList<>();
        testList.add(new Expense("Breakfast", expenseDate, 3,
                Category.FOOD, true, RecurrenceRate.DAILY));
        testWallet.getExpenseList().setExpenseList(testList);
        ExpenseParser.updateRecurringRecords(testWallet);
        ExpenseList expenseList = testWallet.getExpenseList();
        ExpenseParser.updateRecurringRecords(testWallet);

        for (int i = 0; i < expenseList.getSize(); i++) {
            Expense e = expenseList.getExpense(i);
            if (i != expenseList.getSize() - 1) {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals(Category.FOOD, e.getCategory());
                assertEquals(false, e.isRecurring());
                assertEquals(RecurrenceRate.NO, e.getRecFrequency());
            } else {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals(Category.FOOD, e.getCategory());
                assertEquals(true, e.isRecurring());
                assertEquals(RecurrenceRate.DAILY, e.getRecFrequency());
            }
            expenseDate = expenseDate.plusDays(1);
        }
    }

    @Test
    public void updateRecurringRecords_weeklyRecurring_success() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expenseDate = currentDate;
        ArrayList<Expense> testList = new ArrayList<>();
        testList.add(new Expense("Breakfast", expenseDate, 3,
                Category.FOOD, true, RecurrenceRate.WEEKLY));
        testWallet.getExpenseList().setExpenseList(testList);
        ExpenseParser.updateRecurringRecords(testWallet);
        ExpenseList expenseList = testWallet.getExpenseList();
        ExpenseParser.updateRecurringRecords(testWallet);

        for (int i = 0; i < expenseList.getSize(); i++) {
            Expense e = expenseList.getExpense(i);
            if (i != expenseList.getSize() - 1) {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals(Category.FOOD, e.getCategory());
                assertEquals(false, e.isRecurring());
                assertEquals(RecurrenceRate.NO, e.getRecFrequency());
            } else {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals(Category.FOOD, e.getCategory());
                assertEquals(true, e.isRecurring());
                assertEquals(RecurrenceRate.WEEKLY, e.getRecFrequency());
            }
            expenseDate = expenseDate.plusDays(7);
        }
    }

    @Test
    public void updateRecurringRecords_monthlyRecurring_success() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expenseDate = currentDate.minusMonths(2);
        ArrayList<Expense> testList = new ArrayList<>();
        testList.add(new Expense("Breakfast", expenseDate, 3,
                Category.FOOD, true, RecurrenceRate.MONTHLY));
        testWallet.getExpenseList().setExpenseList(testList);
        ExpenseParser.updateRecurringRecords(testWallet);
        ExpenseList expenseList = testWallet.getExpenseList();
        ExpenseParser.updateRecurringRecords(testWallet);

        for (int i = 0; i < expenseList.getSize(); i++) {
            Expense e = expenseList.getExpense(i);
            if (i != expenseList.getSize() - 1) {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals(Category.FOOD, e.getCategory());
                assertEquals(false, e.isRecurring());
                assertEquals(RecurrenceRate.NO, e.getRecFrequency());
            } else {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals(Category.FOOD, e.getCategory());
                assertEquals(true, e.isRecurring());
                assertEquals(RecurrenceRate.MONTHLY, e.getRecFrequency());
            }
            expenseDate = expenseDate.plusMonths(1);
        }
    }
}
