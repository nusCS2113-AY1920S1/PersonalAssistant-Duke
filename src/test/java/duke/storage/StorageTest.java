package duke.storage;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class StorageTest extends Storage {
    /**
     * Creates a directory named nopath.
     */
    public StorageTest() {
        super("nopath");
    }

    /**
     * Tests the readFile function
     * @return null since no file will be read in this case
     */
    @Test
    public ArrayList<String> readFile() {
        return null;
    }

    /**
     * Tests the writeFile function.
     */
    @Test
    public void writeFile() {
        System.out.println("StorageTest: written file");
    }
}