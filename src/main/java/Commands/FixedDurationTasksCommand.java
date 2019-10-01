package Commands;

import Interface.Storage;
import Interface.Ui;
import JavaFx.FixedDurationTask;
import Tasks.TaskList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents the command to find 5 earliest free time from TaskList object for the user to select
 */
public class FixedDurationTasksCommand extends Command {
    private static final String NO_FIELD = new String ("void");
    private static final int MAX_NO_OF_SLOTS = 5;
    String taskDescription;
    String duration;

    /**
     * Creates a FixedDurationTasksCommand object.
     * @param taskDescription The task description given
     * @param duration The duration given
     */
    public FixedDurationTasksCommand(String taskDescription, String duration) {
        this.taskDescription = taskDescription;
        this.duration = duration;
    }

    private Pair<String, String> commandAndTaskDetails = new Pair<>(NO_FIELD,NO_FIELD);

    /**
     * Executes the finding of multiple available block period inside the TaskList object with the given duration.
     * @param ui The Ui object to display the find free time message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the message to display find free time chosen
     * @throws Exception Throws parse exception when there is an error parsing data
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        try {
            ArrayList<Pair<Date, Date>>  data = events.findFreeTimes(duration, MAX_NO_OF_SLOTS);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FixedDurationTask.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            fxmlLoader.<FixedDurationTask>getController().getDataNeeded(data, taskDescription);
            stage.setTitle("Hello Better options");
            stage.showAndWait();

            FixedDurationTask controller = fxmlLoader.getController();
            commandAndTaskDetails = controller.returnData();
        } catch (IOException e){
            e.printStackTrace();
        }
        return ("Your selected task is been added.\n" + commandAndTaskDetails.getValue());

    }


}
