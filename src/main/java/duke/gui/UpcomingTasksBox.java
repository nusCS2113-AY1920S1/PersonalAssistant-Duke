//@@author lmtaek

package duke.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class UpcomingTasksBox extends HBox {
    @FXML
    private UpcomingTasksBox upcomingTasksBox;
    @FXML
    private Label upcomingTaskText;

    /**
     * Control representing the 'Upcoming Tasks Box' within Dukepital's GUI.
     */
    private UpcomingTasksBox(String text) {
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
    }

    /**
     * Creates UpcomingTasksBox for each of the tasks listed within a date.
     * @param taskInfo The String used within the UpcomingTasksBox
     * @return An array of all the UpcomingTasksBoxes designated for a date.
     */
    public static ArrayList<UpcomingTasksBox> createUpcomingTasksBoxesForDate(ArrayList<String> taskInfo) {
        ArrayList<UpcomingTasksBox> upcomingTaskBoxes = new ArrayList<UpcomingTasksBox>();
        for (String task : taskInfo) {
            UpcomingTasksBox currentTaskBox = new UpcomingTasksBox(task);
            currentTaskBox.setPadding(new Insets(5, 5, 5, 5));
            upcomingTaskBoxes.add(new UpcomingTasksBox(task));
        }
        return upcomingTaskBoxes;
    }






}