package duke.gui;

import duke.command.Command;
import duke.command.Parser;
import duke.exception.DukeException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Gui extends AnchorPane {
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

    private Parser parser;
    private String inputStr;

    public Gui() {
        parser = new Parser();
    }

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    @FXML
    private void handleUserInput() {
        inputStr = userInput.getText().trim();

        if (!inputStr.isEmpty()) {
            dialogContainer.getChildren().add(MessageBox.getUserDialog(inputStr, userImage));

            userInput.clear();
        }
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
        // String inputStr = userInput.getText().trim();
        inputStr = inputStr.replaceAll("\t", "    "); //sanitise input
        return parser.parse(inputStr);
    }

    /**
     * Prints a message.
     *
     * @param msg Message to be printed.
     */
    public void print(String msg) {
        dialogContainer.getChildren().add(MessageBox.getDukeDialog(msg, dukeImage));
    }

    /**
     * Prints hello message to indicate that setup is completed and Duke can now receive user input.
     */
    public void printHello() {
        String welcome = UiMessage.MESSAGE_WELCOME_GREET + "\n" + UiMessage.MESSAGE_WELCOME_QUESTION;
        print(welcome);
    }

    /**
     * Prints the error message from an exception.
     *
     * @param excp Exception whose message we want to print.
     */
    public void printError(DukeException excp) {
        print(excp.getMessage());
    }

    /**
     * Disable UI inputs and print a goodbye message. UI should not be used anymore after calling this function.
     */
    public void closeUi() {
        userInput.setDisable(true);
        sendButton.setDisable(true);
        print(UiMessage.MESSAGE_EXIT);
    }
}
