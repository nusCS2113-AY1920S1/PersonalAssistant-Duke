//@@author LL-Pengfei
/**
 * Cube.java
 * The driver file, which is the entry point for Command Line Interface.
 */
package cube;

import cube.model.food.FoodList;
import cube.model.sale.SalesHistory;
import cube.model.promotion.PromotionList;
import cube.model.ModelManager;
import cube.ui.Ui;
import cube.logic.parser.Parser;
import cube.logic.command.Command;
import cube.logic.command.util.CommandResult;
import cube.util.FileUtilJson;
import cube.storage.*;
import cube.exception.CubeException;
import cube.util.LogUtil;

import java.util.logging.Logger;

/**
 * The Entry Point for the Command Line Interface.
 */
public class Cube {
    private StorageManager storageManager;
    private ModelManager modelManager;
    private FileUtilJson<StorageManager> storage;
    private Ui ui;
    private final Logger logger = LogUtil.getLogger(Cube.class);

    /**
     * The Cube constructor with filePath.
     *
     * @param filePath The file path where the Cube data is stored.
     */
    public Cube(String filePath) {
        logger.info("=============================[ Initializing Cube ]===========================");
        ui = new Ui();
        storageManager = new StorageManager();
        storage = new FileUtilJson<>(filePath, "cube.json", storageManager);

        try {
            LogUtil.init(storageManager.getConfig().getLogConfig());
            storageManager = storage.load();
            FoodList foodList = storageManager.getFoodList();
            SalesHistory salesHistory = storageManager.getSalesHistory();
            PromotionList promotionList = storageManager.getPromotionList();
            modelManager = new ModelManager(foodList, salesHistory, promotionList);
        } catch (CubeException e) {
            logger.warning(e.getMessage());
            ui.showLoadingError(filePath);
            modelManager = new ModelManager();
        }
    }

    /**
     * Run the Cube programme with received user commands and execute the commands.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                logger.info("Command Entered : " + fullCommand);
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                isExit = c.isExit();
                CommandResult result = c.execute(modelManager, storageManager);
                ui.showCommandResult(result);
                storage.save(storageManager);
            } catch (CubeException e) {
                logger.warning(e.getMessage());
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        logger.info("=============================[  Exiting Cube  ]==============================");
    }

    /**
     * Initialize new Cube user and run the programme.
     *
     * @param args The programme arguments.
     */
    public static void main(String[] args) {
        //future upgrade: allow user to specify data path
        new Cube("data").run();
    }
}
