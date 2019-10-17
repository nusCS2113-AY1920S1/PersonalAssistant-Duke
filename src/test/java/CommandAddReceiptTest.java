import executor.command.CommandAddIncomeReceipt;
import executor.command.CommandAddSpendingReceipt;
import executor.command.CommandType;

import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import ui.IncomeReceipt;
import ui.Receipt;
import ui.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandAddReceiptTest {
    void execute() {
        Wallet wallet = new Wallet();
        //IncomeReceipt ir = new IncomeReceipt();
        CommandAddIncomeReceipt testIncome = new CommandAddIncomeReceipt("In $3.00 /tag bank robbed crime");
        CommandAddSpendingReceipt testSpending = new CommandAddSpendingReceipt("Out $15 /tag fine bank robbed");
        testIncome.execute(wallet);
        testSpending.execute(wallet);

        assertEquals(CommandType.IN, testIncome.getCommandType());
        assertEquals("3.00", testIncome.getIncome());
        assertEquals("bank robbed crime", testIncome.getTags());

        assertEquals(CommandType.OUT, testSpending.getCommandType());
        assertEquals("15", testSpending.getSpending());
        assertEquals("fine bank robbed", testSpending.getTags());
    }
}
