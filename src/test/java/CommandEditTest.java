import executor.command.CommandEdit;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import ui.Receipt;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandEditTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receipt1 = new Receipt(5.0);
        receipt1.addTag("food");
        receipt1.setDate(LocalDate.parse("2019-10-10"));
        storageManager.getWallet().addReceipt(receipt1);

        Receipt receipt2 = new Receipt(10.0);
        receipt2.addTag("bike");
        receipt2.setDate(LocalDate.parse("2019-11-10"));
        storageManager.getWallet().addReceipt(receipt2);

        CommandEdit c1 = new CommandEdit("edit");
        c1.execute(storageManager);
        String ans1 = c1.getInfoCapsule().getOutputStr();
        assertEquals("No Index input detected",ans1);

        CommandEdit c2 = new CommandEdit("edit 2 /tag mike");
        c2.execute(storageManager);
        String ans2 = c2.getInfoCapsule().getOutputStr();
        assertEquals("The tag for receipt "
                + 2
                + " was changed from "
                + "[bike]"
                + " to "
                + "mike"
                + ".",ans2);

        CommandEdit c3 = new CommandEdit("edit 0");
        c3.execute(storageManager);
        String ans3 = c3.getInfoCapsule().getOutputStr();
        assertEquals("Index out of bounds.",ans3);

        CommandEdit c4 = new CommandEdit("edit /tag sdfv");
        c4.execute(storageManager);
        String ans4 = c4.getInfoCapsule().getOutputStr();
        assertEquals("No Index input detected",ans4);

        CommandEdit c5 = new CommandEdit("edit acwev");
        c5.execute(storageManager);
        String ans5 = c5.getInfoCapsule().getOutputStr();
        assertEquals("Index has be an INTEGER",ans5);

        CommandEdit c6 = new CommandEdit("edit 2 /ascw");
        c6.execute(storageManager);
        String ans6 = c6.getInfoCapsule().getOutputStr();
        assertEquals("Flag invalid. Valid input : tag/value/date",ans6);

        CommandEdit c7 = new CommandEdit("edit 2 /tag");
        c7.execute(storageManager);
        String ans7 = c7.getInfoCapsule().getOutputStr();
        assertEquals("The tag for receipt "
                        + 2
                        + " was changed from "
                        + "[mike]"
                        + " to "
                        + "."
                        + "\nNOTE : Tag is empty",
                ans7);

        CommandEdit c8 = new CommandEdit("edit 2 /tag avrwav(*867");
        c8.execute(storageManager);
        String ans8 = c8.getInfoCapsule().getOutputStr();
        assertEquals("The tag for receipt "
                        + 2
                        + " was changed from "
                        + "[]"
                        + " to "
                        + "avrwav(*867"
                        + "."
                        + "\nNOTE : Tag contains other characters apart from alphabets",
                ans8);

        CommandEdit c9 = new CommandEdit("edit 2 /value");
        c9.execute(storageManager);
        String ans9 = c9.getInfoCapsule().getOutputStr();
        assertEquals("Cash value is empty. Please key in an INTEGER/DOUBLE value",ans9);

        CommandEdit c10 = new CommandEdit("edit 2 /value asfv");
        c10.execute(storageManager);
        String ans10 = c10.getInfoCapsule().getOutputStr();
        assertEquals("Cash value has be an INTEGER/DOUBLE",ans10);

        CommandEdit c11 = new CommandEdit("edit 2 /value 4.444");
        c11.execute(storageManager);
        String ans11 = c11.getInfoCapsule().getOutputStr();
        assertEquals("The cashspent for receipt "
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
        assertEquals("The cashspent for receipt "
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
        assertEquals("Invalid date input. FORMAT : YYYY-MM-DD",ans13);

        CommandEdit c14 = new CommandEdit("edit 2 /date 2019/10/10");
        c14.execute(storageManager);
        String ans14 = c14.getInfoCapsule().getOutputStr();
        assertEquals("Invalid date input. FORMAT : YYYY-MM-DD",ans14);

        CommandEdit c15 = new CommandEdit("edit 2 /date 2019-01-01");
        c15.execute(storageManager);
        String ans15 = c15.getInfoCapsule().getOutputStr();
        assertEquals("The date for receipt "
                        + 2
                        + " was changed from "
                        + "2019-11-10"
                        + " to "
                        + "2019-01-01"
                        + ".",
                ans15);
    }
}

