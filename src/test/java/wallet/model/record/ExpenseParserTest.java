package wallet.model.record;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseParserTest {
    @Test
    public void parseInput_recurringExpense_success() {
        Expense e = ExpenseParser.parseInput("Dinner $10 /on 10/10/2019 /cat Food /r daily");
        assertEquals("Dinner", e.getDescription());
        assertEquals("2019-10-10", e.getDate().toString());
        assertEquals(10.0, e.getAmount());
        assertEquals("Food", e.getCategory());
        assertEquals(true, e.isRecurring());
        assertEquals("DAILY", e.getRecFrequency());
    }

    @Test
    public void getRecurringRecords_populatedList_success() {
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense(new Expense("Lunch", LocalDate.now(), 5, "Food", false, "NULL"));
        expenseList.addExpense(new Expense("Dinner", LocalDate.now(), 10, "Food", false, "NULL"));
        expenseList.addExpense(new Expense("Breakfast", LocalDate.now(), 3, "Food", true, "WEEKLY"));

        for (Expense e : ExpenseParser.getRecurringRecords(expenseList)) {
            assertEquals("Breakfast", e.getDescription());
            assertEquals(LocalDate.now(), e.getDate());
            assertEquals(3.0, e.getAmount());
            assertEquals("Food", e.getCategory());
            assertEquals(true, e.isRecurring());
            assertEquals("WEEKLY", e.getRecFrequency());
        }
    }

    @Test
    public void populateRecurringRecords_dailyRecurring_success() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expenseDate = currentDate.minusDays(5);
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense(new Expense("Breakfast", expenseDate, 3, "Food", true, "DAILY"));

        ExpenseParser.populateRecurringRecords(expenseList);

        for (int i = 0; i < expenseList.getSize(); i++) {
            Expense e = expenseList.getExpense(i);
            if (i != expenseList.getSize() - 1) {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals("Food", e.getCategory());
                assertEquals(false, e.isRecurring());
                assertEquals("NULL", e.getRecFrequency());
            } else {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(expenseDate, e.getDate());
                assertEquals(3.0, e.getAmount());
                assertEquals("Food", e.getCategory());
                assertEquals(true, e.isRecurring());
                assertEquals("DAILY", e.getRecFrequency());
            }
            expenseDate = expenseDate.plusDays(1);
        }
    }
}
