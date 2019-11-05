package com.algosenpai.app.ui;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.ByeCommand;
import com.algosenpai.app.logic.command.ClearCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.SetupCommand;
import com.algosenpai.app.logic.command.UndoCommand;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.logic.parser.Parser;
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

import java.io.IOException;

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
    private ProgressBar levelProgress;

    @FXML
    private Label playerName;

    @FXML
    private Label playerLevel;

    @FXML
    private ImageView userPic;

    @FXML
    private Button sendButton;

    private Logic logic;
    private UserStats stats;
    private int maxuserExp = 8;
    private int userExp = 0;
    private int idleMinutesMax = 180;
    private int userLevel = 1;
    private static final String GREETING_MESSAGE = "Welcome to AlgoSenpai Adventures!"
                                                   + " Type 'hello' followed by your name and gender"
                                                   + " (boy/girl) to start!\n \n"
                                                   + "If you have been here before,"
                                                   + " type 'hello' to load your previous user data!";
    private static final String BOY_PROFILE_PICTURE_PATH = "/images/boychar.jpg";
    private static final String GIRL_PROFILE_PICTURE_PATH = "/images/girlchar.jpg";
    private static final String DEFAULT_PROFILE_PICTURE_PATH = "/images/unknown.png";
    private static final String SENPAI_PROFILE_PICTURE_PATH = "/images/algosenpai.png";

    private Image boyImage = new Image(this.getClass().getResourceAsStream(BOY_PROFILE_PICTURE_PATH));
    private Image girlImage = new Image(this.getClass().getResourceAsStream(GIRL_PROFILE_PICTURE_PATH));
    private Image userImage = new Image(this.getClass().getResourceAsStream(DEFAULT_PROFILE_PICTURE_PATH));
    private Image senpaiImage = new Image(this.getClass().getResourceAsStream(SENPAI_PROFILE_PICTURE_PATH));

    /**
     * Renders the nodes on the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog(GREETING_MESSAGE, senpaiImage));
        handle();
        userPic.setImage(userImage);
        userInput.setPromptText("Enter a command (Enter \"menu\" to see a list of commands");
        levelProgress.setProgress(0);
        playerLevel.setText("You are Level 1");
        handle();
    }

    public void setLogic(Logic logic, UserStats stats) {
        this.logic = logic;
        this.stats = stats;
    }

    /**
     * Handles user inputs and renders outputs on screen.
     */
    @FXML
    private void handleUserInput() throws IOException {
        resetIdle();
        String input = userInput.getText();
        Command commandGenerated = logic.executeCommand(input);
        String response = commandGenerated.execute();

        if (commandGenerated instanceof UndoCommand) {
            undoChat(response, input, response);
        } else if (commandGenerated instanceof ClearCommand) {
            clearChat();
        } else if (commandGenerated instanceof ByeCommand) {
            exit();
        } else if (commandGenerated instanceof SetupCommand) {
            setPlayerGender(response);
            playerName.setText(response);
            printToGui(input, response, userImage, senpaiImage);
        } else if (response.startsWith("You got ")) {
            String[] resp = response.split(" ");
            int expGain = Integer.parseInt(resp[7]);
            updateLevelProgress(expGain);
            printToGui(input, response, userImage, senpaiImage);
        } else {
            printToGui(input, response, userImage, senpaiImage);
        }
    }

    /**
     * Creates the dialog box on GUI to show the response of the Senpai.
     * @param text the response of the program.
     * @param image the profile picture of the Senpai.
     */
    private void printSenpaiText(String text, Image image) {
        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog(text, image));
        userInput.clear();
    }

    /**
     * Creates the dialog box on GUI to show the user what he/she has typed.
     * Clears the user input after processing.
     * @param text the String that user has typed in
     * @param image the profile picture of the user.
     */
    private void printUserText(String text, Image image) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(text, image));
    }

    /**
     * Prints the chat bubbles to the GUI.
     * @param input the user input text.
     * @param response the text generated by the program
     * @param userImage the profile picture of the user
     * @param senpaiImage the profile picture of the Senpai.
     */
    private void printToGui(String input, String response, Image userImage, Image senpaiImage) {
        printUserText(input, userImage);
        printSenpaiText(response, senpaiImage);
    }

    /**
     * Resets the idleMinutesMax variable.
     */
    private void resetIdle() {
        idleMinutesMax = 180;
    }

    /**
     * Delete chat messages.
     * @param numberStr number of chat messages to delete.
     * @param input user input.
     * @param response text generated by program.
     */
    private void undoChat(String numberStr, String input, String response) {
        if (Parser.isInteger(numberStr)) {
            int number = Integer.parseInt(numberStr);
            while (dialogContainer.getChildren().size() > 0 && number > 0) {
                int lastIndex = dialogContainer.getChildren().size() - 1;
                dialogContainer.getChildren().remove(lastIndex, lastIndex + 1);
                number--;
            }
        } else {
            printToGui(input, response, userImage, senpaiImage);
        }
        userInput.clear();
    }

    /**
     * Delete all chats.
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
        AnimationTimerController animationTimerController = new AnimationTimerController(1000) {
            @Override
            public void handle() {
                final String HELP_MESSAGE = "Hello do you need help?";
                if (idleMinutesMax > 0) {
                    idleMinutesMax--;
                } else {
                    idleMinutesMax = 180;
                    printSenpaiText(HELP_MESSAGE, senpaiImage);
                }
            }
        };
        animationTimerController.start();
    }

    /**
     * Update the EXP Level of the user in the progress bar in the GUI.
     * @param expGain the double representing the gain in EXP to be reflected.
     */
    private void updateLevelProgress(int expGain) {
        userExp += expGain;
        if (userExp > maxuserExp) {
            userExp -= maxuserExp;
            userLevel++;
            playerLevel.setText("You are Level " + userLevel + ".");
            maxuserExp *= 2;
        }
        double userProgress = (double) userExp / maxuserExp;

        levelProgress.setProgress(userProgress);
    }

    /**
     * Updates the profile picture of the user in the stats bar in the GUI.
     * @param response the response string generated by the program.
     */
    private void setPlayerGender(String response) {
        if (response.substring(6, 9).equals("Mr.")) {
            userImage = boyImage;
            userPic.setImage(userImage);
        } else if (response.substring(6, 9).equals("Ms.")) {
            userImage = girlImage;
            userPic.setImage(userImage);
        }
    }
}