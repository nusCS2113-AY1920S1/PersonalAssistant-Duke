package duke.ui;

import duke.DukeCore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Collections;

class MessageBox extends UiElement<Region> {
    private static final String FXML = "MessageBox.fxml";
    private static final Image userAvatar = new Image(DukeCore.class.getResourceAsStream("/images/user.png"));
    private static final Image dukeAvatar = new Image(DukeCore.class.getResourceAsStream("/images/duke.png"));

    @FXML
    private HBox container;
    @FXML
    private Circle avatar;
    @FXML
    private VBox messageHolder;
    @FXML
    private Text message;

    /**
     * Constructs a new MessageBox object to be displayed in the command window.
     */
    private MessageBox(String text, Image image) {
        super(FXML, null);

        message.setText(text);
        message.wrappingWidthProperty().bind(messageHolder.prefWidthProperty());
        avatar.setFill(new ImagePattern(image));
    }

    /**
     * Creates a message box for the user's input.
     */
    static MessageBox getUserMessage(String text) {
        MessageBox messageBox = new MessageBox(text, userAvatar);
        // TODO: Fix UI so that user's input is not "flipped".
        messageBox.flip();
        return messageBox;
    }

    /**
     * Creates a new message box for Duke's response.
     */
    static MessageBox getDukeMessage(String text) {
        MessageBox messageBox = new MessageBox(text, dukeAvatar);
        messageBox.flip();
        return messageBox;
    }

    /**
     * Flips the message box such that the {@code avatar} is on the left and the {@code message} is on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(container.getChildren());
        Collections.reverse(tmp);
        container.getChildren().setAll(tmp);
        container.setAlignment(Pos.TOP_LEFT);
    }
}
