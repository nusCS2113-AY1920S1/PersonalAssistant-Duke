package duke.gui;

import duke.DukeCore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Collections;

public class MessageBox extends HBox {
    @FXML
    private Circle displayPicture;
    @FXML
    private VBox dialogHolder;
    @FXML
    private Text dialog;

    /**
     * Creates a new MessageBox object to be displayed in the chat window of the GUI.
     */
    private MessageBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource("/view/MessageBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        dialog.wrappingWidthProperty().bind(dialogHolder.prefWidthProperty());
        displayPicture.setFill(new ImagePattern(img));
    }

    /**
     * Creates a dialog box for the user's input.
     */
    public static MessageBox getUserDialog(String text, Image img) {
        var db = new MessageBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Creates a new dialog box for Duke's response.
     */
    public static MessageBox getDukeDialog(String text, Image img) {
        var db = new MessageBox(text, img);
        db.flip();
        return db;
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
}
