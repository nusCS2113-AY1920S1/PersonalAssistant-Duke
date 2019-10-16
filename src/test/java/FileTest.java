import spinbox.exceptions.CorruptedDataException;
import spinbox.items.File;
import spinbox.items.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTest {

    @org.junit.jupiter.api.Test
    void testToString_createAndMarkDoneNewTask() {
        Item item = new File(0, "file1");
        assertEquals("[✗] file1", item.toString());
        item.markDone();
        assertEquals("[✓] file1", item.toString());
    }

    @org.junit.jupiter.api.Test
    void testStoreString_createAndMarkDoneNewTask() throws CorruptedDataException {
        Item item = new File("0 | file1");
        assertEquals("0 | file1", item.storeString());
        item.markDone();
        assertEquals("1 | file1", item.storeString());
    }
}
