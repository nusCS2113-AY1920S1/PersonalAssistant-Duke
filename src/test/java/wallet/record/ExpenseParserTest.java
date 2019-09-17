package wallet.record;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseParserTest {
    @Test
    public void parseInput_recurringExpense_success() {
        Expense e = ExpenseParser.parseInput("Dinner $10 /on 10/10/2019 /cat Food /r daily");
        assertEquals("Dinner", e.getDescription());
        assertEquals("2019-10-10", e.getCreatedDate().toString());
        assertEquals(10.0, e.getAmount());
        assertEquals("Food", e.getCategory());
        assertEquals(true, e.isRecurring());
        assertEquals("DAILY", e.getRecFrequency());
    }

    @Test
    public void getRecurringRecords_populatedList_success() {
        ExpenseList eList = new ExpenseList();
        eList.addExpense(new Expense("Lunch", LocalDate.now(), 5, "Food", false, "NULL"));
        eList.addExpense(new Expense("Dinner", LocalDate.now(), 10, "Food", false, "NULL"));
        eList.addExpense(new Expense("Breakfast", LocalDate.now(), 3, "Food", true, "WEEKLY"));

        for (Expense e : ExpenseParser.getRecurringRecords(eList)) {
            assertEquals("Breakfast", e.getDescription());
            assertEquals(LocalDate.now(), e.getCreatedDate());
            assertEquals(3.0, e.getAmount());
            assertEquals("Food", e.getCategory());
            assertEquals(true, e.isRecurring());
            assertEquals("WEEKLY", e.getRecFrequency());
        }
    }

    @Test
    public void populateRecurringRecords_dailyRecurring_success() {
        LocalDate currentDate = LocalDate.now();
        LocalDate eDate = currentDate.minusDays(5);
        ExpenseList eList = new ExpenseList();
        eList.addExpense(new Expense("Breakfast", eDate, 3, "Food", true, "DAILY"));

        ExpenseParser.populateRecurringRecords(eList);

        for (int i = 0; i < eList.getSize(); i++) {
            Expense e = eList.getExpense(i);
            if (i != eList.getSize()-1) {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(eDate, e.getCreatedDate());
                assertEquals(3.0, e.getAmount());
                assertEquals("Food", e.getCategory());
                assertEquals(false, e.isRecurring());
                assertEquals("NULL", e.getRecFrequency());
            } else {
                assertEquals("Breakfast", e.getDescription());
                assertEquals(eDate, e.getCreatedDate());
                assertEquals(3.0, e.getAmount());
                assertEquals("Food", e.getCategory());
                assertEquals(true, e.isRecurring());
                assertEquals("DAILY", e.getRecFrequency());
            }
            eDate = eDate.plusDays(1);
        }
    }
}
