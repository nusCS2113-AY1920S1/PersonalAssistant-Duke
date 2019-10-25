package duke.ui;
import duke.command.Executor;
import duke.command.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

abstract class InputHistoryWindow extends UiElement<Region> {

    @FXML
    private AutoCompleteTextField inputTextField;
    @FXML
    private Button sendButton;

    // TODO: A separate (inner) class for input history
    private List<String> inputHistory;
    private int historyPointer;
    private String currentInput;

    /**
     * Constructs the command window of the application.
     */
    InputHistoryWindow(String fxmlFileName, Region root) {
        super(fxmlFileName, root);

        inputHistory = new ArrayList<>();
        historyPointer = 0;

        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (historyPointer == inputHistory.size()) {
                currentInput = newValue;
            }
        });
    }

    /**
     * Updates the text field with the previous input in {@code inputHistory},
     * if there exists a previous input in {@code inputHistory}.
     */
    private void navigateToPreviousInput() {
        if (historyPointer > 0) {
            historyPointer = historyPointer - 1;
            setText(inputHistory.get(historyPointer));
        }
    }

    /**
     * Updates the text field with the next input in {@code inputHistory},
     * if there exists a next input in {@code inputHistory}.
     */
    private void navigateToNextInput() {
        if (historyPointer < inputHistory.size() - 1) {
            historyPointer = historyPointer + 1;
            setText(inputHistory.get(historyPointer));
        } else if (historyPointer == inputHistory.size() - 1) {
            historyPointer = historyPointer + 1;
            setText(currentInput);
        }
    }

    /**
     * Sets {@code inputTextField} with {@code text} and
     * positions the caret to the end of the {@code text}.
     *
     * @param text Text to be set in the input text field of the command window.
     */
    private void setText(String text) {
        inputTextField.setText(text);
        inputTextField.positionCaret(inputTextField.getText().length());
    }

    /**
     * Handles key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case PAGE_UP:
            keyEvent.consume();
            navigateToPreviousInput();
            break;
        case PAGE_DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        default:
            break;
        }
    }

    protected void storeInput(String input) {
        if (historyPointer != inputHistory.size() - 1 || (historyPointer == inputHistory.size() - 1
                && !input.equals(inputHistory.get(historyPointer)))) {
            inputHistory.add(input);
        }

        historyPointer = inputHistory.size();
        currentInput = "";
    }
}
