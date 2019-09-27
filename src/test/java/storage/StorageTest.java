package storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    Storage testStorage = new Storage("dummy.txt");

    @Test
    void testLoad() {
        String buffer = testStorage.load();
        assertEquals("", buffer);
    }

    @Test
    void testStore() {
    }
}