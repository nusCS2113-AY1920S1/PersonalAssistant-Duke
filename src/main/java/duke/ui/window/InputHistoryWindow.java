package duke.ui.window;

import duke.exception.DukeException;
import duke.ui.commons.UiElement;
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

/* @@author gowgos5 */
public abstract class InputHistoryWindow extends UiElement<Region> {
    @FXML
    protected TextArea inputTextField;

    private List<String> inputHistory;
    private int historyPointer;
    private String currentInput = "";
    private File historyFile;

    /**
     * Constructs the command window of the application.
     */
    public InputHistoryWindow(String fxmlFileName, Region root) {
        super(fxmlFileName, root);

        // listen for updates to text field, and save partial input to currentInput if not viewing history
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (historyPointer == inputHistory.size()) {
                currentInput = newValue;
            }
        });

        inputTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
            case UP:
                event.consume();
                this.navigateToPreviousInput();
                break;
            case DOWN:
                event.consume();
                this.navigateToNextInput();
                break;
            case ENTER:
                event.consume();
                this.handleAction();
                break;
            default:
                break;
            }
        });

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

    /**
     * Updates the text field with the previous input in {@code inputHistory},
     * if there exists a previous input in {@code inputHistory}.
     */
    private void navigateToPreviousInput() {
        if (historyPointer > 0) {
            --historyPointer;
            setText(inputHistory.get(historyPointer));
        }
    }

    /**
     * Updates the text field with the next input in {@code inputHistory},
     * if there exists a next input in {@code inputHistory}.
     */
    private void navigateToNextInput() {
        if (historyPointer < inputHistory.size() - 1) {
            ++historyPointer;
            setText(inputHistory.get(historyPointer));
        } else if (historyPointer == inputHistory.size() - 1) {
            ++historyPointer;
            setText(currentInput);
        } //ignore if already viewing current input
    }

    /**
     * Sets {@code inputTextField} with {@code text} and positions the caret to the end of the {@code text}.
     *
     * @param text Text to be set in the input text field of the command window.
     */
    private void setText(String text) {
        inputTextField.setText(text);
        inputTextField.positionCaret(inputTextField.getText().length());
    }

    /* @@author aquohn */
    protected void storeInput(String input) {
        if (historyPointer != inputHistory.size() - 1 || (historyPointer == inputHistory.size() - 1
                && !input.equals(inputHistory.get(historyPointer)))) {
            inputHistory.add(input);
        }

        historyPointer = inputHistory.size();
        currentInput = "";
    }

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
            throw new DukeException("Unable to write command history! Some data may have been lost,");
        }
    }

    /**
     * Handles the event where the user presses "Enter" after he/she has finished
     * typing the command in {@code inputTextField}.
     */
    protected abstract void handleAction();
}
