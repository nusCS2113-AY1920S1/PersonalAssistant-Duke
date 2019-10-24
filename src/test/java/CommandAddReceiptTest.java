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
        String incomeInput = "In $3.00 /date 1990-01-24 /tags bank robbed crime";
        String spendingInput = "Out $15 /date 2019-12-31 /tags fine bank robbed";
        CommandAddIncomeReceipt testIncome = new CommandAddIncomeReceipt(incomeInput);
        CommandAddSpendingReceipt testSpending = new CommandAddSpendingReceipt(spendingInput);
        testIncome.execute(wallet);
        testSpending.execute(wallet);

        assertEquals(CommandType.IN, testIncome.getCommandType());
        assertEquals(3.00, testIncome.getCash());
        assertEquals("1990-01-24", testIncome.getDate().toString());
        assertEquals("bank", testIncome.getTags().get(0));
        assertEquals("robbed", testIncome.getTags().get(1));
        assertEquals("crime", testIncome.getTags().get(2));

        assertEquals(CommandType.OUT, testSpending.getCommandType());
        assertEquals(15, testSpending.getCash());
        assertEquals("2019-12-31", testSpending.getDate().toString());
        assertEquals("fine", testSpending.getTags().get(0));
        assertEquals("bank", testSpending.getTags().get(1));
        assertEquals("robbed", testSpending.getTags().get(2));
    }
}
