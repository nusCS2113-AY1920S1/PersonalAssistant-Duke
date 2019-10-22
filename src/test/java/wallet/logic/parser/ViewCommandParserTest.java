//@@author matthewng1996

package wallet.logic.parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wallet.logic.command.ViewCommand;
import wallet.model.Wallet;
import wallet.model.record.Budget;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewCommandParserTest {
    private static Wallet testWallet = new Wallet();

    /**
     * Set up testwallet by adding a budget.
     */
    @BeforeAll
    public static void setUp() {
        testWallet.getBudgetList().addBudget(new Budget(100, 10, 2019));
    }

    /**
     * Test for valid parsing of input into proper parameters.
     */
    @Test
    public void parseViewCommand_validInput_budget_success() {
        ViewCommandParser viewCommandParser = new ViewCommandParser();
        String input = "view budget 10/2019";
        ViewCommand viewCommand = viewCommandParser.parse(input);
        assertAll("Viewing budget should contain these values",
            () -> assertEquals("view", viewCommand.type[0]),
            () -> assertEquals("budget", viewCommand.type[1]),
            () -> assertEquals("10/2019", viewCommand.type[2])
        );
    }
}
