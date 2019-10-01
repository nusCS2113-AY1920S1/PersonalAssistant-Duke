package Commands;

import Interface.Storage;
import Interface.Ui;
import JavaFx.FixedDurationTask;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.TaskList;
import Tasks.Todo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents the command to find 5 earliest free time from TaskList object for the user to select.
 */
public class FixedDurationTasksCommand extends Command {
    private static final String NO_FIELD ="void";
    private static final int MAX_NO_OF_SLOTS = 5;
    private final String taskDescription;
    private final String duration;
    private final String type;
    private String taskDetails = NO_FIELD;

    /**
     * Creates a FixedDurationTasksCommand object.
     * @param taskDescription The task description given
     * @param duration The task description given
     * @param type The type task given
     */
    public FixedDurationTasksCommand(String taskDescription, String duration, String type) {
        this.taskDescription = taskDescription;
        this.duration = duration;
        this.type = type;
    }

    /**
     * This function breaks down the instruction given then add the task to list by its task type.
     * @param todos The TaskList object used to storage all todo tasks
     * @param events The TaskList object used to storage all event tasks
     * @param deadlines The TaskList object used to storage deadline tasks
     * @throws ParseException The error when the data being passed contains an error
     */
    private void addByType(TaskList todos, TaskList events, TaskList deadlines) throws ParseException {
        String taskType = taskDetails.substring(0,3);
        if(taskType.equals("[T]")){
            String[] spiltDetails = taskDetails.split(" ", 2);
            todos.addTask(new Todo(spiltDetails[1]));
        } else if (taskType.equals("[D]")){
            String[] spiltDetails = taskDetails.split("by: ", 2);
            String[] dates = spiltDetails[1].split(" until ");
            deadlines.addTask(new Deadline(taskDescription, dates[0]));
        } else if (taskType.equals("[E]")){
            String[] spiltDetails = taskDetails.split("at: ", 2);
            String[] dates = spiltDetails[1].split(" until ");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            String date = dateFormat.format(dateTimeFormat.parse(dates[0]));
            String timeStart = timeFormat.format(dateTimeFormat.parse(dates[0]));
            String timeEnd = timeFormat.format(dateTimeFormat.parse(dates[1]));
            events.addTask(new Event(taskDescription, date, timeStart, timeEnd));
        }
    }

    /**
     * Executes the finding of multiple available block period inside the TaskList object with the given duration.
     * @param todos The todo TaskList object used to find free times with the given duration
     * @param events The event TaskList object used to find free times with the given duration
     * @param deadlines The deadline TaskList object used to find frees time with the given duration
     * @param ui The Ui object to display the chosen free time message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display free time chosen
     * @throws Exception Throws ParseException is findFreeTimes contains error
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        try {
            ArrayList<Pair<Date, Date>>  data = events.findFreeTimes(duration, MAX_NO_OF_SLOTS, type);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FixedDurationTask.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            fxmlLoader.<FixedDurationTask>getController().getData(data, taskDescription, type);
            stage.setTitle("Hello Better options");
            stage.showAndWait();

            FixedDurationTask controller = fxmlLoader.getController();
            taskDetails = controller.returnData();
            addByType(todos, events, deadlines);


        } catch (IOException e){
            e.printStackTrace();
        }
        return ui.showFixedDurationTask(taskDetails);

    }


}