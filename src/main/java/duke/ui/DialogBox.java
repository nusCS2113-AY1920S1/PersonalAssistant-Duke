package duke.ui;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

public class DialogBox extends UiPart<HBox> {
    private static final String FXML = "DialogBox.fxml";
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        super(FXML);
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(getRoot().getChildren());
        Collections.reverse(tmp);
        getRoot().getChildren().setAll(tmp);
        getRoot().setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Returns a Dialog Box corresponding to the User.
     * @param text the text in the Dialog Box.
     * @param img the image of the user.
     * @return the Dialog Box to be added.
     */
    public static HBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img).getRoot();
    }

    /**
     * Returns a Dialog Box corresponding to Duke.
     * @param text the text in the Dialog Box.
     * @param img the image used by Duke.
     * @return the dialog Box to be added.
     */
    public static HBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db.getRoot();
    }
}
