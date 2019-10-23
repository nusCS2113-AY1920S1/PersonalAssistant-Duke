import executor.command.CommandAddIncomeReceipt;
import executor.command.CommandAddSpendingReceipt;
import executor.command.CommandType;

import org.junit.jupiter.api.Test;
import ui.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandAddReceiptTest {
    @Test
    void execute() {
        Wallet wallet = new Wallet();
        CommandAddIncomeReceipt testIncome = new CommandAddIncomeReceipt("In $3.00 /tags bank robbed crime");
        CommandAddSpendingReceipt testSpending = new CommandAddSpendingReceipt("Out $15 /tags fine bank robbed");
        testIncome.execute(wallet);
        testSpending.execute(wallet);

        assertEquals(CommandType.IN, testIncome.getCommandType());
        assertEquals(3.00, testIncome.getCash());
        assertEquals("bank", testIncome.getTags().get(0));
        assertEquals("robbed", testIncome.getTags().get(1));
        assertEquals("crime", testIncome.getTags().get(2));

        assertEquals(CommandType.OUT, testSpending.getCommandType());
        assertEquals(15, testSpending.getCash());
        assertEquals("fine", testSpending.getTags().get(0));
        assertEquals("bank", testSpending.getTags().get(1));
        assertEquals("robbed", testSpending.getTags().get(2));
    }
}
