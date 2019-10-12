package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.record.Expense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCommandTest {
    private static Wallet testWallet = new Wallet();

    @BeforeAll
    public static void setUp() {
        testWallet.getExpenseList().addExpense(new Expense("Lunch", LocalDate.now(), 3, "Food", false, null));
        testWallet.getExpenseList().addExpense(new Expense("Dinner", LocalDate.now(), 5, "Food", false, null));
    }

    @Test
    public void execute_validId_success() {
        DeleteCommand command = new DeleteCommand("expense", 1);
        command.execute(testWallet);
        Expense e = testWallet.getExpenseList().findExpenseWithId(1);
        assertEquals(null, e);

        e = testWallet.getExpenseList().findExpenseWithId(2);
        assertEquals(2, e.getId());
        assertEquals("Dinner", e.getDescription());
        assertEquals(LocalDate.now(), e.getDate());
        assertEquals(5.0, e.getAmount());
        assertEquals("Food", e.getCategory());
        assertEquals(false, e.isRecurring());
        assertEquals(null, e.getRecFrequency());
    }

    @Test
    public void execute_invalidId_error() {
        DeleteCommand command = new DeleteCommand("expense", 4);
        command.execute(testWallet);
        assertEquals(2, testWallet.getExpenseList().getSize());
    }
}
