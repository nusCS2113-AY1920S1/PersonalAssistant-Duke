package ui;

import executor.command.CommandBudget;
import executor.command.CommandType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandBudgetTest {

    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        CommandBudget c1 = new CommandBudget("Budget");
        c1.execute(storageManager);
        assertEquals(CommandType.BUDGET, c1.getCommandType());
        String error = "Please kindly follow the format : budget $<amount> \n"
                + "Please enter an amount greater than zero!\n";
        assertTrue(c1.getInfoCapsule().getOutputStr().contains(error));
        String description =  " Sets user budget \n"
                + "FORMAT : budget $<amount>\n";
        assertEquals(description, c1.getDescription());
        CommandBudget c2 = new CommandBudget("budget $600");
        c2.execute(storageManager);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String output = "Budget updated to: $" + decimalFormat.format(600);
        assertTrue(c2.getInfoCapsule().getOutputStr().contains(output));
    }
}
