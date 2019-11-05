//@@lmtaek

package duke.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class UpcomingTaskBox extends HBox {
    @FXML
    private UpcomingTaskBox upcomingTaskBox;
    @FXML
    private Label upcomingTaskText;

    /**
     * Control representing the 'Help Box' within Dukepital's GUI.
     */
    private UpcomingTaskBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/UpcomingTaskBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        upcomingTaskText.setText(text);
        upcomingTaskText.setWrapText(true);
        upcomingTaskText.setAlignment(Pos.CENTER);
    }

    public static ArrayList<UpcomingTaskBox> createUpcomingTaskBox(ArrayList<String> taskInfo) {
        ArrayList<UpcomingTaskBox> upcomingTaskBoxes = new ArrayList<UpcomingTaskBox>();
        for (String task : taskInfo) {
            upcomingTaskBoxes.add(new UpcomingTaskBox(task));
        }
        return upcomingTaskBoxes;
    }






}