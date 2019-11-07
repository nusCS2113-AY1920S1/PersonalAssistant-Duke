import executor.command.CommandTagList;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import ui.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CommandTagListTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receiptOne = new Receipt(3.0);
        receiptOne.addTag("food");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);

        CommandTagList dateOne = new CommandTagList("taglist food");
        dateOne.execute(storageManager);
        String output = dateOne.getInfoCapsule().getOutputStr();

        assertEquals("You spent a total of $3.00 on food\n" + "1. [food] 3.0 2019-02-01\n\n", output);
    }

}
