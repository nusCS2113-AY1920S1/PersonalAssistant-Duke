package cube;

import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.sale.SalesHistory;
import cube.model.ModelManager;
import cube.ui.Ui;
import cube.logic.parser.Parser;
import cube.logic.command.Command;
import cube.logic.command.util.CommandResult;
import cube.util.FileUtilJson;
import cube.storage.*;
import cube.exception.CubeException;


public class Cube {

    private StorageManager storageManager;
    private ModelManager modelManager;
    private FileUtilJson storage;
    private FoodList foodList;
    private SalesHistory salesHistory;
    private Ui ui;

    /**
     * Cube constructor with filePath.
     *
     * @param filePath the file path where duke data is stored.
     */
    public Cube(String filePath) {
        ui = new Ui();
        storage = new FileUtilJson(filePath);

        try {
            storageManager = storage.load();
            foodList = storageManager.getFoodList();
            modelManager = new ModelManager(foodList, new SalesHistory());
            Food.updateRevenue(storageManager.getRevenue());
        } catch (CubeException e) {
            ui.showLoadingError(filePath);
            storageManager = new StorageManager();
            foodList = new FoodList();
            modelManager = new ModelManager();
        }
    }

    /**
     * Runs the Cube programme by receiving user commands and executing the commands.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                isExit = c.isExit();
                CommandResult result = c.execute(modelManager, storageManager);
                ui.showCommandResult(result);
                storage.save(storageManager);
            } catch (CubeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Initializes new Duke user and runs the programme.
     * @param args programme arguments.
     */
    public static void main(String[] args) {
        //todo: allow user to specify data path
        new Cube("data").run();
    }
}
