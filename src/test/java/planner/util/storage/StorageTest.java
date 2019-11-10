//@@author namiwa

package planner.util.storage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {

    @TempDir
    public File tempDir = new File("test");

    @TempDir
    public File tempFile;

    private Storage store = new Storage(tempDir.getPath());

    @DisplayName("Testing Storage")
    @Order(1)
    @Test
    void testStorage() {
        assertNotNull(store);
    }

    @DisplayName("Testing Storage Data Paths")
    @Order(2)
    @Test
    void testStorageDataPaths() {
        assertEquals("test", store.getDataPath().toString());
    }

    @DisplayName("Test File")
    @Order(3)
    @Test
    void testPathsTrue() throws IOException {
        assertTrue(tempDir.isDirectory());
        tempFile = File.createTempFile("test", ".txt", tempDir);
        assertTrue(tempFile.isFile());
        store.setDataPath(tempFile.toPath());
        assertTrue(store.getDataPathExists());
        List<String> tempLines = Arrays.asList("yes\n", "ball-ball");
        store.writeModsData(tempLines);
        assertTrue((tempFile.length() > 0));
    }

}
