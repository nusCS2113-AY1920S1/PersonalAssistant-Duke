/**
 * The primary window for the GUI, consists of multiple placeholders to load GUI elements,
 * as well as where most of the logic for GUI takes place.
 *
 * @author kuromono
 */

package cube.ui;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.command.ProfitCommand;
import cube.logic.command.ConfigCommand;
import cube.logic.command.SoldCommand;
import cube.logic.command.AddCommand;
import cube.logic.command.DeleteCommand;
import cube.logic.command.util.CommandResult;
import cube.logic.parser.Parser;
import cube.logic.parser.ParserUtil;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.storage.ConfigStorage;
import cube.storage.ProfitStorage;
import cube.storage.StorageManager;
import cube.storage.config.UiConfig;
import cube.util.FileUtilJson;
import cube.util.LogUtil;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.logging.Logger;

public class MainWindow extends UiManager<Stage> {
    public static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private OverviewDisplay overviewDisplay;
    private ListPanel listPanel;
    private StatusBar statusBar;

    @FXML
    private MenuBar menuBar;

    @FXML
    private GridPane menuBarPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane overviewDisplayPlaceholder;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    private static double windowOffsetX = 0;
    private static double windowOffsetY = 0;

    private StorageManager storageManager;
    private ConfigStorage configStorage;
    private FileUtilJson<StorageManager> storage;
    private ModelManager modelManager;
    private final Logger logger = LogUtil.getLogger(MainWindow.class);

    /**
     * Default constructor for MainWindow. Initialises all the required objects for Cube.
     *
     * @param primaryStage   PrimaryStage for GUI.
     * @param storageManager StorageManager containing all the data storages.
     * @param storage        File Utility the handles read/write operations.
     */
    public MainWindow(Stage primaryStage, StorageManager storageManager,
                      FileUtilJson<StorageManager> storage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.storageManager = storageManager;
        this.storage = storage;
        this.configStorage = storageManager.getConfig();
        this.modelManager = new ModelManager(storageManager.getFoodList(),
            storageManager.getSalesHistory(), storageManager.getPromotionList());
    }

    /**
     * Show the main window.
     */
    public void show() {
        primaryStage.show();
    }

    /**
     * Initialize the components.
     */
    public void initComponents() {
        logger.info("Initializing Cube GUI components.");

        initWindowSize();
        initWindowDrag();

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setResultText("Welcome to Cube!\nEnter 'help' to see the list of available commands.");

        ProfitCommand.generateAnnualProfitRevenue(modelManager);
        overviewDisplay = new OverviewDisplay(storageManager.getFoodList().size(),
            ProfitStorage.getAnnualProfit(), ProfitStorage.getAnnualRevenue());
        overviewDisplayPlaceholder.getChildren().add(overviewDisplay.getRoot());

        listPanel = new ListPanel(storageManager.getFoodList(), this::executeSell, this::executeEdit);
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());

        statusBar = new StatusBar(storage.getFileFullPath());
        statusbarPlaceholder.getChildren().add(statusBar.getRoot());
    }

    /**
     * Initialises the window size and set the maximum width and height.
     */
    private void initWindowSize() {
        final double windowHeight = configStorage.getUiConfig().getWindowHeight();
        final double windowWidth = configStorage.getUiConfig().getWindowWidth();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double maxHeight = primaryScreenBounds.getHeight();
        double maxWidth = primaryScreenBounds.getWidth();

        UiConfig.setMaxWindowHeight(maxHeight);
        UiConfig.setMaxWindowWidth(maxWidth);

        if (windowHeight >= primaryStage.getMinHeight() && windowHeight <= maxHeight) {
            primaryStage.setHeight(windowHeight);
        }
        if (windowWidth >= primaryStage.getMinWidth() && windowWidth <= maxWidth) {
            primaryStage.setWidth(windowWidth);
        }
    }

    /**
     * Initialises the ability for the GUI window to be dragged with a mouse.
     */
    private void initWindowDrag() {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        menuBar.setOnMousePressed(event -> {
            windowOffsetX = event.getSceneX();
            windowOffsetY = event.getSceneY();
        });
        menuBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - windowOffsetX);
            primaryStage.setY(event.getScreenY() - windowOffsetY);
        });

        primaryStage.getScene().setOnMousePressed(event -> {
            windowOffsetX = event.getSceneX();
            windowOffsetY = event.getSceneY();
        });
        primaryStage.getScene().setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - windowOffsetX);
            primaryStage.setY(event.getScreenY() - windowOffsetY);
        });
    }

    /**
     * Function that allows commands to be executed from within different GUI elements.
     *
     * @param command Command string to be parsed and executed.
     * @return CommandResult that updates the GUI based on the command feedback.
     * @throws CubeException Exception whenever error within command.
     */
    private CommandResult executeCommand(String command) throws CubeException {
        try {
            logger.info("Command Entered : " + command);
            Command c = Parser.parse(command);
            CommandResult result = c.execute(modelManager, storageManager);

            resultDisplay.setResultText(result.getFeedbackToUser());
            // Updates GUI components
            listPanel.updateProductList(storageManager.getFoodList());

            if (c instanceof SoldCommand) {
                ProfitCommand.generateAnnualProfitRevenue(modelManager);
            }
            if (c instanceof ConfigCommand) {
                initWindowSize();
            }
            if (result.isShowHelp()) {
                handleHelp();
            }
            if (result.isExit()) {
                handleExit();
            }
            overviewDisplay.updateOverview(storageManager.getFoodList().size(),
                ProfitStorage.getAnnualProfit(), ProfitStorage.getAnnualRevenue());

            storage.save(storageManager);
            return result;
        } catch (CubeException e) {
            logger.warning(e.getMessage());
            resultDisplay.setResultText(e.getMessage());
            throw e;
        }
    }

    /**
     * Function for Edit Button. Generates the edit command for the current object to commandbox.
     *
     * @param index Index of current product.
     */
    private void executeEdit(int index) {
        Food food = storageManager.getFoodList().get(index - 1);
        String command = "update %1$s -t %2$s -p %3$s -c %4$s -s %5$s -e %6$s";

        commandBox.setCommandText(String.format(command, food.getName(), food.getType(),
            food.getPrice(), food.getCost(), food.getStock(),
            ParserUtil.parseDateToString(food.getExpiryDate())));
    }

    /**
     * Function for Sell Button. Generates the sell command for the current object to commandbox.
     *
     * @param index Index of current product.
     */
    private void executeSell(int index) {
        Food food = storageManager.getFoodList().get(index - 1);

        String command = "sold %1$s -q 1";
        commandBox.setCommandText(String.format(command, food.getName()));
    }

    /**
     * Minimizes the application.
     */
    @FXML
    private void handleMinimize() {
        primaryStage.setIconified(true);
    }

    /**
     * Maximizes the application.
     */
    @FXML
    private void handleMaximize() {
        if (primaryStage.isMaximized()) {
            primaryStage.setMaximized(false);
        } else {
            primaryStage.setMaximized(true);
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        primaryStage.hide();
    }

    /**
     * Shows the help window.
     */
    @FXML
    private void handleHelp() {
        String command = "help";
        commandBox.setCommandText(command);
    }

}
