package JavaFx;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBoxUser extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates the DialogBox object.
     * @param text The text to be displayed
     * @param img The image to be shown
     */
    private DialogBoxUser(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBoxUser.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Displays the user's DiaLog box in the GUI window.
     * @param text The text to be displayed in the user's DialogBox
     * @param img The image to be displayed in the user's DialogBox
     * @return This creates and returns a DialogBox object representing the user
     */
    public static DialogBoxUser getUserDialog(String text, Image img) {
        return new DialogBoxUser(text, img);
    }
}