/**
 * BatchCommand.java
 * Support commands related to batch file imports & exports.
 *
 * @author kuromono
 */

package cube.logic.command;

import cube.exception.CubeException;
import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import cube.util.FileUtilCSV;

import java.util.ArrayList;

public class BatchCommand extends Command {
    /**
     * Use enums to specify the export or import operations to be performed by FileUtilCSV.
     */
    public enum OperationType {
        EXPORT, IMPORT, EMPTY
    }

    private String fileName;
    private BatchCommand.OperationType operationType;
    private FileUtilCSV<Food> batchUtil;

    public static final String MESSAGE_SUCCESS = "The product list has been successfully %1$s as file:\n"
        + "%2$s\n";
    public static final String MESSAGE_EXPORT = "exported";
    public static final String MESSAGE_IMPORT = "imported";
    public static final String MESSAGE_SUCCESS_TEMPLATE = "An empty template has been successfully generated as file:\n"
        + "%1$s\n";

    public static final String MESSAGE_FILE_NOT_FOUND = "The file that you are importing cannot be found:\n"
        + "%1$s\n";

    /**
     * Default constructor.
     *
     * @param fileName      Sets the filename of the CSV file to be loaded/saved from.
     * @param operationType Specifies to either IMPORT or EXPORT operation.
     */
    public BatchCommand(String fileName, String operationType) {
        this.fileName = fileName;
        this.operationType = OperationType.valueOf(operationType);
        this.batchUtil = new FileUtilCSV<>("data", fileName, new Food());
    }

    /**
     * Calls & updates the required functions for CSV batch import operations.
     *
     * @param model   ModelManager object to update the FoodList object from the import.
     * @param storage StorageManager object to update the FoodList object from the import.
     * @throws CubeException Throws an exception if error occured during file handling.
     */
    private boolean batchImport(ModelManager model, StorageManager storage) throws CubeException {
        if (batchUtil.checkFileAvailable(false)) {
            ArrayList<Food> foodList = batchUtil.load();
            FoodList importedFoodList = new FoodList(foodList);
            model.setFoodList(importedFoodList);
            storage.storeFoodList(importedFoodList);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calls & updates the required functions for CSV batch export operations.
     *
     * @param storage StorageManager object that contains the FoodList object to be saved.
     * @throws CubeException Throws an exception if error occured during file handling.
     */
    private void batchExport(StorageManager storage) throws CubeException {
        batchUtil.save(storage.getFoodList().getFoodList());
    }

    /**
     * Creates a empty template CSV file.
     *
     * @throws CubeException Throws an exception if error occured during file handling.
     */
    private void batchEmpty() throws CubeException {
        batchUtil.save(new ArrayList<>());
    }

    /**
     * Constructs the command result output to be shown to the user.
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        try {
            switch (operationType) {
            case IMPORT:
                boolean importSuccess = batchImport(model, storage);
                if (importSuccess) {
                    return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_IMPORT, fileName));
                } else {
                    return new CommandResult(String.format(MESSAGE_FILE_NOT_FOUND, fileName));
                }
            case EXPORT:
                batchExport(storage);
                return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_EXPORT, fileName));
            case EMPTY:
                batchEmpty();
                return new CommandResult(String.format(MESSAGE_SUCCESS_TEMPLATE, fileName));
            }
        } catch (CubeException e) {
            throw new CommandException(CommandErrorMessage.INVALID_COMMAND_FORMAT);
        }
        return null;
    }
}
