package cube.logic.command;

import cube.exception.CubeException;
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
        EXPORT, IMPORT
    }

    private String fileName;
    private BatchCommand.OperationType operationType;
    private FileUtilCSV<Food> batchUtil;

    private final String MESSAGE_SUCCESS = "The product list has been successfully %1$s as file:\n"
        + "%2$s\n";
    private final String MESSAGE_EXPORT = "exported";
    private final String MESSAGE_IMPORT = "imported";

    public BatchCommand(String fileName, String operationType) {
        this.fileName = fileName;
        this.operationType = OperationType.valueOf(operationType);
        this.batchUtil = new FileUtilCSV<>("data", fileName, new Food());
    }

    private void batchImport(ModelManager model, StorageManager storage) throws CubeException {
        ArrayList<Food> foodList = batchUtil.load();
        FoodList importedFoodList = new FoodList(foodList);
        model.setFoodList(importedFoodList);
        storage.storeFoodList(importedFoodList);
    }

    private void batchExport(StorageManager storage) throws CubeException {
        batchUtil.save(storage.getFoodList().getFoodList());
    }

    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) {
        try {
            switch (operationType) {
            case IMPORT:
                batchImport(model, storage);
                return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_IMPORT, fileName));
            case EXPORT:
                batchExport(storage);
                return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_EXPORT, fileName));
            }
        } catch (CubeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
