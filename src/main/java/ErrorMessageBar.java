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
public class ErrorMessageBar extends HBox {

    @FXML
    private Label errorMessage;

    /**
     * This TodayTaskBox function acts as the constructor for the TaskBox and will hold the user's tasks
     * for the day and it is automatically generated upon setup.
     *
     * @param dukeException the exceptions thrown to duke will be printed using this portion of the GUI
     */
    private ErrorMessageBar(String dukeException) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/ErrorMessageBar.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        errorMessage.setText(dukeException);
    }

    /**
     * This getUserTaskForToday function is utilised to generate the tasksForToday.
     *
     * @param dukeException the exceptions thrown to duke will be printed using this portion of the GUI
     */
    public static ErrorMessageBar getErrorMessage(String dukeException) {
        return new ErrorMessageBar(dukeException);
    }
}