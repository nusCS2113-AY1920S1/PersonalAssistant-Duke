package javacake.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class CrashScreen extends GridPane {
    @FXML
    private Label crashText;
    @FXML
    private ImageView crashFace;

    /**
     * Initialise the Main Window launched.
     */
    @FXML
    public void initialize() {
        crashText.setText("Your waifu is disappointed with you for cheating on her!\n"
                + "Please delete your 'data' directory to not upset her again...");
    }
}
