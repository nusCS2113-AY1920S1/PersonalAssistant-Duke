package dukeobjects;

import exception.DukeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ExpenseListTest {
    @TempDir
    File USER_DIR;

    private static final String STORAGE_DELIMITER = "\n\n";


    @Test
    public void testBasicOperations() throws FileNotFoundException {
        ExpenseList testExpenseList = new ExpenseList(USER_DIR);
        Expense testExpense = new Expense.Builder().build();
        testExpenseList.add(testExpense);
        testExpenseList.update();
        assertEquals(testExpenseList.get(1), testExpense);
        assertEquals(testExpenseList.internalSize(), 1);
        testExpenseList.remove(1);
        testExpenseList.update();
        assertEquals(testExpenseList.internalSize(), 0);
    }

    @Test
    public void testInvalidBasicOperations() {
        ExpenseList testExpenseList = new ExpenseList(USER_DIR);
        Expense testExpense = new Expense.Builder().build();
        testExpenseList.add(testExpense);
        testExpenseList.update();
        try {
            testExpenseList.get(2);
            fail();
        } catch (DukeException e) {
            // Success
            // todo: Test that the error message is correct when it's implemented
            e.printStackTrace();
        }

        try {
            testExpenseList.remove(2);
            fail();
        } catch (DukeException e) {
            // Success
            // todo: Test that the error message is correct when it's implemented
            e.printStackTrace();
        }
    }

    @Test
    public void testUndoRedo() {
        ExpenseList testExpenseList = new ExpenseList(USER_DIR);
        Expense testExpense = new Expense.Builder().build();
        testExpenseList.add(testExpense);
        testExpenseList.update();
        testExpenseList.remove(1);
        testExpenseList.update();
        testExpenseList.undo(1);
        assertEquals(testExpenseList.internalSize(), 1);
        testExpenseList.undo(1);
        assertEquals(testExpenseList.internalSize(), 0);
        try {
            testExpenseList.undo(1);
            fail();
        } catch (DukeException e) {
            // Success
            // todo: Test that the error message is correct when it's implemented
            e.printStackTrace();
        }
        testExpenseList.redo(1);
        assertEquals(testExpenseList.internalSize(), 1);
        testExpenseList.redo(1);
        assertEquals(testExpenseList.internalSize(), 0);
        try {
            testExpenseList.redo(1);
            fail();
        } catch (DukeException e) {
            // Success
            // todo: Test that the error message is correct when it's implemented
            e.printStackTrace();
        }
        testExpenseList.undo(1);
        testExpenseList.update();
        try {
            testExpenseList.redo(1);
            fail();
        } catch (DukeException e) {
            // Success
            // todo: Test that the error message is correct when it's implemented
            e.printStackTrace();
        }
    }
}
