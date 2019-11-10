/**
 * Testing for FileUtilCSV utilities
 *
 * @author kuromono
 */

package cube.util;

import cube.exception.CubeException;
import cube.logic.parser.exception.ParserException;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.testutil.SampleUtil;
import cube.testutil.StorageUtil;
import cube.util.exception.CubeUtilException;
import cube.util.exception.UtilErrorMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static cube.testutil.Assert.assertThrowEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilCSVTest {
    private static FoodList foodList;
    private static String fileName;
    private static FileUtilCSV<Food> csvstorage;

    /**
     * Initializes variables used for the tests.
     */
    @BeforeAll
    public static void initVariables() throws ParserException {
        foodList = SampleUtil.generateSampleData(5);
        fileName = "test_fileutil.csv";
    }

    /**
     * Resets FileUtilCSV for every test run.
     */
    @BeforeEach
    public void clearVariables() {
        csvstorage = new FileUtilCSV<>("data", fileName, new Food());
    }

    /**
     * Deletes all the files used during this test.
     */
    @AfterAll
    public static void deleteTestFiles() {
        StorageUtil.deleteFile(fileName);
        StorageUtil.deleteFile("test_load_non_existing_file.csv");
    }

    /**
     * Test exporting a CSV file.
     */
    @Test
    @Order(1)
    public void save_file() throws CubeException {
        csvstorage.save(foodList.getFoodList());

        assertTrue(StorageUtil.checkFileAvailable(fileName));
    }

    /**
     * Test importing a CSV file with sample values.
     */
    @Test
    @Order(2)
    public void load_file() throws CubeException {
        ArrayList<Food> resultFoodList = csvstorage.load();

        assertTrue(StorageUtil.checkFileAvailable(fileName));
        assertEquals(resultFoodList.toString(), foodList.getFoodList().toString());
    }

    /**
     * Test importing a non existent CSV file.
     */
    @Test
    @Order(3)
    public void load_non_existing_file() {
        String fileName = "test_load_non_existing_file.csv";
        csvstorage = new FileUtilCSV<>("data", fileName, new Food());

        assertThrowEquals(CubeUtilException.class, UtilErrorMessage.READ_ERROR + csvstorage.getFileFullPath(), () -> {
            csvstorage.load();
        });
    }
}

