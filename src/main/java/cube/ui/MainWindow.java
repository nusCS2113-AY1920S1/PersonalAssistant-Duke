package cube.ui;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.command.util.CommandResult;
import cube.logic.parser.Parser;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.storage.ConfigStorage;
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


    public MainWindow(Stage primaryStage, StorageManager storageManager, FileUtilJson<StorageManager> storage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.storageManager = storageManager;
        this.storage = storage;
        this.configStorage = storageManager.getConfig();
        this.modelManager = new ModelManager(storageManager.getFoodList(), storageManager.getSalesHistory(), storageManager.getPromotionList());
    }

    public void show() {
        primaryStage.show();
    }

    public void initComponents() {
        logger.info("Initializing Cube GUI components.");

        initWindowSize();
        initWindowDrag();

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setResultText("Welcome to Cube!\nEnter 'help' to see the list of available commands.");

        overviewDisplay = new OverviewDisplay(storageManager.getFoodList().size(), Food.getRevenue(), Food.getRevenue());
        overviewDisplayPlaceholder.getChildren().add(overviewDisplay.getRoot());

        listPanel = new ListPanel(storageManager.getFoodList(), this::executeSell, this::executeDelete);
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());

        statusBar = new StatusBar(storage.getFileFullPath());
        statusbarPlaceholder.getChildren().add(statusBar.getRoot());
    }

    private void initWindowSize() {
        double windowHeight = configStorage.getUiConfig().getWindowHeight();
        double windowWidth = configStorage.getUiConfig().getWindowWidth();

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

    private CommandResult executeCommand(String command) throws CubeException {
        try {
            logger.info("Command Entered : " + command);
            Command c = Parser.parse(command);
            CommandResult result = c.execute(modelManager, storageManager);
            resultDisplay.setResultText(result.getFeedbackToUser());
            // Updates GUI components
            listPanel.updateProductList(storageManager.getFoodList());
            // TODO: Updated profits and revenue respectively
            overviewDisplay.updateOverview(storageManager.getFoodList().size(), Food.getRevenue(), Food.getRevenue());

            // TODO: isConfig()
            initWindowSize();
            if (result.isShowHelp()) {
                handleHelp();
            }
            if (result.isExit()) {
                handleExit();
            }

            storage.save(storageManager);
            return result;
        } catch (CubeException e) {
            logger.warning(e.getMessage());
            resultDisplay.setResultText(e.getMessage());
            throw e;
        }
    }

    private void executeSell(int index) {
        Food food = storageManager.getFoodList().get(index - 1);

        String command = "sold %1$s -q 1";
        commandBox.setCommandText(String.format(command, food.getName()));
    }

    private void executeDelete(int index) {
        String command = "delete -i %1$s";
        commandBox.setCommandText(String.format(command, index));
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
