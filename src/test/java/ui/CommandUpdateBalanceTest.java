package ui;

import executor.command.CommandType;
import executor.command.CommandUpdateBalance;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandUpdateBalanceTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        CommandUpdateBalance c1 = new CommandUpdateBalance("setbalance");
        c1.execute(storageManager);
        String error = "Please kindly follow the format : setbalance $<amount> \n"
                + "Please enter an amount greater than or equal to zero in your wallet !\n";
        assertTrue(c1.getInfoCapsule().getOutputStr().contains(error));
        assertEquals(CommandType.SETBALANCE, c1.getCommandType());
        CommandUpdateBalance c2 = new CommandUpdateBalance("setbalance $300");
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        c2.execute(storageManager);
        String output = "Balance updated to: $" + decimalFormat.format(300) + "\n";
        assertTrue(c2.getInfoCapsule().getOutputStr().contains(output));
        String description = "Updates current balance to new balance in the wallet and can only be set once \n"
                + "FORMAT : setbalance $<amount>";
        assertEquals(description,c2.getDescription());
    }
}
