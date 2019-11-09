import executor.command.CommandPercent;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import ui.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CommandPercentTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receiptOne = new Receipt(4.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);

        Receipt receiptTwo = new Receipt(4.0);
        receiptTwo.addTag("food");
        receiptTwo.setDate(LocalDate.parse("2019-02-02"));
        storageManager.getWallet().addReceipt(receiptTwo);


        CommandPercent percentOne = new CommandPercent("percent transport");
        percentOne.execute(storageManager);
        String output = percentOne.getInfoCapsule().getOutputStr();
        assertEquals("50.0% of your wallet expenses is spent on transport\n", output);

        CommandPercent percentTwo = new CommandPercent("percent");
        percentTwo.execute(storageManager);
        String result = percentTwo.getInfoCapsule().getOutputStr();
        assertEquals("Tag input is missing. FORMAT : percent <tag>", result);

        CommandPercent percentThree = new CommandPercent("percent books");
        percentThree.execute(storageManager);
        String result2 = percentThree.getInfoCapsule().getOutputStr();
        assertEquals("0.0% of your wallet expenses is spent on books\n", result2);

    }

}
