import java.io.IOException;
import java.util.Collections;

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
     * The DialogBox essentially acts as the constructor for the dialog boxes which make up the chat bot section of
     * the GUI.
     *
     * @param text the text portion of the label is passed in through this parameter.
     * @param image the image portion of the label is passed in through this parameter.
     */
    private DialogBox(String text, Image image) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(image);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_RIGHT);
    }

    /**
     * The getUserDialog distinction is needed to enable the flipping of the labels to create the chat bot like
     * structure.
     *
     * @param text the text portion of the label designated for the user is passed in through this parameter.
     * @param image the image portion of the label designated for the user is passed in through this parameter.
     */
    public static DialogBox getUserDialog(String text, Image image) {
        return new DialogBox(text, image);
    }

    /**
     * The getDukeDialog is used to flip the labels that are created for Duke to create the chat bot like
     * structure.
     *
     * @param text the text portion of the label for Duke is passed in through this parameter.
     * @param image the image portion of the label for Duke is passed in through this parameter.
     */

    public static DialogBox getDukeDialog(String text, Image image) {
        var dialogBox = new DialogBox(text, image);
        dialogBox.flip();
        return dialogBox;
    }
}