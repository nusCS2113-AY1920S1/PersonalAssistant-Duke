package chronologer.ui;

import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.parser.Parser;
import chronologer.parser.ParserFactory;
import chronologer.storage.Storage;
import chronologer.task.TaskList;

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
 * UI component that allows the user to interact with Chronologer like a chatbot.
 * Its mainly acts on the user input and gives the user appopriate feedback.
 */
class ChatbotWindow extends UiComponent<Region> {

    private static final String CHRONOLOGER_WELCOME_MESSAGE = "Hello! I'm Chronologer, your task manager!";
    private static final String FXML = "ChatbotWindow.fxml";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogBoxContainer;
    @FXML
    private Button sendButton;
    @FXML
    private TextField inputTextField;

    private Parser parser;
    private Command command;
    private TaskList tasks;
    private Storage storage;

    private List<String> userInputHistory = new ArrayList<>();
    private int userInputHistoryPointer = 0;
    private String currentInput = "default";

    /**
     * Constructs the chat-bot window of the application.
     *
     * @param command Holds the Command object responsible for executing user commands.
     * @param parser Holds the Parser object which is responsible for parsing user input.
     */
    ChatbotWindow(Command command, Parser parser, TaskList tasks, Storage storage) {
        super(FXML, null);
        this.command = command;
        this.parser = parser;
        this.tasks = tasks;
        this.storage = storage;
        scrollPane.vvalueProperty().bind(dialogBoxContainer.heightProperty());

        attachInputListeners();
        printWelcome();
    }

    @FXML
    private void handleUserInput() {
        String input = inputTextField.getText();
        storeUserInputHistory(input);
        try {
            Command command = ParserFactory.parse(input);
            command.execute(tasks, storage);
        } catch (ChronologerException e) {
            System.out.println(e.getMessage());
        }
        DialogBox toChangeDimension = DialogBox.getUserDialog(" " + input);
        dialogBoxContainer.getChildren().addAll(toChangeDimension.getRoot(),
            DialogBox.getChronologerDialog(UiTemporary.userOutputForUI).getRoot());
        inputTextField.clear();
    }

    /**
     * Prints message.
     *
     * @param message Message.
     */
    private void print(String message) {
        dialogBoxContainer.getChildren().add(DialogBox.getChronologerDialog(message).getRoot());
    }

    /**
     * Prints chronologer's welcome message.
     */
    private void printWelcome() {
        print(CHRONOLOGER_WELCOME_MESSAGE);
    }

    private void setText(String text) {
        inputTextField.setText(text);
        inputTextField.positionCaret(inputTextField.getText().length());
    }

    /**
     * Handles the key press event which simulates command line.
     */
    private void attachInputListeners() {
        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (userInputHistoryPointer == userInputHistory.size()) {
                currentInput = newValue;
            }
        });

        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
            case ENTER:
                keyEvent.consume();
                handleUserInput();
                break;
            case UP:
                keyEvent.consume();
                if (userInputHistoryPointer >= 1) {
                    userInputHistoryPointer -= 1;
                    setText(userInputHistory.get(userInputHistoryPointer));
                }
                break;
            case DOWN:
                keyEvent.consume();
                if (userInputHistoryPointer < userInputHistory.size() - 1) {
                    userInputHistoryPointer += 1;
                    setText(userInputHistory.get(userInputHistoryPointer));
                } else if (userInputHistoryPointer == userInputHistory.size() - 1) {
                    userInputHistoryPointer += 1;
                    setText(currentInput);
                }
                break;
            default:
                break;
            }
        });
    }

    private void storeUserInputHistory(String input) {
        if (userInputHistoryPointer != userInputHistory.size() - 1
            || (userInputHistoryPointer == userInputHistory.size() - 1
            && !input.equals(userInputHistory.get(userInputHistoryPointer)))) {
            userInputHistory.add(input);
        }
        userInputHistoryPointer = userInputHistory.size();
        currentInput = null;
    }

}