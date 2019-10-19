package duke.gui;

import duke.DukeCore;
import duke.command.Command;
import duke.command.Parser;
import duke.exception.DukeException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for reading and display commands.
 */
public class CommandWindow extends UiElement<Region> {
    private static final String FXML = "CommandWindow.fxml";

    private Parser parser;
    private String input;
    private DukeCore core;

    private List<String> inputHistory;
    private int historyPointer;
    private String currentInput;
    private MainWindow window;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Construct a CommandWindow object.
     *
     * @param core DukeCore.
     */
    public CommandWindow(DukeCore core, MainWindow window) {
        super(FXML, null);
        parser = new Parser();
        this.core = core;
        this.window = window;

        inputHistory = new ArrayList<>();
        historyPointer = 0;

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (historyPointer == inputHistory.size()) {
                currentInput = newValue;
            }
        });

        printHello();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            keyEvent.consume();
            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        default:
            break;
        }
    }

    /**
     * Handles the user input event.
     */
    @FXML
    private void handleUserInput() {
        input = userInput.getText().trim();

        if (!input.isEmpty()) {
            if (historyPointer != inputHistory.size() - 1 || (historyPointer == inputHistory.size() - 1
                    && !input.equals(inputHistory.get(historyPointer)))) {
                inputHistory.add(input);
            }

            historyPointer = inputHistory.size();
            currentInput = "";

            dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

            if (input.equals("home") || input.equals("patient")) {
                window.tmp(input);
            } else {
                try {
                    Command c = parseCommand();
                    c.execute(core);
                } catch (DukeException e) {
                    printError(e);
                }
            }

            userInput.clear();
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}.
     */
    private void navigateToPreviousInput() {
        if (historyPointer > 0) {
            historyPointer = historyPointer - 1;
            replaceText(inputHistory.get(historyPointer));
        }
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}.
     */
    private void navigateToNextInput() {
        if (historyPointer < inputHistory.size() - 1) {
            historyPointer = historyPointer + 1;
            replaceText(inputHistory.get(historyPointer));
        } else if (historyPointer == inputHistory.size() - 1) {
            historyPointer = historyPointer + 1;
            replaceText(currentInput);
        }
    }

    /**
     * Sets {@code CommandWindow}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        userInput.setText(text);
        userInput.positionCaret(userInput.getText().length());
    }

    /**
     * Use the Parser to extract the requested command, which will be loaded with parameters
     * extracted from the user's arguments.
     *
     * @return The command specified by the user, with arguments parsed.
     * @throws DukeException If Parser fails to find a matching command or the arguments do not meet the command's
     *                       requirements.
     */
    public Command parseCommand() throws DukeException {
        input = input.replaceAll("\t", "    ");
        return parser.parse(input);
    }

    /**
     * Prints a message.
     *
     * @param msg Message to be printed.
     */
    public void print(String msg) {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(msg, dukeImage));
    }

    /**
     * Prints Hello message.
     */
    public void printHello() {
        String welcome = Message.MESSAGE_WELCOME_GREET + "\n" + Message.MESSAGE_WELCOME_QUESTION;
        print(welcome);
    }

    /**
     * Prints the error message from an exception.
     *
     * @param e Exception whose message we want to print.
     */
    public void printError(DukeException e) {
        print(e.getMessage());
    }
}
