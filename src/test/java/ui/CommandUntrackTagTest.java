package ui;

import duke.exception.DukeException;
import executor.command.CommandUntrackTag;
import org.junit.jupiter.api.Test;
import storage.wallet.Receipt;
import storage.StorageManager;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandUntrackTagTest {
    @Test
    void executeTest() {
        StorageManager store = new StorageManager();
        CommandUntrackTag emptyArg = new CommandUntrackTag("Untrack");
        emptyArg.execute(store);
        assertEquals(UiCode.ERROR, emptyArg.getInfoCapsule().getUiCode());
        assertEquals("Please enter a tag to untrack", emptyArg.getInfoCapsule().getOutputStr());

        try {
            ArrayList<String> tags = new ArrayList<>();
            tags.add("food");
            store.getWallet().addReceipt(new Receipt(100.0, LocalDate.now(), tags));
            tags.remove("food");
            store.getWallet().addReceipt(new Receipt(100.0, LocalDate.now(), tags));
            store.getWallet().getReceipts().addFolder("food");
        } catch (DukeException e) {
            e.printStackTrace();
        }

        CommandUntrackTag correctUntrack = new CommandUntrackTag("Untrack food");
        correctUntrack.execute(store);
        assertEquals("Untracked tags: food", correctUntrack.getInfoCapsule().getOutputStr());
        assertEquals(UiCode.TOAST, correctUntrack.getInfoCapsule().getUiCode());

        // Removing a non-existent tracking tag
        CommandUntrackTag notTracked = new CommandUntrackTag("Untrack food");
        notTracked.execute(store);
        assertEquals(notTracked.getInfoCapsule().getUiCode(), UiCode.ERROR);
        assertEquals("<food> is not being tracked!\nIf you wish to track this tag, try TRACK <tag>.",
                notTracked.getInfoCapsule().getOutputStr());
    }
}
