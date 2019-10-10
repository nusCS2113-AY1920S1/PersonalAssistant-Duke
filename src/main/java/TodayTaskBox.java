import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import task.Task;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class TodayTaskBox extends HBox {

    @FXML
    private Label tasksForTheDay;

    /**
     * This TodayTaskBox function acts as the constructor for the TaskBox and will hold the user's tasks
     * for the day and it is automatically generated upon setup.
     *
     * @param tasksForToday this ArrayList of Tasks contains all the tasks that the user has on the current day.
     */
    private TodayTaskBox(ArrayList<Task> tasksForToday) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/TodayTaskBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String holdTodayTasks = "Today's tasks " + "(" + tasksForToday.size() + ") :\n";
        holdTodayTasks += "------------------------\n";
        if (tasksForToday.size() != 0) {
            for (Task task : tasksForToday) {
                holdTodayTasks += task.toString() + "\n";
            }
        }
        tasksForTheDay.setText(holdTodayTasks);
    }

    /**
     * This getUserTaskForToday function is utilised to generate the tasksForToday.
     *
     * @param tasksForToday this ArrayList of Tasks contains all the tasks that the user has on the current day.
     */
    public static TodayTaskBox getUserTaskForToday(ArrayList<Task> tasksForToday) {
        return new TodayTaskBox(tasksForToday);
    }
}