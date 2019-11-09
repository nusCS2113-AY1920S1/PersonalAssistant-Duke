import executor.command.CommandDateList;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import ui.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CommandDateListTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receiptOne = new Receipt(3.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);

        CommandDateList dateOne = new CommandDateList("datelist 2019-02-01");
        dateOne.execute(storageManager);
        String output = dateOne.getInfoCapsule().getOutputStr();

        assertEquals("You have the following receipts for 2019-02-01\n1. [transport] $3.00 2019-02-01\n", output);

    }

}
