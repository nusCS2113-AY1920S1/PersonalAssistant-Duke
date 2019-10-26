package cube.util;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.parser.Parser;
import cube.storage.StorageManager;
import cube.model.FoodList;
import org.junit.jupiter.api.Test;


public class FileUtilJsonTest {

    private FileUtilJson storage;
    private StorageManager storageManager;

    public void init(String filePath, String fileName) throws CubeException {
        storage = new FileUtilJson(filePath, fileName);
        storageManager = new StorageManager();
    }

    /**
     * Creates a sample test JSON file to test Cube.
     * @throws CubeException
     */
    public void createTestFile(int NUM_OF_PRODUCTS) throws CubeException {
        FoodList foodList = storageManager.getFoodList();

        for (int i = 0; i < NUM_OF_PRODUCTS; i += 1) {
            String command = "add Food_" + i + " -t food -p " + i + " -s 5000 -e " + "31/12/2020";
            Command c = Parser.parse(command);
            c.execute(foodList, storageManager);
        }

        storage.save(storageManager);
    }

    public void testSaveTime() throws CubeException {
        long startTime = System.currentTimeMillis();
        storage.save(storageManager);
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed Save Time : " + (endTime - startTime) + " ms");
    }

    public void testLoadTime() throws CubeException {
        long startTime = System.currentTimeMillis();
        storageManager = storage.load();
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed Load Time : " + (endTime - startTime) + " ms");
    }

    public void generateSampleTestFile() throws CubeException {
        init("data","test");
        createTestFile(50);
    }

    @Test
    public void testFileUtilPerformance() throws CubeException {
        for (int i = 5; i <= 50000; i *= 10) {
            init("test",i + ".json");
            testLoadTime();
            testSaveTime();
            System.out.println();
        }
    }
}
