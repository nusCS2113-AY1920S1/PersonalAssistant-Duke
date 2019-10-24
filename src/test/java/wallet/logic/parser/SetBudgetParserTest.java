//@@author matthewng1996

package wallet.logic.parser;

import org.junit.jupiter.api.Test;
import wallet.logic.command.SetBudgetCommand;
import wallet.model.Wallet;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetBudgetParserTest {
    private static Wallet testWallet = new Wallet();

    /**
     * Test for valid parsing of input into proper parameters.
     */
    @Test
    public void parseBudgetValidInputSuccess() {
        SetBudgetParser setBudgetParser = new SetBudgetParser();
        String input = "$1000 01/2019";
        try {
            SetBudgetCommand setBudgetCommand = setBudgetParser.parse(input);
            setBudgetCommand.execute(testWallet);
            assertAll("Budget should contain these values",
                () -> assertEquals(1000, testWallet.getBudgetList().getBudgetList().get(0).getAmount()),
                () -> assertEquals(1, testWallet.getBudgetList().getBudgetList().get(0).getMonth()),
                () -> assertEquals(2019, testWallet.getBudgetList().getBudgetList().get(0).getYear())
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
