package ui;

import executor.command.CommandBudget;
import executor.command.CommandType;
import org.junit.jupiter.api.Test;
import storage.wallet.Receipt;
import storage.StorageManager;

import java.text.DecimalFormat;
import java.time.LocalDate;

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
        assertEquals(error, c1.getInfoCapsule().getOutputStr());

        String description =  " Sets user budget \n"
                + "FORMAT : budget $<amount>\n";
        assertEquals(description, c1.getDescription());

        CommandBudget c2 = new CommandBudget("Budget $0");
        c2.execute(storageManager);
        assertEquals(c2.getInfoCapsule().getOutputStr(), error);

        StorageManager storageManager1 = new StorageManager();
        Receipt receiptOne = new Receipt(10.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager1.getWallet().addReceipt(receiptOne);

        Receipt receiptTwo = new Receipt(15.0);
        receiptTwo.addTag("food");
        receiptTwo.setDate(LocalDate.parse("2019-02-02"));
        storageManager1.getWallet().addReceipt(receiptTwo);

        CommandBudget c3 = new CommandBudget("budget $100");
        c3.execute(storageManager1);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String output = "Budget updated to: $" + decimalFormat.format(100);
        assertTrue(c3.getInfoCapsule().getOutputStr().contains(output));
        assertTrue(c3.getInfoCapsule().getOutputStr().contains("You are still good and safe "
                + "as you did not overspend your budget ;) \n"));
        String theFullOutput = output + "\n"
                + "You are still good and safe as you did not overspend your budget ;) \n"
                + "\n"
                + "Percentage of Budget Used Up : 25.0%\n\n";
        assertEquals(theFullOutput, c3.getInfoCapsule().getOutputStr());
    }
}
