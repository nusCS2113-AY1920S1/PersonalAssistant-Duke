package storage;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageTest {

    Storage testStorage = new Storage("dummy.txt", "dummy.txt");

    @Test
    void testLoad() {
        //String buffer = testStorage.load();
        //assertEquals("", buffer);
    }

    @Test
    void testStore() {
    }
}