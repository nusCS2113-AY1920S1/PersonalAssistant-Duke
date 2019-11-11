import executor.command.CommandGetSpendingByYear;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import storage.wallet.Receipt;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandGetSpendingByYearTest {

    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receipt1 = new Receipt(5.0);
        receipt1.addTag("food");
        receipt1.setDate(LocalDate.parse("2019-10-10"));
        storageManager.getWallet().addReceipt(receipt1);

        Receipt receipt2 = new Receipt(5.0);
        receipt2.addTag("food");
        receipt2.setDate(LocalDate.parse("2019-11-10"));
        storageManager.getWallet().addReceipt(receipt2);

        CommandGetSpendingByYear c1 = new CommandGetSpendingByYear("expendedyear 2019");
        c1.execute(storageManager);
        String ans1 = c1.getInfoCapsule().getOutputStr();
        assertEquals("The total amount of money spent in 2019 : $10.0\n", ans1);

        CommandGetSpendingByYear c2 = new CommandGetSpendingByYear("expendedyear");
        c2.execute(storageManager);
        String ans2 = c2.getInfoCapsule().getOutputStr();
        assertEquals("No year input detected.\nFORMAT : expendedyear 2019\n", ans2);

        CommandGetSpendingByYear c3 = new CommandGetSpendingByYear("expendedyear 201");
        c3.execute(storageManager);
        String ans3 = c3.getInfoCapsule().getOutputStr();
        assertEquals("Year input contains lesser/extra number of variables.\n"
                + " FORMAT : expendedyear 2019\n", ans3);

        CommandGetSpendingByYear c4 = new CommandGetSpendingByYear("expendedyear 2091");
        c4.execute(storageManager);
        String ans4 = c4.getInfoCapsule().getOutputStr();
        assertEquals("Future year entered is invalid!" + "\n", ans4);

        CommandGetSpendingByYear c5 = new CommandGetSpendingByYear("expendedyear 1750");
        c5.execute(storageManager);
        String ans5 = c5.getInfoCapsule().getOutputStr();
        assertEquals("Year is too far back into the past" + "\n", ans5);

        CommandGetSpendingByYear c6 = new CommandGetSpendingByYear("expendedyear abcd");
        c6.execute(storageManager);
        String ans6 = c6.getInfoCapsule().getOutputStr();
        assertEquals("Year input is either a double or contains String values.\n"
                + "FORMAT : expendedyear 2019\n", ans6);







    }
}
