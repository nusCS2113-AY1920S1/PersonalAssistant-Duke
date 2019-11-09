package wallet.model.record;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseListTest {
    //@@author kyang96
    ExpenseList expenseList = new ExpenseList();

    @Test
    public void addExpense_nonRecurringExpense_success() {
        Expense e = new Expense("Lunch", LocalDate.now(), 3, Category.FOOD, false, RecurrenceRate.NO);
        expenseList.addExpense(e);
        Expense expense = expenseList.getExpense(0);
        assertAll("Expense should contain correct input values",
            () -> assertEquals("Lunch", expense.getDescription()),
            () -> assertEquals(LocalDate.now(), expense.getDate()),
            () -> assertEquals(3.0, expense.getAmount()),
            () -> assertEquals(Category.FOOD, expense.getCategory()),
            () -> assertEquals(false, expense.isRecurring()),
            () -> assertEquals(RecurrenceRate.NO, expense.getRecFrequency())
        );
    }

    @Test
    public void addExpense_recurringDailyExpense_success() {
        Expense e = new Expense("Lunch", LocalDate.now(), 3, Category.FOOD, true, RecurrenceRate.DAILY);
        expenseList.addExpense(e);
        Expense expense = expenseList.getExpense(0);
        assertAll("Expense should contain correct input values",
            () -> assertEquals("Lunch", expense.getDescription()),
            () -> assertEquals(LocalDate.now(), expense.getDate()),
            () -> assertEquals(3.0, expense.getAmount()),
            () -> assertEquals(Category.FOOD, expense.getCategory()),
            () -> assertEquals(true, expense.isRecurring()),
            () -> assertEquals(RecurrenceRate.DAILY, expense.getRecFrequency())
        );
    }

    @Test
    public void editExpense_validExpense_success() {
        Expense e = new Expense("Lunch", LocalDate.now(), 3, Category.FOOD, false, RecurrenceRate.NO);
        expenseList.addExpense(e);
        e.setDescription("Dinner");
        e.setAmount(5);
        expenseList.editExpense(0, e);
        Expense expense = expenseList.getExpense(0);
        assertAll("Expense should contain correct edited values",
            () -> assertEquals("Dinner", expense.getDescription()),
            () -> assertEquals(LocalDate.now(), expense.getDate()),
            () -> assertEquals(5.0, expense.getAmount()),
            () -> assertEquals(Category.FOOD, expense.getCategory()),
            () -> assertEquals(false, expense.isRecurring()),
            () -> assertEquals(RecurrenceRate.NO, expense.getRecFrequency())
        );
    }

    @Test
    public void findExpenseIndex_validExpense_success() {
        Expense e = new Expense("Lunch", LocalDate.now(), 3, Category.FOOD, false, RecurrenceRate.NO);
        expenseList.addExpense(e);
        int index = expenseList.findExpenseIndex(e);
        assertEquals(0, index);
    }

    @Test
    public void deleteExpense_validId_success() {
        Expense e = new Expense("Phone Bills", LocalDate.now(), 40, Category.BILLS, true, RecurrenceRate.MONTHLY);
        expenseList.addExpense(e);
        Expense expense = expenseList.deleteExpense(1);
        assertAll("Deleted expense should contain correct values",
            () -> assertEquals("Phone Bills", expense.getDescription()),
            () -> assertEquals(LocalDate.now(), expense.getDate()),
            () -> assertEquals(40.0, expense.getAmount()),
            () -> assertEquals(Category.BILLS, expense.getCategory()),
            () -> assertEquals(true, expense.isRecurring()),
            () -> assertEquals(RecurrenceRate.MONTHLY, expense.getRecFrequency())
        );
    }

    @Test
    public void deleteExpense_invalidId_nullReturned() {
        Expense expense = expenseList.deleteExpense(10);
        assertEquals(null, expense);
    }

    @Test
    public void findIndexWithId_validId_success() {
        Expense e = new Expense("Formal Shirt", LocalDate.now(), 40, Category.SHOPPING, false, RecurrenceRate.NO);
        expenseList.addExpense(e);
        int index = expenseList.findIndexWithId(1);
        assertEquals(0, index);
    }

    @Test
    public void findIndexWithId_invalidId_success() {
        Expense e = new Expense("T-Shirt", LocalDate.now(), 10, Category.SHOPPING, false, RecurrenceRate.NO);
        expenseList.addExpense(e);
        int index = expenseList.findIndexWithId(5);
        assertEquals(-1, index);
    }

    @Test
    public void findExpenseWithId_validId_success() {
        Expense e = new Expense("Electricity bills", LocalDate.now(), 80.0,
                Category.BILLS, true, RecurrenceRate.MONTHLY);
        expenseList.addExpense(e);
        Expense expense = expenseList.findExpenseWithId(1);
        assertAll("Expense should contain correct values",
            () -> assertEquals("Electricity bills", expense.getDescription()),
            () -> assertEquals(LocalDate.now(), expense.getDate()),
            () -> assertEquals(80.0, expense.getAmount()),
            () -> assertEquals(Category.BILLS, expense.getCategory()),
            () -> assertEquals(true, expense.isRecurring()),
            () -> assertEquals(RecurrenceRate.MONTHLY, expense.getRecFrequency())
        );
    }

    @Test
    public void findExpenseWithId_invalidId_nullReturned() {
        Expense expense = expenseList.findExpenseWithId(-1);
        assertEquals(null, expense);
    }
}
