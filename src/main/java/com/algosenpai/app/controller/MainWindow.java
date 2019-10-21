package com.algosenpai.app.controller;

import com.algosenpai.app.DialogBox;
import com.algosenpai.app.Logic;
import com.algosenpai.app.command.Command;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for MainWindow. Provides the layout for the other controls.
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

    private Logic logic;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/unknown.png"));
    private Image senpaiImage = new Image(this.getClass().getResourceAsStream("/images/miku.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog("Welcome to AlgoSenpai Adventures!", senpaiImage));
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
        String input = userInput.getText();
        String response = logic.parseInput(input);
        printUserText(input, userImage);
        printSenpaiText(response, senpaiImage);
    }

    /**
     * Creates the dialog box on GUI to show the user what he/she has typed.
     * Clears the user input after processing.
     * @param text the String that user has typed in
     * @param image the profile picture of the user.
     */
    public void printSenpaiText(String text, Image image) {
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

}