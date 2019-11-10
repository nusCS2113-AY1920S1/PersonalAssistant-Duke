package duke.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

//@@author HUANGXUANKUN

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

    /**
     * A gui component which displays a Dialog with an image.
     *
     * @param text Dialog to be displayed in the
     * @param img  an picture representing the dialog's owner
     */
    private DialogBox(String text, Image img, String colorCode) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
        dialog.setTextFill(Color.web(colorCode));
    }

    /**
     * It returns a dialog block with text in black, as well as user's icon image.
     *
     * @param text Dialog to be displayed in the
     * @param img  an picture representing the dialog's owner
     * @return a JavaFX DialogBox
     */
    static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, "#000000"); //Dialog with black text
    }

    /**
     * It returns a dialog block with text in red and blue, as well as duke's icon image.
     * Exception message will be displayed in red.
     * Duke's reply without exception will be displayed in blue.
     *
     * @param text Dialog to be displayed in the
     * @param img  an picture representing the dialog's owner
     * @return a JavaFX DialogBox
     */
    static DialogBox getDukeDialog(String text, Image img, boolean isException) {
        if (isException) {
            return new DialogBox(text, img, "#ff0000"); //Dialog with red text
        } else {
            return new DialogBox(text, img, "0000FF"); //Dialog with blue text
        }
    }
}