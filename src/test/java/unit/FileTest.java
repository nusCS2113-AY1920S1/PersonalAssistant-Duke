package unit;

import spinbox.exceptions.CorruptedDataException;
import spinbox.entities.items.File;
import spinbox.entities.items.Item;
import spinbox.exceptions.DateFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTest {

    @org.junit.jupiter.api.Test
    void testToString_createAndMarkDoneNewTask() {
        Item item = new File(0, "file1");
        assertEquals("[NOT DOWNLOADED] file1", item.toString());
        item.markDone();
        assertEquals("[DOWNLOADED] file1", item.toString());
    }

    @org.junit.jupiter.api.Test
    void testStoreString_createAndMarkDoneNewTask() throws CorruptedDataException, DateFormatException {
        Item item = new File();
        item.fromStoredString("0 | file1");
        assertEquals("0 | file1", item.storeString());
        item.markDone();
        assertEquals("1 | file1", item.storeString());
    }
}
