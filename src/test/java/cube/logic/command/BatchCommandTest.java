/**
 * Testing for BatchCommand utilities
 *
 * @author kuromono
 */

package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.parser.ParserUtil;
import cube.logic.parser.exception.ParserException;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BatchCommandTest {

    private static ModelManager model;
    private static StorageManager storage;
    private static FoodList foodList;

    /**
     * Initializes variables used for the tests.
     */
    @BeforeAll
    public static void initVariables() throws ParserException {
        model = new ModelManager();
        storage = new StorageManager();
        foodList = generateSampleData(5);
    }

    /**
     * Resets ModelManager & StorageManager for every test run.
     */
    @AfterEach
    public void clearVariables() {
        model = new ModelManager();
        storage = new StorageManager();
    }

    /**
     * Deletes all the files used during this test.
     */
    @AfterAll
    public static void deleteTestFiles() {
        deleteFile("test_export_file.csv");
        deleteFile("test_create_empty_template.csv");
        deleteFile("test_execute_invalid_command.csv");
    }

    /**
     * Test exporting a CSV file with sample values.
     */
    @Test
    @Order(1)
    public void export_file() throws CommandException {
        String fileName = "test_export_file.csv";
        storage.storeFoodList(foodList);
        BatchCommand command = new BatchCommand(fileName, "EXPORT");
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(String.format(BatchCommand.MESSAGE_SUCCESS, BatchCommand.MESSAGE_EXPORT, fileName));
        assertEquals(result, expectedResult);
        assertTrue(checkFileAvailable(fileName));
    }

    /**
     * Test importing a CSV file.
     */
    @Test
    @Order(2)
    public void import_file() throws CommandException {
        String fileName = "test_export_file.csv";
        BatchCommand command = new BatchCommand(fileName, "IMPORT");
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(String.format(BatchCommand.MESSAGE_SUCCESS, BatchCommand.MESSAGE_IMPORT, fileName));
        assertEquals(result, expectedResult);
        assertEquals(storage.getFoodList().toString(), foodList.toString());
    }

    /**
     * Test importing a non-existent CSV file.
     */
    @Test
    @Order(3)
    public void import_non_existing_file() throws CommandException {
        String fileName = "test_import_non_existing_file.csv";
        BatchCommand command = new BatchCommand(fileName, "IMPORT");
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(String.format(BatchCommand.MESSAGE_FILE_NOT_FOUND, fileName));
        assertEquals(result, expectedResult);
    }

    /**
     * Test creation of an empty CSV template for Foodlist.
     */
    @Test
    @Order(4)
    public void create_empty_template() throws CommandException {
        String fileName = "test_create_empty_template.csv";
        BatchCommand command = new BatchCommand(fileName, "EMPTY");
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(String.format(BatchCommand.MESSAGE_SUCCESS_TEMPLATE, fileName));
        FoodList expectedList = new FoodList();

        assertEquals(result, expectedResult);
        assertTrue(checkFileAvailable(fileName));
        assertEquals(model.getFoodList(), expectedList);
    }

    /**
     * Creates a sample test JSON file to test Cube.
     */
    private static FoodList generateSampleData(int NUM_OF_PRODUCTS) throws ParserException {
        FoodList foodList = new FoodList();

        for (int i = 0; i < NUM_OF_PRODUCTS; i += 1) {
            int testFoodIndex = i + 1;
            Food testFood = new Food("Food_" + testFoodIndex);
            testFood.setType("food");
            testFood.setPrice(testFoodIndex);
            testFood.setCost(i);
            testFood.setStock(5000);
            testFood.setExpiryDate(ParserUtil.parseStringToDate("31/12/2020"));

            foodList.add(testFood);
        }

        return foodList;
    }

    /**
     * Boolean check to see if file exists or not.
     *
     * @return true if data file exists, false if not found.
     */
    private boolean checkFileAvailable(String fileName) {
        String fileFullPath = "data" + File.separator + fileName;
        File file = new File(fileFullPath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes the file with the specified filename.
     */
    private static void deleteFile(String fileName) {
        String fileFullPath = "data" + File.separator + fileName;
        File file = new File(fileFullPath);
        file.delete();
    }
}
