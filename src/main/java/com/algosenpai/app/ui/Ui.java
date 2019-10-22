package com.algosenpai.app.ui;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.ui.controller.AnimationTimerController;
import com.algosenpai.app.ui.components.DialogBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class Ui extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Logic logic;

    int idleMaxMinutes = 180;

    AnimationTimerController animationTimerController;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/unknown.png"));
    private Image senpaiImage = new Image(this.getClass().getResourceAsStream("/images/miku.png"));


    /**
     * Renders the nodes on the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog(
                "Welcome to AlgoSenpai Adventures! Type 'hello' to start!", senpaiImage));
        handle();
    }

    public void setLogic(Logic l) {
        logic = l;
    }

    /**
     * Handles the input of the user and prints the output of the program
     * onto the GUI.
     */
    @FXML
    private void handleUserInput() {
        resetIdle();
        String input = userInput.getText();
        Command currCommand = logic.parseInputCommand(input);
        String response = logic.executeCommand(currCommand);
        if (response.equals("undo")) {
            undoChat();
        } else if (response.equals("clear")) {
            clearChat();
        } else {
            printUserText(input, userImage);
            printSenpaiText(response, senpaiImage);
        }
        if (input.equals("exit")) {
            exit();
        }
    }

    private void resetIdle() {
        idleMaxMinutes = 180;
    }

    /**
     * Creates the dialog box on GUI to show the user what he/she has typed.
     * Clears the user input after processing.
     * @param text the String that user has typed in
     * @param image the profile picture of the user.
     */
    private void printSenpaiText(String text, Image image) {
        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog(text, image));
        userInput.clear();
    }

    /**
     * Creates the dialog box on GUI to show the response of the Senpai.
     * @param text the response of the program.
     * @param image the profile picture of the Senpai.
     */
    private void printUserText(String text, Image image) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(text, image));
    }

    /**
     * Delete chat messages.
     */
    private void undoChat() {
        if (dialogContainer.getChildren().size() > 1) {
            int messageIndex = dialogContainer.getChildren().size() - 2;
            dialogContainer.getChildren().remove(messageIndex, messageIndex + 2);
        }
        userInput.clear();
    }

    /**
     * Clear chat.
     */
    private void clearChat() {
        int messageIndex = dialogContainer.getChildren().size();
        dialogContainer.getChildren().remove(0, messageIndex);
        userInput.clear();
    }

    /**
     * Closes the application.
     */
    private void exit() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            Platform.exit();
        });
        pause.play();
    }

    private void handle() {
        animationTimerController = new AnimationTimerController(1000) {
            @Override
            public void handle() {
                if (idleMaxMinutes > 0) {
                    idleMaxMinutes--;
                } else {
                    dialogContainer.getChildren()
                            .add(DialogBox.getSenpaiDialog("Hello do you need help?", senpaiImage));
                }
            }
        };
        animationTimerController.start();
    }
}
