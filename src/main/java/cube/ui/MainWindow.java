package cube.ui;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.parser.Parser;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import cube.util.FileUtilJson;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;

public class MainWindow extends UiManager<Stage> {
    public static final String FXML = "MainWindow.fxml";

    private Stage primaryStage;
    private ResultDisplay resultDisplay;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    private StorageManager storageManager;
    private FileUtilJson storage;
    private FoodList foodList;

    public MainWindow (Stage primaryStage) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
    }

    public MainWindow (Stage primaryStage, StorageManager storageManager, FileUtilJson storage, FoodList foodList) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.storageManager = storageManager;
        this.storage = storage;
        this.foodList = foodList;
    }

    public void show() {
        primaryStage.show();
    }

    public void initComponents() {
        //CommandBox commandBox = new CommandBox();
        CommandBox commandBox = new CommandBox(storageManager, storage, foodList);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
    }

    private String cmdExecutor(String input) {
        try {
            Command c = Parser.parse(input);
            c.execute(foodList, new Ui(), storageManager);
            storage.save(storageManager);
        } catch (CubeException e) {
            e.printStackTrace();
        }
        return input;
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
