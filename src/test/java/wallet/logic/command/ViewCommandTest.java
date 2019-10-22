//@@author matthewng1996

package wallet.logic.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.model.Wallet;
import wallet.model.record.Budget;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewCommandTest {
    private static Wallet testWallet = new Wallet();

    /**
     * Set up test wallet by adding budget.
     */
    @BeforeAll
    public static void setUp() {
        testWallet.getBudgetList().addBudget(new Budget(1000, 10, 2019));
    }

    /**
     * Test for viewing existing budget via view command.
     */
    @Test
    public void execute_view_budget() {
        ViewCommand viewCommand = new ViewCommand("budget", "10/2019");
        viewCommand.execute(testWallet);
        assertEquals(1000, testWallet.getBudgetList().getBudgetList().get(0).getAmount());
    }
}
