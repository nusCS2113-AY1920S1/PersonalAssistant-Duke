package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for ui.MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private DukeUI duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/lucria.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/lucria.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(DukeUI d) {
        duke = d;
        dukeGreeting();
    }

    private void dukeGreeting() {
        String greetingText = "Hello! I'm Duke\n"
                + "What can i do for you";
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(greetingText, dukeImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing DukeTest's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.contains("bye")) {
            duke.getResponse("Bye. Hope to see you again soon!");
            timeDelay(1000);
            System.exit(0);
        } else {
            String response = duke.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            userInput.clear();
        }
    }

    private void timeDelay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {}
    }
}