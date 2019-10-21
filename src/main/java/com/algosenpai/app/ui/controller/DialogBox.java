package com.algosenpai.app.ui.controller;

import java.io.IOException;
import java.util.Collections;

import com.algosenpai.app.ui.Ui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The dialog box template.
 * It represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;


    private DialogBox(String text, Image img, boolean isUser) {
        String fxmlPath = null;
        if (!isUser) {
            fxmlPath = "/view/DialogBox.fxml";
        }
        if (isUser) {
            fxmlPath = "/view/DialogBoxUser.fxml";
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Ui.class.getResource(fxmlPath));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Generates a new DialogBox object containing the user inputted text and his profile picture.
     * @param img the Image of the user.
     * @param text the text to be put into the DialogBox.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, true);
    }

    /**
     * Generates a new DialogBox object containing the program response text and the avatar picture.
     * @param img the Image of the avatar.
     * @param text the text to be put into the DialogBox.
     */
    public static DialogBox getSenpaiDialog(String text, Image img) {
        var db = new DialogBox(text, img, false);
        db.flip();
        return db;
    }
}
