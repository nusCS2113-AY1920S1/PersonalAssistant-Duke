import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class TodayTaskBox extends HBox {
    @FXML
    private Label tasksForTheDay;

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
        if(tasksForToday.size() != 0)
        {
            for (Task task : tasksForToday) {
                holdTodayTasks += task.toString() + "\n";
            }
        }
        tasksForTheDay.setText(holdTodayTasks);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void shift() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.CENTER_LEFT);
    }

    public static TodayTaskBox getUserTaskForToday(ArrayList<Task> tasksForToday) {
        return new TodayTaskBox(tasksForToday);
    }
}