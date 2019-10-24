package duke.ui;

import duke.command.Command;
import duke.command.Executor;
import duke.command.Parser;
import duke.exception.DukeException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * UI element designed for the user to interact with the application.
 * It has 3 main tasks.
 * 1. Displays and reads user's input.
 * 2. Parses VALID user's input into a defined command and displays the corresponding result.
 * 3. Displays the appropriate error message for INVALID user's input.
 */
class CommandWindow extends UiElement<Region> {
    private static final String FXML = "CommandWindow.fxml";
    private static final String MESSAGE_WELCOME_GREET = "Hello! I'm Dr. Duke.";
    private static final String MESSAGE_WELCOME_QUESTION = "What can I do for you today?";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox messageContainer;
    @FXML
    private AutoCompleteTextField inputTextField;
    @FXML
    private Button sendButton;

    private Parser parser;
    private Executor executor;

    // TODO: A separate (inner) class for input history
    private List<String> inputHistory;
    private int historyPointer;
    private String currentInput;

    /**
     * Constructs the command window of the application.
     *
     * @param executor Executor object responsible for executing user commands.
     */
    CommandWindow(Executor executor, Parser parser) {
        super(FXML, null);

        this.parser = parser;
        this.executor = executor;

        inputHistory = new ArrayList<>();
        historyPointer = 0;

        scrollPane.vvalueProperty().bind(messageContainer.heightProperty());
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (historyPointer == inputHistory.size()) {
                currentInput = newValue;
            }
        });

        printWelcome();
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

    /**
     * Handles the event where the user clicks on {@code sendButton} or the user presses 'Enter" after he/she
     * has finished typing the command in {@code inputTextField}.
     */
    @FXML
    private void handleAction() {
        String input = inputTextField.getText().trim();
        input = input.replaceAll("\t", "    ");

        if (!input.isEmpty()) {
            if (historyPointer != inputHistory.size() - 1 || (historyPointer == inputHistory.size() - 1
                    && !input.equals(inputHistory.get(historyPointer)))) {
                inputHistory.add(input);
            }

            historyPointer = inputHistory.size();
            currentInput = "";

            messageContainer.getChildren().add(MessageBox.getUserMessage(input).getRoot());

            try {
                executor.execute(parseCommand(input));
            } catch (DukeException e) {
                printError(e);
            }

            inputTextField.clear();
        }
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
     * Uses the Parser to retrieve the requested command, which will be loaded with parameters
     * extracted from the user's input arguments.
     *
     * @param input Input string to be parsed.
     * @return The command specified by the user, with arguments parsed.
     * @throws DukeException If the parser fails to find a matching command or the arguments do not meet the command's
     *                       requirements.
     */
    private Command parseCommand(String input) throws DukeException {
        return parser.parse(input);
    }

    /**
     * Prints message.
     *
     * @param message Message.
     */
    void print(String message) {
        messageContainer.getChildren().add(MessageBox.getDukeMessage(message).getRoot());
    }

    /**
     * Prints welcome message.
     */
    private void printWelcome() {
        String welcome = MESSAGE_WELCOME_GREET + System.lineSeparator() + MESSAGE_WELCOME_QUESTION;
        print(welcome);
    }

    /**
     * Prints error message from an exception.
     *
     * @param e Exception.
     */
    private void printError(DukeException e) {
        print(e.getMessage());
    }
}
