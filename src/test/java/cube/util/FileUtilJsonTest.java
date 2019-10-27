package cube.util;

import cube.exception.CubeException;
import cube.logic.parser.ParserUtil;
import cube.model.food.Food;
import cube.storage.StorageManager;
import cube.model.food.FoodList;
import org.junit.jupiter.api.Test;


public class FileUtilJsonTest {

    private FileUtilJson<StorageManager> storage;
    private StorageManager storageManager;

    public void init(String filePath, String fileName) throws CubeException {
        storageManager = new StorageManager();
        storage = new FileUtilJson(filePath, fileName, storageManager);
    }

    /**
     * Creates a sample test JSON file to test Cube.
     * @throws CubeException
     */
    public void createTestFile(int NUM_OF_PRODUCTS) throws CubeException {
        FoodList foodList = storageManager.getFoodList();

        for (int i = 0; i < NUM_OF_PRODUCTS; i += 1) {
            int testFoodIdx = i + 1;
            Food testFood = new Food("Food_" + testFoodIdx);
            testFood.setType("food");
            testFood.setPrice(testFoodIdx);
            testFood.setCost(i);
            testFood.setStock(5000);
            testFood.setExpiryDate(ParserUtil.parseStringToDate("31/12/2020"));

            foodList.add(testFood);
        }

        storageManager.storeFoodList(foodList);
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

    @Test
    public void generateSampleTestFile() throws CubeException {
        init("data","cube.json");
        createTestFile(50);
    }

    public void testFileUtilPerformance() throws CubeException {
        for (int i = 5; i <= 50000; i *= 10) {
            init("test",i + ".json");
            testLoadTime();
            testSaveTime();
            System.out.println();
        }
    }
}
