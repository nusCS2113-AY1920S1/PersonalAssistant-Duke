package cube.ui;

import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;


public class CommandBox extends UiManager<StackPane> {
    private static final String FXML = "CommandBox.fxml";

    @FXML
    private TextField commandTextField;

    public CommandBox() {
        super(FXML);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        System.out.println(commandTextField.getText());
        commandTextField.clear();
    }
}
