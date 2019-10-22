package javacake.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private boolean isSet = false;
    private String displayText = "";
    private char[] charList;
    private int charCount = 0;
    private Timeline textTimeline;

    private String deadlineTextBase = "Deadlines:\n";

    /**
     * Method to create dialogbox with image.
     * @param text text to display from Cake
     * @param img Image of Cake
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPrefWidth(675);
        setStyleLoop();
        setScrollText();
        displayText = text;
        charList = displayText.toCharArray();
        dialog.setText(displayText.substring(0, 1));
        displayPicture.setImage(img);
    }

    /**
     * Method to create dialogbox without image.
     * @param text text to display from Cake
     */
    private DialogBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setStyleLoop();
        setScrollText();
        displayText = deadlineTextBase + text;
        charList = displayText.toCharArray();
        dialog.setText(displayText.substring(0, 1));
    }

    private void setScrollText() {
        textTimeline = new Timeline(new KeyFrame(Duration.millis(10), ev -> {
            if (charCount != charList.length) {
                charCount++;
                dialog.setText(displayText.substring(0, charCount));
            }
        }));
        textTimeline.setCycleCount(Animation.INDEFINITE);
        textTimeline.play();
    }

    private void setStyleLoop() {
        if (MainWindow.isLightMode) {
            this.setStyle("-fx-background-color: #EE8EC7;"
                    + " -fx-background-radius: 20;"
                    + " -fx-border-color: white;"
                    + " -fx-border-radius: 20;");
            dialog.setStyle("-fx-text-fill: white");
        } else {
            this.setStyle("-fx-background-color: #CCC;"
                    + " -fx-background-radius: 20;"
                    + " -fx-border-color: grey;"
                    + " -fx-border-radius: 20;");
            dialog.setStyle("-fx-text-fill: black");
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), ev -> {
            if (charCount == charList.length) {
                textTimeline.stop();
            }
            if (MainWindow.isLightMode) {
                if (isSet) {
                    this.setStyle("-fx-background-color: #EE8EC7;"
                            + " -fx-background-radius: 20;"
                            + " -fx-border-color: white;"
                            + " -fx-border-radius: 20;");
                    dialog.setStyle("-fx-text-fill: white");
                    isSet = false;
                }
            } else {
                if (!isSet) {
                    this.setStyle("-fx-background-color: #CCC;"
                            + " -fx-background-radius: 20;"
                            + " -fx-border-color: grey;"
                            + " -fx-border-radius: 20;");
                    dialog.setStyle("-fx-text-fill: black");
                    isSet = true;
                }
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
     * Method to obtain DialogBox.
     * @param text text to display from Cake
     * @param img Image of Cake
     * @return a DialogBox containing both of them
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Method to obtain DialogBox.
     * @param text text to display from Cake
     * @param img Image of Cake
     * @return a DialogBox containing both of them
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Method to get TaskDialog.
     * @param text text to display from Cake
     * @return a DialogBox containing text
     */
    public static DialogBox getTaskDialog(String text) {
        return new DialogBox(text);
    }
}