package com.algosenpai.app.ui;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.critical.ByeCommand;
import com.algosenpai.app.logic.command.critical.ResetCommand;
import com.algosenpai.app.logic.command.utility.ClearCommand;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.utility.DeleteCommand;
import com.algosenpai.app.logic.command.utility.LoadCommand;
import com.algosenpai.app.logic.command.utility.SetupCommand;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.logic.parser.Parser;
import com.algosenpai.app.ui.controller.AnimationTimerController;
import com.algosenpai.app.ui.components.DialogBox;
import com.algosenpai.app.utility.AutoCompleteHelper;
import com.algosenpai.app.utility.LogCenter;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

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
    private int idleMinutesMax = 60;
    private int idleMinutes = 60;
    private int userLevel = 1;

    // A flag to prevent a key *held down* from being interpreted as multiple key Presses.
    // Once keyPressed is true, subsequent keypress events are ignored.
    private boolean keyPressed = false;

    private static final String BOY_PROFILE_PICTURE_PATH = "/images/boychar.jpg";
    private static final String GIRL_PROFILE_PICTURE_PATH = "/images/girlchar.jpg";
    private static final String DEFAULT_PROFILE_PICTURE_PATH = "/images/unknown.png";
    private static final String SENPAI_PROFILE_PICTURE_PATH = "/images/algosenpai.png";

    private Image boyImage = new Image(this.getClass().getResourceAsStream(BOY_PROFILE_PICTURE_PATH));
    private Image girlImage = new Image(this.getClass().getResourceAsStream(GIRL_PROFILE_PICTURE_PATH));
    private Image userImage = new Image(this.getClass().getResourceAsStream(DEFAULT_PROFILE_PICTURE_PATH));
    private Image senpaiImage = new Image(this.getClass().getResourceAsStream(SENPAI_PROFILE_PICTURE_PATH));

    private static final Logger logger = LogCenter.getLogger(Ui.class);

    /**
     * Renders the nodes on the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String response = new SetupCommand(new ArrayList<>()).execute();
        if (response.contains("Welcome back")) {
            new SetupCommand(new ArrayList<>());
            userLevel = SetupCommand.getLevel();
            maxuserExp = maxuserExp << (userLevel - 1);
            userExp = SetupCommand.getExpLevel();
            String gender = SetupCommand.getGender();
            updateLevelProgress(0);
            String username = "Username : " + SetupCommand.getUserName();
            setPlayerGender(gender);
            playerName.setText(username);
        }

        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog(response, senpaiImage));
        handle();
        userPic.setImage(userImage);

        userInput.setPromptText("Enter a command (Enter \"menu\" to see a list of commands");

        // Add a listener to monitor for Arrow keys and Tab.
        userInput.setOnKeyPressed(keyEvent -> {
            if (!keyPressed) {
                handleKeyPress(keyEvent.getCode());
                // Set flag to true to ignore any more keyPress events when that key is held down.
                keyPressed = true;
            }
        });
        // Reset the flag when the key is released.
        userInput.setOnKeyReleased(keyEvent -> keyPressed = false);
        handle();
    }

    /**
     * Set Logic.
     */
    public void setLogic(Logic logic, UserStats stats, boolean wasDatafileCorrupted) {
        this.logic = logic;
        this.stats = stats;
        // If the datafile was corrupted, notify the user that their data has been reset.
        if (wasDatafileCorrupted) {
            clearChat();
            printSenpaiText("Data file was corrupted! Data has been reset!", senpaiImage);
            printSenpaiText("\"Hello there! Welcome to the world of DATA STRUCTURES AND ALGORITHMS.\\n\"\n"
                      + "\"Can I have your name and gender in the format : 'hello NAME GENDER (boy/girl)' please.\";",
                    senpaiImage);
        }
    }

    /**
     * Handles user inputs and renders outputs on screen.
     */
    @FXML
    private void handleUserInput() throws IOException, FileParsingException {
        resetIdle();
        String input = userInput.getText();
        Command commandGenerated = logic.executeCommand(input);
        String response = commandGenerated.execute();

        if (commandGenerated instanceof DeleteCommand) {
            if (dialogContainer.getChildren().isEmpty()) {
                idleMinutes = 60;
                printSenpaiText("There are no more chats to delete!", senpaiImage);
                handleUndoAfterClear();
            } else {
                logger.info("Deleting selected chat bubbles on screen..");
                deleteChat(response, input, response);
            }
        } else if (commandGenerated instanceof ClearCommand) {
            logger.info("Clearing ALL chat bubbles on screen...");
            clearChat();
        } else if (commandGenerated instanceof ResetCommand || commandGenerated instanceof LoadCommand) {
            userLevel = stats.getUserLevel();
            maxuserExp = 8 << (userLevel - 1);
            userExp = stats.getUserExp();
            updateLevelProgress(0);
            playerName.setText("Username : " + stats.getUsername());
            printToGui(input, response, userImage, senpaiImage);
        } else if (commandGenerated instanceof ByeCommand) {
            printToGui(input, response, userImage, senpaiImage);
            exit();
        } else if (commandGenerated instanceof SetupCommand) {
            setupPlayer();
            printToGui(input, response, userImage, senpaiImage);
        } else if (response.startsWith("You got ")) {
            int expGain = Integer.parseInt(response.split(" ")[7]);
            updateLevelProgress(expGain);
            printToGui(input, response, userImage, senpaiImage);
        } else {
            printToGui(input, response, userImage, senpaiImage);
        }
    }

    /**
     * Sets up the user's information such as gender, level, exp.
     * Updates the level on the UI, as well as the username.
     */
    private void setupPlayer() {
        setPlayerGender(SetupCommand.getGender());
        userLevel = SetupCommand.getLevel();
        maxuserExp = 8 << (userLevel - 1);
        userExp = SetupCommand.getExpLevel();
        updateLevelProgress(0);
        playerName.setText("Username : " + SetupCommand.getUserName());
    }

    /**
     * Handles any keypresses when the userInput is in focus. Currently it responds to UP/DOWN to navigate through
     * history and TAB to auto complete words. Other keys are ignored.
     * @param k The Keycode of the key pressed.
     */
    @FXML
    private void handleKeyPress(KeyCode k) {
        // Get the previous and next commands from the historyList inside logic.
        if (k == KeyCode.UP) {
            userInput.setText(logic.getPreviousCommand());
            // Puts the cursor to the front of the text, overriding the default behaviour of arrow keys.
            userInput.positionCaret(userInput.getText().length());
        } else if (k == KeyCode.DOWN) {
            userInput.setText(logic.getNextCommand());
            userInput.positionCaret(userInput.getText().length());
        } else if (k == KeyCode.TAB) {
            // Replace text with autocomplete best match.
            // If no match is found, text is unchanged.
            userInput.setText(AutoCompleteHelper.autoCompleteCommand(userInput.getText()));
            // Bring focus back to textfield to prevent the default behaviour of tab.
            userInput.requestFocus();

            // Un select the text (selected by default).
            userInput.deselect();
            // Puts the cursor to the front of the text.
            userInput.positionCaret(userInput.getText().length());
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
        idleMinutesMax = 60;
    }

    /**
     * Delete chat messages.
     * @param numberStr number of chat messages to delete.
     * @param input user input.
     * @param response text generated by program.
     */
    private void deleteChat(String numberStr, String input, String response) {
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
        logger.info("==========================[ Closing AlgoSenpai Adventures ]========================");
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
                    idleMinutesMax = 60;
                    printSenpaiText(HELP_MESSAGE, senpaiImage);
                }
            }
        };
        animationTimerController.start();
    }

    private void handleUndoAfterClear() {
        AnimationTimerController animationTimerController = new AnimationTimerController(1000) {
            @Override
            public void handle() {
                if (idleMinutes > 58) {
                    idleMinutes--;
                } else if (idleMinutes == 58) {
                    idleMinutes = 0;
                    clearChat();
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
            maxuserExp *= 2;
        }
        playerLevel.setText("You are Level " + userLevel + ".");
        double userProgress = (double) userExp / maxuserExp;
        levelProgress.setProgress(userProgress);
    }

    /**
     * Updates the profile picture of the user in the stats bar in the GUI.
     * @param response the response string generated by the program.
     */
    private void setPlayerGender(String response) {
        if (response.equals("boy")) {
            userImage = boyImage;
            userPic.setImage(userImage);
        } else if (response.equals("girl")) {
            userImage = girlImage;
            userPic.setImage(userImage);
        }
    }
}
