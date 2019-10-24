package com.algosenpai.app.ui;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.ui.controller.AnimationTimerController;
import com.algosenpai.app.ui.components.DialogBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private ProgressBar levelProgress;
    @FXML
    private Label playerName;
    @FXML
    private Label playerLevel;
    @FXML
    private ImageView userPic;

    private Logic logic;
    private double playerExp = 0.0;


    private Image boyImage = new Image(this.getClass().getResourceAsStream("/images/boyplayer.jpg"));
    private Image girlImage = new Image(this.getClass().getResourceAsStream("/images/girlplayer.png"));
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/unknown.png"));
    private Image senpaiImage = new Image(this.getClass().getResourceAsStream("/images/miku.png"));

    int idleMaxMinutes = 180;

    AnimationTimerController animationTimerController;


    /**
     * Renders the nodes on the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog(
                "Welcome to AlgoSenpai Adventures! Type 'hello' to start!", senpaiImage));
        userPic.setImage(userImage);
        levelProgress.setProgress(playerExp);
        handle();
    }

    public void setLogic(Logic l) {
        logic = l;
    }

    /**
     * Changes the user Image.
     * @param input the input of the user which will either be "boy" or "girl".
     */
    public void changeUserImage(String input) {
        if (input.equals("boy")) {
            userImage = boyImage;
            userPic.setImage(userImage);
        } else if (input.equals("girl")) {
            userImage = girlImage;
            userPic.setImage(userImage);
        }
    }

    /**
     * Handles the input of the user and prints the output of the program
     * onto the GUI.
     */
    @FXML
    private void handleUserInput() {
        resetIdle();
        String input = userInput.getText();
        Command command = logic.executeCommand(input);
        String response = command.execute();
        if (input.equals("undo")) {
            undoChat();
        } else if (input.equals("clear")) {
            clearChat();
        } else if (input.equals("exit")) {
            exit();
        } else if (response.equals("You're all set! Time to start your journey to become an AlgoSenpai!")) {
            playerLevel.setText("You are Level 1");
            changeUserImage(input);
            printtoGui(input, response, userImage, senpaiImage);
        } else if (response.equals("Are you a boy or a girl?")) {
            playerName.setText("Hi, " + input + "!");
            printtoGui(input, response, userImage, senpaiImage);
        } else if (response.startsWith("You got ")) {
            updateLevelProgress(0.1);
            printtoGui(input, response, userImage, senpaiImage);
        } else {
            printtoGui(input, response, userImage, senpaiImage);
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
                    idleMaxMinutes = 180;
                    dialogContainer.getChildren()
                            .add(DialogBox.getSenpaiDialog("Hello do you need help?", senpaiImage));
                }
            }
        };
        animationTimerController.start();
    }

    /**
     * Prints the chat bubbles to the GUI.
     * @param input the user input text.
     * @param response the text generated by the program
     * @param userImage the profile picture of the user
     * @param senpaiImage the profile picture of the Senpai.
     */
    private void printtoGui(String input, String response, Image userImage, Image senpaiImage) {
        printUserText(input, userImage);
        printSenpaiText(response, senpaiImage);
    }

    /**
     * Update the EXP Level of the user in the progress bar in the GUI.
     * @param expGain the double representing the gain in EXP to be reflected.
     */
    private void updateLevelProgress(double expGain) {
        playerExp += expGain;
        levelProgress.setProgress(playerExp);
    }
}
