package duke.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.shape.Circle;     //import needed to clip the ImageView into a circle
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    public DialogBox(Label l, ImageView iv) {
        text = l;
        displayPicture = iv;

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        displayPicture.setClip(new Circle(50,50,50));    //Clip the ImageView into a circle
        text.setPadding(new Insets(0,10,0,10));    //Add padding between the ImageView and the text

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);

        //Add padding around the inside edges of the DialogBox
        this.setPadding(new Insets(10));     //give 4 numbers for top,right,bottom,left
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static duke.GUI.DialogBox getUserDialog(Label l, ImageView iv) {
        return new duke.GUI.DialogBox(l, iv);
    }

    public static duke.GUI.DialogBox getDukeDialog(Label l, ImageView iv) {
        var db = new duke.GUI.DialogBox(l, iv);
        db.flip();
        return db;
    }

}
