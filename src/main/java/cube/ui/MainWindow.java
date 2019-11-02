package cube.ui;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.command.util.CommandResult;
import cube.logic.parser.Parser;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.promotion.PromotionList;
import cube.model.sale.SalesHistory;
import cube.storage.ConfigStorage;
import cube.storage.StorageManager;
import cube.util.FileUtilJson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    @FXML
    private Button resizeBtn;

    private static double windowOffsetX = 0;
    private static double windowOffsetY = 0;

    private StorageManager storageManager;
    private ConfigStorage configStorage;
    private FileUtilJson<StorageManager> storage;
    private FoodList foodList;
    private SalesHistory salesHistory;
    private PromotionList promotionList;
    private ModelManager modelManager;

    public MainWindow(Stage primaryStage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
    }

    public MainWindow(Stage primaryStage, StorageManager storageManager, FileUtilJson<StorageManager> storage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.storageManager = storageManager;
        this.storage = storage;
        this.configStorage = storageManager.getConfig();
        this.foodList = storageManager.getFoodList();
        this.salesHistory = storageManager.getSalesHistory();
        // Temporary Fix TODO: implement Promotion to storage manager.
        this.promotionList = new PromotionList();
        this.modelManager = new ModelManager(foodList, salesHistory, promotionList);
    }

    public void show() {
        primaryStage.show();
    }

    public void initComponents() {
        double windowHeight = configStorage.getUiConfig().getWindowHeight();
        double windowWidth = configStorage.getUiConfig().getWindowWidth();

        if (windowHeight > primaryStage.getMinHeight() && windowWidth > primaryStage.getMinWidth()) {
            primaryStage.setHeight(windowHeight);
            primaryStage.setWidth(windowWidth);
        }

        primaryStage.initStyle(StageStyle.UNDECORATED);

        draggableMenuBar();

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        overviewDisplay = new OverviewDisplay(foodList.size(), Food.getRevenue(), Food.getRevenue());
        overviewDisplayPlaceholder.getChildren().add(overviewDisplay.getRoot());

        listPanel = new ListPanel(foodList, this::executeEdit, this::executeDelete);
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());

        statusBar = new StatusBar(storage.getFileFullPath());
        statusbarPlaceholder.getChildren().add(statusBar.getRoot());
    }

    private CommandResult executeCommand(String command) throws CubeException {
        try {
            Command c = Parser.parse(command);
            CommandResult result = c.execute(modelManager, storageManager);
            resultDisplay.setResultText(result.getFeedbackToUser());
            // Updates GUI components
            listPanel.updateProductList(storageManager.getFoodList());
            // TODO: Updated profits and revenue respectively
            overviewDisplay.updateOverview(foodList.size(), 0, 0);

            if (result.isShowHelp()) {
                handleHelp();
            }
            if (result.isExit()) {
                handleExit();
            }

            storage.save(storageManager);
            return result;
        } catch (CubeException e) {
            resultDisplay.setResultText(e.getMessage());
            throw e;
        }
    }

    private void executeEdit(int index) {
        Food food = foodList.get(index - 1);

        String command = "edit -i %1$s -n %2$s -t %3$s -p %4$s -s %5$s -e %6$s";
        commandBox.setCommandText(String.format(command, index, food.getName(), food.getType(), food.getPrice(), food.getStock(), food.getExpiryDate()));
    }

    private void executeDelete(int index) {
        String command = "delete -i %1$s";
        commandBox.setCommandText(String.format(command, index));
    }

    private void draggableMenuBar() {
        primaryStage.setResizable(true);

        menuBar.setOnMousePressed(event -> {
            windowOffsetX = event.getSceneX();
            windowOffsetY = event.getSceneY();
        });
        menuBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - windowOffsetX);
            primaryStage.setY(event.getScreenY() - windowOffsetY);
        });

        menuBarPane.setOnMousePressed(event -> {
            windowOffsetX = event.getSceneX();
            windowOffsetY = event.getSceneY();
        });
        menuBarPane.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - windowOffsetX);
            primaryStage.setY(event.getScreenY() - windowOffsetY);
        });
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
