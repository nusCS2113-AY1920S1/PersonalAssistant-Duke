import executor.command.CommandEdit;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import storage.wallet.Receipt;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandEditTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receipt1 = new Receipt(5.0);
        receipt1.addTag("Expenses,food");
        receipt1.setDate(LocalDate.parse("2019-10-10"));
        storageManager.getWallet().addReceipt(receipt1);

        Receipt receipt2 = new Receipt(10.0);
        receipt2.addTag("bike");
        receipt2.setDate(LocalDate.parse("2019-11-10"));
        storageManager.getWallet().addReceipt(receipt2);

        CommandEdit c1 = new CommandEdit("edit");
        c1.execute(storageManager);
        String ans1 = c1.getInfoCapsule().getOutputStr();
        assertEquals("Index has to be an INTEGER"
                + "\nFORMAT : edit <index> /<part to be edited> <new-input>", ans1);


        CommandEdit c3 = new CommandEdit("edit 0");
        c3.execute(storageManager);
        String ans3 = c3.getInfoCapsule().getOutputStr();
        assertEquals("Index out of bounds.", ans3);

        CommandEdit c4 = new CommandEdit("edit /tag sdfv");
        c4.execute(storageManager);
        String ans4 = c4.getInfoCapsule().getOutputStr();
        assertEquals("Index has to be an INTEGER"
                + "\nFORMAT : edit <index> /<part to be edited> <new-input>", ans4);

        CommandEdit c5 = new CommandEdit("edit acwev");
        c5.execute(storageManager);
        String ans5 = c5.getInfoCapsule().getOutputStr();
        assertEquals("Index has to be an INTEGER"
                + "\nFORMAT : edit <index> /<part to be edited> <new-input>", ans5);

        CommandEdit c6 = new CommandEdit("edit 2 /ascw");
        c6.execute(storageManager);
        String ans6 = c6.getInfoCapsule().getOutputStr();
        assertEquals("Flag invalid. Valid input : /tag/value/date", ans6);

        CommandEdit c9 = new CommandEdit("edit 2 /value");
        c9.execute(storageManager);
        String ans9 = c9.getInfoCapsule().getOutputStr();
        assertEquals("Cash value has be an INTEGER/DOUBLE", ans9);

        CommandEdit c10 = new CommandEdit("edit 2 /value asfv");
        c10.execute(storageManager);
        String ans10 = c10.getInfoCapsule().getOutputStr();
        assertEquals("Cash value has be an INTEGER/DOUBLE", ans10);

        CommandEdit c11 = new CommandEdit("edit 2 /value 4.444");
        c11.execute(storageManager);
        String ans11 = c11.getInfoCapsule().getOutputStr();
        assertEquals("\n\nThe cashspent for receipt "
                        + 2
                        + " was changed from "
                        + 10.0
                        + " to "
                        + 4.44
                        + "."
                        + "\nNOTE : The cash value had more then 2 decimal points, thus it was \nrounded up"
                        + " from "
                        + 4.444
                        + " to "
                        + 4.44,
                ans11);

        CommandEdit c12 = new CommandEdit("edit 2 /value 4.44987");
        c12.execute(storageManager);
        String ans12 = c12.getInfoCapsule().getOutputStr();
        assertEquals("\n\nThe cashspent for receipt "
                        + 2
                        + " was changed from "
                        + 4.44
                        + " to "
                        + 4.45
                        + "."
                        + "\nNOTE : The cash value had more then 2 decimal points, thus it was \nrounded up"
                        + " from "
                        + 4.44987
                        + " to "
                        + 4.45,
                ans12);

        CommandEdit c13 = new CommandEdit("edit 2 /date sfvvv");
        c13.execute(storageManager);
        String ans13 = c13.getInfoCapsule().getOutputStr();
        assertEquals("Invalid date input. FORMAT : YYYY-MM-DD", ans13);

        CommandEdit c14 = new CommandEdit("edit 2 /date 2019/10/10");
        c14.execute(storageManager);
        String ans14 = c14.getInfoCapsule().getOutputStr();
        assertEquals("Invalid date input. FORMAT : YYYY-MM-DD", ans14);

        CommandEdit c15 = new CommandEdit("edit 2 /date 2019-01-01");
        c15.execute(storageManager);
        String ans15 = c15.getInfoCapsule().getOutputStr();
        assertEquals("\n\nThe date for receipt "
                        + 2
                        + " was changed from "
                        + "2019-11-10"
                        + " to "
                        + "2019-01-01"
                        + ".",
                ans15);

        CommandEdit c16 = new CommandEdit("edit 2 /date 2097-01-01");
        c16.execute(storageManager);
        String ans16 = c16.getInfoCapsule().getOutputStr();
        assertEquals("\n\nThe date for receipt "
                        + 2
                        + " was changed from "
                        + "2019-01-01"
                        + " to "
                        + "2097-01-01"
                        + "."
                        + "\nNOTE : The year input is in the future",
                ans16);


        CommandEdit c17 = new CommandEdit("edit 2 /date 2097-01-01 /value 121.423");
        c17.execute(storageManager);
        String ans17 = c17.getInfoCapsule().getOutputStr();
        assertEquals("More than 1 flag detected. \nOnly key in one flag: /tag or /value or /date",
                ans17);
    }

}

