import executor.command.CommandIncome;
import executor.command.CommandSpending;

import executor.command.CommandType;
import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import ui.IncomeReceipt;
import ui.Receipt;
import ui.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandWalletTest {
    void execute() {
        Wallet wallet = new Wallet();
        //IncomeReceipt ir = new IncomeReceipt();
        CommandIncome testCommand = new CommandIncome("In $3.00 /tag bank robbed crime");
        testCommand.execute(wallet);

        assertEquals(CommandType.IN, testCommand.getCommandType());
        assertEquals("3.00", testCommand.getIncome());
        assertEquals("bank robbed crime", testCommand.getTags());
    }
}
