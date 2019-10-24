package moomoo.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import moomoo.MooMoo;

/**
 * Represents the main window holding the different elements of the GUI.
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

    private MooMoo moomoo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setMoomoo(MooMoo newMooMoo) {
        moomoo = newMooMoo;
    }

    @FXML
    /**
     * Handles input given by the user and creates a DialogBox for every value given by user or response given by Duke.
     */
    private void handleUserInput() throws InterruptedException {
        String input = userInput.getText();
        String response = moomoo.getResponse(input);
        String[] splitArray = response.split("\n");
        String newResponse = "";
        for (String element : splitArray) {
            if (element.length() > 80) {
                for (int i = 0; i < element.length(); ++i) {
                    if (i > 0 && i % 80 == 0) {
                        newResponse += "\n";
                    }
                    newResponse += element.charAt(i);
                }
            } else {
                newResponse += element;
            }
            newResponse += "\n";
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(newResponse, dukeImage)
        );
        userInput.clear();

        if ("bye".equals(input)) {
            Thread exitThread = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            };
            exitThread.start();
        }
    }
}