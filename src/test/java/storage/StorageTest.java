package storage;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageTest {

    Storage testStorage = new Storage("dummy.txt");

    @Test
    void testLoad() throws DukeException {
        String buffer = testStorage.load();
        assertEquals("", buffer);
    }

    @Test
    void testStore() {
    }
}