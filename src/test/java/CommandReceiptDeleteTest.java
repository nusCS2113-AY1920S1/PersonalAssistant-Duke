import executor.command.CommandPercent;
import executor.command.CommandReceiptDelete;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import ui.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CommandReceiptDeleteTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receiptOne = new Receipt(4.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);

        CommandReceiptDelete d1 = new CommandReceiptDelete("receiptdelete 1");
        d1.execute(storageManager);
        String output = d1.getInfoCapsule().getOutputStr();
        assertEquals("Receipt 1 has been deleted\n", output);

        CommandReceiptDelete d2 = new CommandReceiptDelete("receiptdelete");
        d2.execute(storageManager);
        String result = d2.getInfoCapsule().getOutputStr();
        assertEquals("Index input is missing. FORMAT : receiptdelete <tag>", result);


    }
}
