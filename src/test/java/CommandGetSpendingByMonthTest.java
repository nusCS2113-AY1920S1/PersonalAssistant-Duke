import executor.command.CommandGetSpendingByMonth;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import ui.Receipt;
import ui.Wallet;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandGetSpendingByMonthTest {
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


       CommandGetSpendingByMonth c1 = new CommandGetSpendingByMonth("expendedmonth october /year 2019");
       c1.execute(storageManager);
       String ans1 = c1.getInfoCapsule().getOutputStr();

       CommandGetSpendingByMonth c2 = new CommandGetSpendingByMonth("expendedmonth november /year 2019");
       c2.execute(storageManager);
       String ans2 = c2.getInfoCapsule().getOutputStr();

       CommandGetSpendingByMonth c3 = new CommandGetSpendingByMonth("expendedmonth nov /year 2019");
       c3.execute(storageManager);
       String ans3 = c3.getInfoCapsule().getOutputStr();

       CommandGetSpendingByMonth c4 = new CommandGetSpendingByMonth("expendedmonth november /year 209");
       c4.execute(storageManager);
       String ans4 = c4.getInfoCapsule().getOutputStr();

        CommandGetSpendingByMonth c5 = new CommandGetSpendingByMonth("expendedmonth november /yea 2019");
        c5.execute(storageManager);
        String ans5 = c5.getInfoCapsule().getOutputStr();

        CommandGetSpendingByMonth c6 = new CommandGetSpendingByMonth("expendedmonth november /year 1750");
        c6.execute(storageManager);
        String ans6 = c6.getInfoCapsule().getOutputStr();

        CommandGetSpendingByMonth c7 = new CommandGetSpendingByMonth("expendedmonth november /year 2750");
        c7.execute(storageManager);
        String ans7 = c7.getInfoCapsule().getOutputStr();

        CommandGetSpendingByMonth c8 = new CommandGetSpendingByMonth("expendedmonth november /year");
        c8.execute(storageManager);
        String ans8 = c8.getInfoCapsule().getOutputStr();

        CommandGetSpendingByMonth c9 = new CommandGetSpendingByMonth("expendedmonth /year 2019");
        c9.execute(storageManager);
        String ans9 = c9.getInfoCapsule().getOutputStr();

       assertEquals("The total amount of money spent in october 2019 : $5.0\n", ans1);
       assertEquals("The total amount of money spent in november 2019 : $5.0\n", ans2);
       assertEquals("Wrong month input. Check Spelling", ans3);
       assertEquals("Year input contains lesser/extra number of variables. "
               + "\nFORMAT : expendedmonth <month> /year <year>", ans4);
       assertEquals("Wrong format! FORMAT : expendedmonth <month> /year <year>", ans5);
       assertEquals("Year is too far back into the past", ans6);
       assertEquals("Future year entered is invalid!", ans7);
       assertEquals("No year input detected. FORMAT : expendedmonth <month> /year <year>", ans8);
       assertEquals("No month input detected. FORMAT : expendedmonth <month> /year <year>", ans9);
    }

}
