package cube.ui;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.command.CommandResult;
import cube.logic.parser.Parser;
import cube.model.FoodList;
import cube.storage.StorageManager;
import cube.storage.ConfigStorage;
import cube.util.FileUtilJson;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;

public class MainWindow extends UiManager<Stage> {
    public static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;
    private ResultDisplay resultDisplay;
    private ListPanel listPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane listPanelPlaceholder;

    private StorageManager storageManager;
    private ConfigStorage configStorage;
    private FileUtilJson storage;
    private FoodList foodList;

    public MainWindow (Stage primaryStage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
    }

    public MainWindow (Stage primaryStage, StorageManager storageManager, FileUtilJson storage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.storageManager = storageManager;
        this.storage = storage;
        this.configStorage = storageManager.getConfig();
        this.foodList = storageManager.getFoodList();
    }

    public void show() {
        primaryStage.show();
    }

    public void initComponents() {
        primaryStage.setHeight(configStorage.getWindowHeight());
        primaryStage.setWidth(configStorage.getWindowWidth());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        listPanel = new ListPanel();
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());
    }

    private CommandResult executeCommand(String command) throws CubeException {
        try {
            Command c = Parser.parse(command);
            CommandResult result = c.execute(foodList, storageManager);
            resultDisplay.setResultText(result.getFeedbackToUser());

            if (result.isShowHelp()) {
                handleHelp();
            }
            if (result.isExit()) {
                handleExit();
            }

            storage.save(storageManager);
            return result;
        } catch (CubeException e) {
            e.printStackTrace();
            resultDisplay.setResultText(e.getMessage());
            throw e;
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
    public void handleHelp() {
        //add a pop-up box here
    }

}
