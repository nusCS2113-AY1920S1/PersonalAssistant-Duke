package sgtravel.ui.dialogbox;

import sgtravel.ui.UiPart;
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
 * Dialog Box containing text only.
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

    /**
     * Constructs the DialogBox object.
     *
     * @param text The text to display.
     * @param user The picture of the user.
     */
    private DialogBox(String text, Image user) {
        super(FXML);
        dialog.setText(text);
        dialog.setMinHeight(Label.USE_PREF_SIZE);
        roundImageView(user);
    }

    /**
     * Rounds an image.
     *
     * @param image The image to round.
     */
    private void roundImageView(Image image) {
        ImagePattern pattern = new ImagePattern(image);
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

    /**
     * Flips the node.
     *
     * @param tmp The ObservableList of Nodes.
     * @param root The HBox to interact with.
     */
    private void flipNode(ObservableList<Node> tmp, HBox root) {
        Collections.reverse(tmp);
        root.getChildren().setAll(tmp);
        root.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Gets a User Dialog box for display.
     *
     * @param text The text to display.
     * @param user The picture of the user.
     * @return The DialogBox created.
     */
    public static HBox getUserDialog(String text, Image user) {
        return new DialogBox(text, user).getRoot();
    }

    /**
     * Gets a DialogBox for display.
     *
     * @param text The text to display.
     * @param user The picture of the user.
     * @return The DialogBox created.
     */
    public static HBox getDialog(String text, Image user) {
        DialogBox db = new DialogBox(text, user);
        db.flip();
        return db.getRoot();
    }
}
