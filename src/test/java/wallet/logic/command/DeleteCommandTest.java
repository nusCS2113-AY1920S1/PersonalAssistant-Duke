package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.record.Category;
import wallet.model.record.Expense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCommandTest {
    //@@author kyang96
    private static Wallet testWallet = new Wallet();

    @BeforeAll
    public static void setUp() {
        testWallet.getExpenseList().addExpense(new Expense("Lunch", LocalDate.now(), 3, Category.FOOD, false, null));
        testWallet.getExpenseList().addExpense(new Expense("Dinner", LocalDate.now(), 5, Category.FOOD, false, null));
    }

    @Test
    public void execute_validId_success() {
        DeleteCommand command = new DeleteCommand("expense", 1);
        command.execute(testWallet);
        Expense e = testWallet.getExpenseList().findExpenseWithId(1);
        assertEquals(null, e);

        assertEquals(1, testWallet.getExpenseList().getSize());
    }

    @Test
    public void execute_invalidId_error() {
        DeleteCommand command = new DeleteCommand("expense", 4);
        command.execute(testWallet);
        assertEquals(2, testWallet.getExpenseList().getSize());
    }
}
