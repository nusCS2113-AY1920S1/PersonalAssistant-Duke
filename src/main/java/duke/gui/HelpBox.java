//@@lmtaek

package duke.gui;

import duke.commands.functional.HelpCommand;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

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
    private HelpBox(String text, String colorCode) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/HelpBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helpText.setText(text);
        helpText.setWrapText(true);
        helpText.setAlignment(Pos.CENTER);
        helpText.setTextFill(Color.web(colorCode));
    }

    /**
     * Sets up text display for the HelpBox.
     *
     * @return the text displayed in the HelpBox.
     */
    public static ArrayList<HelpBox> getHelpGuide() {
        ArrayList<HelpBox> helpCommands = new ArrayList<HelpBox>();
        HelpBox introText = new HelpBox("Here's a list of all available commands and their formats:",
                "#d20000");
        helpCommands.add(introText);

        int alternator = 0;
        for (String command : new HelpCommand().getHelpCommands()) {
            if (alternator % 2 == 0) {
                HelpBox newHelpBox = new HelpBox(command, "#000000");
                newHelpBox.setPadding(new Insets(0, 5, 10, 5));
                helpCommands.add(newHelpBox);
            } else {
                HelpBox newHelpBox = new HelpBox(command, "#ab5e4f");
                newHelpBox.setPadding(new Insets(0, 5, 10, 5));
                helpCommands.add(newHelpBox);
            }
            alternator++;
        }

        return helpCommands;
    }

}