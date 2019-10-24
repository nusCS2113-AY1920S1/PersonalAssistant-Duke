package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The style of the dialog box shown on the UI.
 */
public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    /**
     * Defines the style of the dialogue between the AlphaNUS and the User.
     * @param l a label for the current dialog.
     */
    public DialogBox(Label l) {
        text = l;


        text.setWrapText(true);

        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().addAll(text);
    }
}