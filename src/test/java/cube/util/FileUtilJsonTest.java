package util;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.parser.Parser;
import cube.storage.StorageManager;
import cube.model.FoodList;
import cube.util.FileUtilJson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilJsonTest {

    /**
     * Creates a sample test JSON file to test Cube.
     * @throws CubeException
     */
    @Test
    public void createTestFile() throws CubeException {
        FileUtilJson storage = new FileUtilJson("data");
        StorageManager storageManager = new StorageManager();
        FoodList foodList = storageManager.getFoodList();

        int NUM_OF_PRODUCTS = 30;

        for (int i = 0; i < NUM_OF_PRODUCTS; i += 1) {
            String command = "add Food_" + i + " -t food -p " + i + " -s 5000 -e " + "31/12/2020";
            Command c = Parser.parse(command);
            c.execute(foodList, storageManager);
        }

        storage.save(storageManager);

        // temporary placeholder
        assertTrue(true);
    }
}
