package duke.ui;

import duke.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import java.io.IOException;

public class HelpWindow extends HBox {
    private static final String USERGUIDE_URL = "https://github.com/AY1920S1-CS2113T-T12-4/main/blob/master/docs/README.adoc";
    private static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    @FXML
    private Button copyButton;
    @FXML
    private Label helpMessage;

    public HelpWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/HelpWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Copies the URL of the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        Window owner = copyButton.getScene().getWindow();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Copied URL",
                "URL link has been copied");
    }
}