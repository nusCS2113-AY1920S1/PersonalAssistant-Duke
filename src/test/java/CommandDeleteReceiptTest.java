import executor.command.CommandDeleteReceipt;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import storage.wallet.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CommandDeleteReceiptTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receiptOne = new Receipt(4.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);

        CommandDeleteReceipt d1 = new CommandDeleteReceipt("deletereceipt 1");
        d1.execute(storageManager);
        String output = d1.getInfoCapsule().getOutputStr();
        assertEquals("Receipt 1 has been deleted\n", output);

        CommandDeleteReceipt d2 = new CommandDeleteReceipt("deletereceipt");
        d2.execute(storageManager);
        String result = d2.getInfoCapsule().getOutputStr();
        assertEquals("Index input is missing. FORMAT : deletereceipt <Index_of_Entry>", result);

        CommandDeleteReceipt d3 = new CommandDeleteReceipt("deletereceipt 2.0f");
        d3.execute(storageManager);
        String out = d3.getInfoCapsule().getOutputStr();
        assertEquals("Invalid index input. Please enter an integer", out);

        CommandDeleteReceipt d4 = new CommandDeleteReceipt("deletereceipt 10");
        d4.execute(storageManager);
        String output2 = d4.getInfoCapsule().getOutputStr();
        assertEquals("Invalid 'deletereceipt' statement."
                + "Please indicate the index of the receipt you wish to delete.\n", output2);

    }
}
