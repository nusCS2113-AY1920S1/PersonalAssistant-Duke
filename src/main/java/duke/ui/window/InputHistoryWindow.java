package duke.ui.window;

import duke.exception.DukeException;
import duke.ui.commons.UiElement;
import duke.ui.commons.UiStrings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//@@author gowgos5
/**
 * UI window that tracks and saves the input history of the user.
 */
public abstract class InputHistoryWindow extends UiElement<Region> {
    @FXML
    protected TextArea inputTextField;

    private File historyFile;
    private List<String> inputHistory;
    private int historyPointer;
    private String currentInput = "";

    /**
     * Constructs the input history window of the application.
     */
    public InputHistoryWindow(String fxmlFileName, Region root) {
        super(fxmlFileName, root);

        attachKeyListenerToTextField();
        readFromHistoryFile();

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
     * Sets {@code inputTextField} with {@code text} and positions the caret to the end of the {@code text}.
     *
     * @param text Text to be set in the {@code inputTextField}.
     */
    private void setText(String text) {
        inputTextField.setText(text);
        inputTextField.positionCaret(inputTextField.getText().length());
    }

    /**
     * Attaches a listener to the {@code inputTextField} to listen for key presses.
     */
    private void attachKeyListenerToTextField() {
        inputTextField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            switch (event.getCode()) {
            case PAGE_UP:
                event.consume();
                this.navigateToPreviousInput();
                break;
            case PAGE_DOWN:
                event.consume();
                this.navigateToNextInput();
                break;
            case ENTER:
                event.consume();

                if (event.isShiftDown()) {
                    inputTextField.appendText(System.lineSeparator());
                } else {
                    this.handleAction();
                }

                break;
            default:
                break;
            }
        });
    }

    /**
     * Attaches a listener to the {@code inputTextField} to listen for text changes.
     */
    public void attachTextListenerToTextField(ChangeListener<String> listener) {
        inputTextField.textProperty().addListener(listener);
    }

    /**
     * Handles the event where the user presses "Enter" after he/she has finished
     * typing his/her command in the {@code inputTextField}.
     */
    protected abstract void handleAction();

    //@@author aquohn
    protected void storeInput(String input) {
        if (historyPointer != inputHistory.size() - 1 || (historyPointer == inputHistory.size() - 1
                && !input.equals(inputHistory.get(historyPointer)))) {
            inputHistory.add(input);
        }

        historyPointer = inputHistory.size();
        currentInput = "";
    }

    //@@author aquohn
    protected void writeHistory() throws DukeException {
        try {
            FileWriter cmdFileWr = new FileWriter(historyFile);
            StringBuilder cmdStrBuilder = new StringBuilder();
            for (String input : inputHistory) {
                cmdStrBuilder.append(input).append(System.lineSeparator());
            }
            cmdFileWr.write(cmdStrBuilder.toString());
            cmdFileWr.close();
        } catch (IOException e) {
            throw new DukeException(UiStrings.MESSAGE_ERROR_WRITE_COMMAND_HISTORY);
        }
    }

    //@@author aquohn
    private void readFromHistoryFile() {
        historyFile = new File("data/history.txt");
        try {
            Scanner commandScanner = new Scanner(historyFile);
            inputHistory = new ArrayList<>();
            while (commandScanner.hasNextLine()) {
                inputHistory.add(commandScanner.nextLine());
            }
            historyPointer = inputHistory.size();
        } catch (FileNotFoundException excp) {
            inputHistory = new ArrayList<>();
            historyPointer = 0;
        }
    }
}
