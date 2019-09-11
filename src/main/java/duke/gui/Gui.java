package duke.gui;

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

    private DukeGui dukeGui;

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();

        if (!input.isEmpty()) {
            dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

            dukeGui.respond(input);
            userInput.clear();
        }
    }

    public void setDuke(DukeGui dukeGui) {
        this.dukeGui = dukeGui;
    }

    public void showWelcomeMessage() {
        String welcome = Message.MESSAGE_WELCOME_GREET + "\n" + Message.MESSAGE_WELCOME_QUESTION;
        showMessage(welcome);
    }

    public void showExitMessage() {
        showMessage(Message.MESSAGE_EXIT);
    }

    public void showMessage(String message) {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(message, dukeImage));
    }

    public void showLoadingError(String errorMessage) {
        showMessage(errorMessage);
    }

    public void showError(String errorMessage) {
        showMessage(errorMessage);
    }

    public void exit() {
        userInput.setDisable(true);
        sendButton.setDisable(true);
    }
}
