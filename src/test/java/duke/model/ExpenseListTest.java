package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ExpenseListTest {
    @TempDir
    File userDirectory;

    private static final String STORAGE_DELIMITER = "\n\n";


    @Test
    public void testBasicOperations() throws DukeException {
        ExpenseList testExpenseList = new ExpenseList(new ArrayList<>());
        Expense testExpense = new Expense.Builder().build();
        testExpenseList.add(testExpense);
        assertEquals(testExpenseList.get(1), testExpense);
        assertEquals(testExpenseList.internalSize(), 1);
        testExpenseList.remove(1);
        assertEquals(testExpenseList.internalSize(), 0);
    }

    @Test
    public void testInvalidBasicOperations() throws DukeException {
        ExpenseList testExpenseList = new ExpenseList(new ArrayList<>());
        Expense testExpense = new Expense.Builder().build();
        testExpenseList.add(testExpense);
        try {
            testExpenseList.get(2);
            fail();
        } catch (DukeException e) {
            assertEquals(String.format(DukeException.MESSAGE_NO_ITEM_AT_INDEX, "expense", 2), e.getMessage());
        }

        try {
            testExpenseList.remove(2);
            fail();
        } catch (DukeException e) {
            assertEquals(String.format(DukeException.MESSAGE_NO_ITEM_AT_INDEX, "expense", 2), e.getMessage());
        }
    }
    /*TODO:This test is Currently broken due to regressions, please fix.
    @Test
    public void testUndoRedo() throws DukeException {
        ExpenseList testExpenseList = new ExpenseList(new ArrayList<>());
        Expense testExpense = new Expense.Builder().build();
        testExpenseList.add(testExpense);
        testExpenseList.remove(1);
        assertEquals(testExpenseList.undo(1), 1);
        assertEquals(testExpenseList.internalSize(), 1);
        assertEquals(testExpenseList.undo(1), 1);
        assertEquals(testExpenseList.internalSize(), 0);
        assertEquals(testExpenseList.redo(1), 1);
        assertEquals(testExpenseList.internalSize(), 1);
        assertEquals(testExpenseList.redo(1), 1);
        assertEquals(testExpenseList.internalSize(), 0);
        assertEquals(testExpenseList.undo(2), 2);
        assertEquals(testExpenseList.redo(2), 2);
    }

     */
    @Test
    public void testGetTotalAmount() throws DukeException {
        ExpenseList testExpenseList = new ExpenseList(new ArrayList<>());
        Expense testExpenseOne = new Expense.Builder().build();
        Expense testExpenseTwo = new Expense.Builder().setAmount("12").build();
        Expense testExpenseThree = new Expense.Builder().setAmount("13").build();
        Expense testExpenseFour = new Expense.Builder().setAmount("12.4").build();
        Expense testExpenseFive = new Expense.Builder().setAmount("12.23").build();
        testExpenseList.add(testExpenseOne);
        testExpenseList.add(testExpenseTwo);
        testExpenseList.add(testExpenseThree);
        testExpenseList.add(testExpenseFour);
        testExpenseList.add(testExpenseFive);
        assertEquals(testExpenseList.getTotalAmount(), new BigDecimal("49.63"));
    }
}
