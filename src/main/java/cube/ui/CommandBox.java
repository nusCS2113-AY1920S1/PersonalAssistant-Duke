package cube.ui;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.parser.Parser;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import cube.util.FileUtilJson;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;


public class CommandBox extends UiManager<StackPane> {
    private static final String FXML = "CommandBox.fxml";

    @FXML
    private TextField commandTextField;

    private StorageManager storageManager;
    private FileUtilJson storage;
    private FoodList foodList;

    public CommandBox() {
        super(FXML);
    }

    public CommandBox(StorageManager storageManager, FileUtilJson storage, FoodList foodList) {
        super(FXML);
        this.storageManager = storageManager;
        this.storage = storage;
        this.foodList = foodList;
    }

    /**
     * Handles the Enter button pressed event.
     *
     * Currently bad implementation, need changes to model to work.
     */
    @FXML
    private void handleCommandEntered() {
        String fullCommand = commandTextField.getText();

        try {
            Command c = Parser.parse(fullCommand);
            //c.execute(foodList, new Ui(), storageManager);
            c.execute(foodList, storageManager);
            storage.save(storageManager);
        } catch (CubeException e) {
            e.printStackTrace();
        }

        System.out.println(commandTextField.getText());
        commandTextField.clear();
    }
}
