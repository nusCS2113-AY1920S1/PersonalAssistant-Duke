package duke.gui;

import duke.commands.functional.HelpCommand;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class HelpBox extends HBox {
    @FXML
    private HelpBox helpBox;
    @FXML
    private Label helpText;

    /**
     * Control representing the 'Help Box' within Dukepital's GUI.
     */
    private HelpBox() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/HelpBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helpText.setText(new HelpCommand().getHelpCommands());
    }

    /**
     * Sets up text display for the HelpBox.
     *
     * @return the text displayed in the HelpBox.
     */
    public static HelpBox getHelpGuide() {
        return new HelpBox();
    }

}