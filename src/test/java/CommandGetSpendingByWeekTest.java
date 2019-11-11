import executor.command.CommandGetSpendingByWeek;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import storage.wallet.Receipt;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandGetSpendingByWeekTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        String dayDate = LocalDate.now().toString();

        Receipt receipt1 = new Receipt(5.0);
        receipt1.addTag("breakfast");
        receipt1.setDate(LocalDate.parse(dayDate));
        storageManager.getWallet().addReceipt(receipt1);

        String yesterday = LocalDate.now().minusDays(1).toString();
        Receipt receipt2 = new Receipt(5.0);
        receipt2.addTag("trans");
        receipt2.setDate(LocalDate.parse(yesterday));
        storageManager.getWallet().addReceipt(receipt2);

        int dayNum = CommandGetSpendingByWeek.dayStrToInt(LocalDate.now().getDayOfWeek().toString().toLowerCase());
        int dayLeft = 7 - dayNum;
        CommandGetSpendingByWeek c1 = new CommandGetSpendingByWeek("expendedweek");
        c1.execute(storageManager);
        String ans1 = c1.getInfoCapsule().getOutputStr();
        assertEquals("The total amount spent this week is $"
                + "5.0"
                + " and there is/are "
                + dayLeft
                + "day(s) to end of week", ans1);
    }
}
