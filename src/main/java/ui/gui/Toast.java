package ui.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.ArrayList;

// @@author {Mudaafi}-reused
// Solution below adapted from:
// https://stackoverflow.com/questions/18669209/javafx-what-is-the-best-way-to-display-a-simple-message
public final class Toast {
    private static int TOAST_TIMEOUT = 2000;
    private static ArrayList<String> toastedArr = new ArrayList<>();

    private static Popup createPopup(final String message) {
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        Label label = new Label(message);
        label.getStylesheets().add("/css/mainStyles.css");
        label.getStyleClass().add("popup");
        popup.getContent().add(label);
        return popup;
    }

    /**
     * Creates a popup message for the User on the Graphical User Interface.
     * @param stage Stage the popup is to appear on
     * @param message String to be printed to the User
     */
    public static void makeText(final Stage stage, final String message) {
        Popup popup = createPopup(message);
        popup.setOnShown(e -> {
            popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
            popup.setY(stage.getHeight() + (1.5 - toastedArr.size()) * popup.getHeight());
            popup.setWidth(stage.getWidth());
        });
        popup.show(stage);
        toastedArr.add(popup.toString());
        int toastedTime = TOAST_TIMEOUT * (1 + message.length() / 40);
        new Timeline(new KeyFrame(Duration.millis(toastedTime), ae -> {
            popup.hide();
            toastedArr.remove(popup.toString());
        })).play();
    }
}