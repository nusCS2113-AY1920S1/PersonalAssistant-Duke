package chronologer.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Forms the base of each dialog box.
 */
public class DialogBox extends UiComponent<Region> {
    private static final String FXML = "DialogBox.fxml";
    private static final String USER_ICON_PATH = "/images/UserIcon.png";
    private static final String CHAT_ICON_PATH = "/images/ChatIcon.png";
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs the dialog boxes which make up the chat bot section of the GUI.
     *
     * @param text the text portion of the label is passed in through this parameter.
     */
    private DialogBox(String text, boolean isDuke) {
        super(FXML, null);
        Image userImage = new Image(this.getClass().getResourceAsStream(USER_ICON_PATH));
        Image chronologerImage = new Image(this.getClass().getResourceAsStream(CHAT_ICON_PATH));
        dialog.setText(text);
        if (isDuke) {
            displayPicture.setImage(chronologerImage);
        } else {
            displayPicture.setImage(userImage);
        }
    }

    /**
     * Renders the appropriate type of dialog box with the correct image.
     *
     * @param text the text portion of the label designated for the user is passed in through this parameter.
     *
     */
    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text, false);
    }

    public static DialogBox getChronologerDialog(String text) {
        return new DialogBox(text, true);
    }

}