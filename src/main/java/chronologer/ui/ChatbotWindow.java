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
//import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * UI component that allows the user to interact with Chronologer like a chatbot.
 * Its mainly acts on the user input and gives the user appopriate feedback.
 */
class ChatbotWindow extends InputWindow {

    private static final String CHRONOLOGER_WELCOME_MESSAGE = "Hello! I'm Chronologer, your task manager!";
    private static final String FXML = "ChatbotWindow.fxml";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogBoxContainer;
    @FXML
    private Button sendButton;

    private Parser parser;
    private Command command;
    private TaskList tasks;
    private Storage storage;

    /**
     * Constructs the chatbot window of the application.
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
        printWelcome();
    }

    @FXML
    protected void handleAction() {
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

}