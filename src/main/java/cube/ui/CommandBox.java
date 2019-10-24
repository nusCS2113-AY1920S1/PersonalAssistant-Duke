package cube.ui;

import cube.exception.CubeException;
import cube.logic.command.Command;
import cube.logic.command.CommandResult;
import cube.logic.parser.Parser;
import cube.model.FoodList;
import cube.storage.StorageManager;
import cube.util.FileUtilJson;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;


public class CommandBox extends UiManager<StackPane> {
    private static final String FXML = "CommandBox.fxml";

    @FXML
    private TextField commandTextField;

    private final CommandExecutor commandExecutor;

    private StorageManager storageManager;
    private FileUtilJson storage;
    private FoodList foodList;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
    }

    /**
     * Handles the Enter button pressed event listener.
     */
    @FXML
    private void handleCommandEntered() {
        String fullCommand = commandTextField.getText();

        try {
            commandExecutor.execute(fullCommand);
        } catch (CubeException e) {
            e.printStackTrace();
        }

        System.out.println(commandTextField.getText());
        commandTextField.clear();
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         */
        CommandResult execute(String commandText) throws CubeException;
    }
}
