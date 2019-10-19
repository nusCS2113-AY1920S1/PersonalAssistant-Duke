package scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Box extends HBox {
    private Label text;
    private ImageView displayPicture;

    /**
     * Creates a dialogue box
     * @param l text represents command from user and reply from bot
     * @param iv image user and bot
     */
    public Box(Label l, ImageView iv) {
        text = l;
        displayPicture = iv;

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Changes the position of dialogue box to the other side
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static Box getUserDialog(Label l, ImageView iv) {
        return new Box(l, iv);
    }

    public static Box getDukeDialog(Label l, ImageView iv) {
        var db = new Box(l, iv);
        db.flip();
        return db;
    }
}
