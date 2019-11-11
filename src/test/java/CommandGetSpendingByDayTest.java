
import executor.command.CommandGetSpendingByDay;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import storage.wallet.Receipt;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandGetSpendingByDayTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        String dayDate = LocalDate.now().toString();

        CommandGetSpendingByDay c1 = new CommandGetSpendingByDay("expendedday today");
        c1.execute(storageManager);
        String ans1 = c1.getInfoCapsule().getOutputStr();
        assertEquals("The total amount of money spent today"
                + " ("
                + dayDate
                + ")"
                + " is $"
                + 0.0,
                ans1);

        Receipt receipt1 = new Receipt(5.0);
        receipt1.addTag("chocolate");
        receipt1.setDate(LocalDate.parse("2019-10-10"));
        storageManager.getWallet().addReceipt(receipt1);

        Receipt receipt2 = new Receipt(5.0);
        receipt2.addTag("breakfast");
        receipt2.setDate(LocalDate.parse(dayDate));
        storageManager.getWallet().addReceipt(receipt2);

        String yesterday = LocalDate.now().minusDays(1).toString();
        Receipt receipt3 = new Receipt(5.0);
        receipt3.addTag("trans");
        receipt3.setDate(LocalDate.parse(yesterday));
        storageManager.getWallet().addReceipt(receipt3);

        Receipt receipt4 = new Receipt(5.0);
        receipt4.addTag("food");
        receipt4.setDate(LocalDate.parse("2097-10-10"));
        storageManager.getWallet().addReceipt(receipt4);

        CommandGetSpendingByDay c2 = new CommandGetSpendingByDay("expendedday today");
        c2.execute(storageManager);
        String ans2 = c2.getInfoCapsule().getOutputStr();
        assertEquals("The total amount of money spent today"
                + " ("
                + "2019-11-11"
                + ")"
                + " is $"
                + 5.0,
                ans2);

        CommandGetSpendingByDay c3 = new CommandGetSpendingByDay("expendedday yesterday");
        c3.execute(storageManager);
        String ans3 = c3.getInfoCapsule().getOutputStr();
        assertEquals("The total amount of money spent yesterday"
                + " ("
                + "2019-11-10"
                + ")"
                + " is $"
                + 5.0,
                ans3);

        CommandGetSpendingByDay c4 = new CommandGetSpendingByDay("expendedday 2097-10-10");
        c4.execute(storageManager);
        String ans4 = c4.getInfoCapsule().getOutputStr();
        assertEquals("The total amount of money spent on "
                + "2097-10-10"
                + " is $"
                + 5.0
                + "\nNOTE : The date input is in the future",
                ans4);

        CommandGetSpendingByDay c5 = new CommandGetSpendingByDay("expendedday 2019-10-10");
        c5.execute(storageManager);
        String ans5 = c5.getInfoCapsule().getOutputStr();
        assertEquals("The total amount of money spent on "
                + "2019-10-10"
                + " is $"
                + 5.0,
                ans5);

        CommandGetSpendingByDay c6 = new CommandGetSpendingByDay("expendedday ajicnawjc");
        c6.execute(storageManager);
        String ans6 = c6.getInfoCapsule().getOutputStr();
        assertEquals("Input is invalid."
                + " FORMAT : expendedday "
                + "\ntoday or"
                + "\nyesterday or"
                + "\nYYYY-MM-DD",
                ans6);

        CommandGetSpendingByDay c7 = new CommandGetSpendingByDay("expendedday");
        c7.execute(storageManager);
        String ans7 = c7.getInfoCapsule().getOutputStr();
        assertEquals("Input is invalid."
                + " FORMAT : expendedday "
                + "\ntoday or"
                + "\nyesterday or"
                + "\nYYYY-MM-DD",
                ans7);


    }
}

