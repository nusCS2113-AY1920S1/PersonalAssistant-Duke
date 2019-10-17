package javafx;

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

import java.io.IOException;
import java.util.Collections;


/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;

    /**
     * Constructor that creates a dialog box containing text of the poster.
     * The dimensions and settings of this dialog box is imported from an fxml file.
     *
     * @param text The text to be shown in the dialog box
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

        dialog.setText(text);
        //displayPicture.setImage(img);
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
     * Method to get and display a text and in a dialog box.
     * This is used to get the input of the user.
     *
     * @param text The user input to be shown.
     * @return A dialog box containing the text to be displayed in the GUI
     */
    public static DialogBox getUserDialog(String text) {
        var db = new DialogBox(text);
        db.flip();
        return db;
    }

    /**
     * Method to get and display text in a dialog box.
     * This is used for responses from JavaFX.Main.Duke.
     *
     * @param text JavaFX.Main.Duke's response to user input.
     * @return A flipped dialog box containing JavaFX.Main.Duke's response
     */
    public static DialogBox getDukeDialog(String text) {
        var db = new DialogBox(text);
        db.flip();
        return db;
    }
}