package duke.ui.window;

import duke.DukeCore;
import duke.command.Command;
import duke.command.Executor;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.ui.commons.UiElement;
import duke.ui.commons.UiStrings;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * UI element designed for the user to interact with the application.
 * It has 3 main tasks.
 * 1. Displays and reads user's input.
 * 2. Use the Parser to parse VALID user's input into a defined command and displays the corresponding result.
 * 3. Displays the appropriate error message for INVALID user's input.
 */
public class CommandWindow extends InputHistoryWindow {
    private static final String FXML = "CommandWindow.fxml";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox messageContainer;

    private Parser parser;
    private Executor executor;

    /**
     * Constructs the command window of the application.
     *
     * @param parser   Parser object responsible for parsing user commands.
     * @param executor Executor object responsible for executing user commands.
     */
    public CommandWindow(Parser parser, Executor executor) {
        super(FXML, null);

        this.parser = parser;
        this.executor = executor;

        scrollPane.vvalueProperty().bind(messageContainer.heightProperty());
        inputTextField.requestFocus();

        printWelcome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleAction() {
        // TODO: Format block
        String inputMessage = inputTextField.getText().strip();

        if (inputMessage.isEmpty()) {
            return;
        }

        storeInput(inputMessage);

        try {
            writeHistory();
        } catch (DukeException e) {
            printError(e);
        }

        MessageBox userMessageBox = MessageBox.getUserMessage(inputMessage);
        messageContainer.getChildren().add(userMessageBox.getRoot());

        try {
            Command command = parser.parse(inputMessage);
            executor.execute(command);
            inputTextField.clear();
        } catch (DukeException e) {
            printError(e);
        }
    }

    /**
     * Prints message in the {@code messageContainer}.
     *
     * @param message Output message.
     */
    public void print(String message) {
        MessageBox dukeMessageBox = MessageBox.getDukeMessage(message);
        messageContainer.getChildren().add(dukeMessageBox.getRoot());
    }

    /**
     * Prints welcome message.
     */
    private void printWelcome() {
        String welcome = UiStrings.MESSAGE_WELCOME_GREET + System.lineSeparator() + UiStrings.MESSAGE_WELCOME_QUESTION;
        print(welcome);
    }

    /**
     * Prints error message.
     *
     * @param e Error.
     */
    private void printError(DukeException e) {
        print(e.getMessage());
    }

    public TextArea getInputTextField() {
        return inputTextField;
    }

    private static class MessageBox extends UiElement<Region> {
        private static final String FXML = "MessageBox.fxml";
        private static final Image userAvatar = new Image(DukeCore.class.getResourceAsStream("/images/user.png"));
        private static final Image dukeAvatar = new Image(DukeCore.class.getResourceAsStream("/images/duke.png"));

        @FXML
        private Circle avatar;
        @FXML
        private VBox messageHolder;
        @FXML
        private Text message;

        /**
         * Constructs a new MessageBox object to be displayed in the command window.
         */
        private MessageBox(String text, Image image) {
            super(FXML, null);

            message.setText(text);
            message.wrappingWidthProperty().bind(messageHolder.prefWidthProperty());
            avatar.setFill(new ImagePattern(image));
        }

        /**
         * Creates a message box for the user's input.
         */
        static MessageBox getUserMessage(String text) {
            return new MessageBox(text, userAvatar);
        }

        /**
         * Creates a message box for Dr. Duke's response.
         */
        static MessageBox getDukeMessage(String text) {
            return new MessageBox(text, dukeAvatar);
        }
    }
}
