package duke.ui.dialogbox;

import duke.ui.UiPart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Collections;

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
    private HBox dialogBox;
    @FXML
    private Circle circle;
    @FXML
    private HBox miniBox;

    private DialogBox(String text, Image img) {
        super(FXML);
        dialog.setText(text);
        dialog.setMinHeight(Label.USE_PREF_SIZE);
        roundImageView(img);
    }

    /**
     * Rounds an image.
     */
    private void roundImageView(Image img) {
        ImagePattern pattern = new ImagePattern(img);
        circle.setFill(pattern);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        miniBox.getStyleClass().remove("dialog");
        miniBox.getStyleClass().add("dialogFlip");
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getRoot().getChildren());
        flipNode(tmp, this.getRoot());
        ObservableList<Node> tmp2 = FXCollections.observableArrayList(miniBox.getChildren());
        flipNode(tmp2, miniBox);
    }

    private void flipNode(ObservableList<Node> tmp, HBox root) {
        Collections.reverse(tmp);
        root.getChildren().setAll(tmp);
        root.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Gets a User Dialog box for display.
     */
    public static HBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img).getRoot();
    }

    /**
     * Gets a Duke Dialog box for display.
     */
    public static HBox getDukeDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db.getRoot();
    }
}
