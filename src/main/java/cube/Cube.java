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


public class Cube {

    private StorageManager storageManager;
    private ModelManager modelManager;
    private FileUtilJson<StorageManager> storage;
    private FoodList foodList;
    private SalesHistory salesHistory;
    private PromotionList promotionList;
    private Ui ui;
    private final Logger logger = LogUtil.getLogger(Cube.class);

    /**
     * Cube constructor with filePath.
     *
     * @param filePath the file path where duke data is stored.
     */
    public Cube(String filePath) {
        logger.info("=============================[ Initializing Cube ]===========================");
        ui = new Ui();
        storageManager = new StorageManager();
        storage = new FileUtilJson<>(filePath, "cube.json", storageManager);

        try {
            LogUtil.init(storageManager.getConfig().getLogConfig());
            storageManager = storage.load();
            foodList = storageManager.getFoodList();
            salesHistory = storageManager.getSalesHistory();
            promotionList = storageManager.getPromotionList();
            modelManager = new ModelManager(foodList, salesHistory, promotionList);
        } catch (CubeException e) {
            logger.warning(e.getMessage());
            ui.showLoadingError(filePath);
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
     * Initializes new Duke user and runs the programme.
     * @param args programme arguments.
     */
    public static void main(String[] args) {
        //todo: allow user to specify data path
        new Cube("data").run();
    }
}
