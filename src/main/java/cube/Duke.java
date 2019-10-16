/**
 * This is the main entrance of Duke programme.
 *
 * @author tygq13
 */
package cube;

import cube.model.food.FoodList;
import cube.model.food.Food;
import cube.ui.Ui;
import cube.logic.parser.Parser;
import cube.util.FileUtilJson;
import cube.logic.command.Command;
import cube.storage.*;
import cube.exception.CubeException;

/**
 * the main class of Duke Programme
 */
public class Duke {

    private StorageManager storageManager;
    private FileUtilJson storage;
    private FoodList foodList;
    private Ui ui;

    /**
     * Duke constructor with filePath.
     *
     * @param filePath the file path where duke data is stored.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new FileUtilJson(filePath);

        try {
            storageManager = storage.load();
            foodList = storageManager.getFoodList();
        } catch (CubeException e) {
            ui.showLoadingError(filePath);
            foodList = new FoodList();
            storageManager = new StorageManager();
        }
    }

    /**
     * Runs the Duke programme by receiving user commands and executing the commands.
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
                c.execute(foodList, ui, storageManager);
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
        new Duke("data").run();
    }
}
