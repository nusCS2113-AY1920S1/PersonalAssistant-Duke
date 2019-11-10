/**
 * Testing for FileUtilJson utilities
 *
 * @author kuromono
 */

package cube.util;

import cube.exception.CubeException;
import cube.logic.parser.exception.ParserException;
import cube.storage.StorageManager;
import cube.testutil.SampleUtil;
import cube.testutil.StorageUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilJsonTest {

    private static StorageManager storageManager;
    private static String filePath;
    private static String fileName;
    private static FileUtilJson<StorageManager> storage;

    /**
     * Initializes variables used for the tests.
     */
    @BeforeAll
    public static void initVariables() throws ParserException {
        storageManager = new StorageManager();
        storageManager.storeFoodList(SampleUtil.generateSampleData(5));
        filePath = "data";
        fileName = "test_fileutil.json";
    }

    /**
     * Resets FileUtilJson for every test run.
     */
    @BeforeEach
    public void clearVariables() {
        storage = new FileUtilJson<>(filePath, fileName, storageManager);
    }

    /**
     * Deletes all the files used during this test.
     */
    @AfterAll
    public static void deleteTestFiles() {
        StorageUtil.deleteFile(fileName);
    }

    /**
     * Test saving a JSON file.
     */
    @Test
    @Order(1)
    public void save_file() throws CubeException {
        storage.save(storageManager);

        assertTrue(StorageUtil.checkFileAvailable(fileName));
    }

    /**
     * Test loading a JSON file.
     */
    @Test
    @Order(2)
    public void load_file() throws CubeException {
        StorageManager resultStorageManager = storage.load();

        assertTrue(StorageUtil.checkFileAvailable(fileName));
        assertEquals(resultStorageManager.getFoodList().toString(), storageManager.getFoodList().toString());
        assertEquals(resultStorageManager.getConfig().getUiConfig().toString(), storageManager.getConfig().getUiConfig().toString());
        assertEquals(resultStorageManager.getConfig().getLogConfig().toString(), storageManager.getConfig().getLogConfig().toString());
        assertEquals(resultStorageManager.getPromotionList().getPromotionList().toString(), storageManager.getPromotionList().getPromotionList().toString());
        assertEquals(resultStorageManager.getRevenue(), storageManager.getRevenue());
    }

    /**
     * Optional test : load/save performance by calculating elapsed time
     */
    @Test
    @Order(3)
    public void performance_test() throws CubeException {
        storageManager.storeFoodList(SampleUtil.generateSampleData(5));
        System.out.println(String.format("--- Testing performance for %1$s products ---", storageManager.getFoodList().size()));
        testSaveTime();
        testLoadTime();

        storageManager.storeFoodList(SampleUtil.generateSampleData(50));
        System.out.println(String.format("--- Testing performance for %1$s products ---", storageManager.getFoodList().size()));
        testSaveTime();
        testLoadTime();

        storageManager.storeFoodList(SampleUtil.generateSampleData(500));
        System.out.println(String.format("--- Testing performance for %1$s products ---", storageManager.getFoodList().size()));
        testSaveTime();
        testLoadTime();
    }

    private void testSaveTime() throws CubeException {
        long startTime = System.currentTimeMillis();
        storage.save(storageManager);
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed Save Time : " + (endTime - startTime) + " ms");
    }

    private void testLoadTime() throws CubeException {
        long startTime = System.currentTimeMillis();
        storageManager = storage.load();
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed Load Time : " + (endTime - startTime) + " ms");
    }
}
