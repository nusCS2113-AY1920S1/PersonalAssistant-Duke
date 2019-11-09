/**
 * Testing for BatchCommand utilities
 *
 * @author kuromono
 */

package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.logic.parser.exception.ParserException;
import cube.model.ModelManager;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import cube.testutil.SampleUtil;
import cube.testutil.StorageUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

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
        foodList = SampleUtil.generateSampleData(5);
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
        StorageUtil.deleteFile("test_export_file.csv");
        StorageUtil.deleteFile("test_create_empty_template.csv");
        StorageUtil.deleteFile("test_execute_invalid_command.csv");
    }

    /**
     * Test exporting a CSV file with sample values.
     */
    @Test
    @Order(1)
    public void export_file() throws CommandException {
        String fileName = "test_export_file.csv";
        storage.storeFoodList(foodList);
        BatchCommand command = new BatchCommand(fileName, BatchCommand.OperationType.EXPORT);
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(String.format(BatchCommand.MESSAGE_SUCCESS, BatchCommand.MESSAGE_EXPORT, fileName));
        assertEquals(result, expectedResult);
        assertTrue(StorageUtil.checkFileAvailable(fileName));
    }

    /**
     * Test importing a CSV file.
     */
    @Test
    @Order(2)
    public void import_file() throws CommandException {
        String fileName = "test_export_file.csv";
        BatchCommand command = new BatchCommand(fileName, BatchCommand.OperationType.IMPORT);
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
        BatchCommand command = new BatchCommand(fileName, BatchCommand.OperationType.IMPORT);
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
        BatchCommand command = new BatchCommand(fileName, BatchCommand.OperationType.EMPTY);
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(String.format(BatchCommand.MESSAGE_SUCCESS_TEMPLATE, fileName));
        FoodList expectedList = new FoodList();

        assertEquals(result, expectedResult);
        assertTrue(StorageUtil.checkFileAvailable(fileName));
        assertEquals(model.getFoodList(), expectedList);
    }
}
