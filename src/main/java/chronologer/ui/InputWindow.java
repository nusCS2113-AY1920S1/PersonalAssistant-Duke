package chronologer.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public abstract class InputWindow extends UiComponent<Region> {

    /**
     * Constructs a UiComponent with the corresponding FXML file name and root object.
     * The FXML file written should not have a controller attribute as this is handled by the loadFXMLFile.
     *
     * @param fxmlFileName Holds the name of the corresponding FXML file.
     * @param root contains the region node.
     */
    @FXML
    TextField inputTextField;
    private List<String> userInputHistory = new ArrayList<>();
    private int userInputPointer = 0;
    private String currentInput = null;

    InputWindow(String fxmlFileName, Region root) {
        super(fxmlFileName, root);
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (userInputPointer == userInputHistory.size()) {
                currentInput = newValue;
            }
        });
    }

    private void next() {
        if (userInputPointer < userInputHistory.size() - 1) {
            userInputPointer += 1;
            setText(userInputHistory.get(userInputPointer));
        } else if (userInputPointer == userInputHistory.size() - 1) {
            userInputPointer += 1;
            setText(currentInput);
        }
    }

    private void previous() {
        if (userInputPointer >= 1) {
            userInputPointer -= 1;
            setText(userInputHistory.get(userInputPointer));
        }
    }

    private void setText(String text) {
        inputTextField.setText(text);
        inputTextField.positionCaret(inputTextField.getText().length());
    }

    /**
     * Handles the key press event which simulates command line.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.UP) {
            keyEvent.consume();
            previous();
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            keyEvent.consume();
            next();
        } else if (keyEvent.getCode() == KeyCode.ENTER) {
            keyEvent.consume();
            handleAction();
        }
    }

    protected abstract void handleAction();

    void storeUserInputHistory(String input) {
        if (userInputPointer != userInputHistory.size() - 1 || (userInputPointer == userInputHistory.size() - 1
            && !input.equals(userInputHistory.get(userInputPointer)))) {
            userInputHistory.add(input);
        }
        userInputPointer = userInputHistory.size();
        currentInput = null;
    }
}
