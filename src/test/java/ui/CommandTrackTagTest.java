package ui;

import executor.command.CommandTrackTag;
import org.junit.jupiter.api.Test;
import storage.wallet.Receipt;
import storage.StorageManager;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTrackTagTest {
    @Test
    void executeTest() {
        StorageManager store = new StorageManager();
        CommandTrackTag emptyArg = new CommandTrackTag("Track");
        emptyArg.execute(store);
        assertEquals(emptyArg.getInfoCapsule().getUiCode(), UiCode.ERROR);
        assertEquals(emptyArg.getInfoCapsule().getOutputStr(), "Please enter a tag to track");

        ArrayList<String> tags = new ArrayList<>();
        tags.add("food");
        store.getWallet().addReceipt(new Receipt(100.0, LocalDate.now(), tags));
        store.getWallet().addReceipt(new Receipt(100.0, LocalDate.now(), tags));
        CommandTrackTag correctArg = new CommandTrackTag("Track food");
        correctArg.execute(store);
        assertEquals(correctArg.getInfoCapsule().getUiCode(), UiCode.TOAST);
        assertEquals(correctArg.getInfoCapsule().getOutputStr(), "Tracking tags: food");

        CommandTrackTag multipleArgs = new CommandTrackTag("Track food dating");
        multipleArgs.execute(store);
        assertEquals(multipleArgs.getInfoCapsule().getUiCode(), UiCode.TOAST);
        assertEquals(multipleArgs.getInfoCapsule().getOutputStr(), "Tracking tags: food dating");
    }
}
