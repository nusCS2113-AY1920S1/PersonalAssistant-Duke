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
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents the command to find 5 earliest free time from TaskList object for the user to select
 */
public class FixedDurationTasksCommand extends Command {
    private static final String NO_FIELD ="void";
    private static final int MAX_NO_OF_SLOTS = 5;
    private final String taskDescription;
    private final String duration;

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
     * This function breaks down the instruction given then add the task to list by its task type
     * @param list The TaskList object used to storage all tasks
     */
    private void addByType(TaskList list){
        String taskType = commandAndTaskDetails.getValue().substring(0,3);
        if(taskType.equals("[T]")){
            String[] spiltDetails = commandAndTaskDetails.getValue().split(" ", 2);
            //System.out.println(spiltDetails.length);
            //System.out.println("|"+spiltDetails[1]+"|");
            list.addTask(new Todo(spiltDetails[1]));
        } else if (taskType.equals("[D]")){
            String[] spiltDetails = commandAndTaskDetails.getValue().split("by: ", 2);
            String[] dates = spiltDetails[1].split(" until ");
            //System.out.println("|"+dates[0]+"|");
            list.addTask(new Deadline(taskDescription, dates[0]));
        } else if (taskType.equals("[E]")){
            String[] spiltDetails = commandAndTaskDetails.getValue().split("at: ", 2);
            String[] dates = spiltDetails[1].split(" until ");
            //System.out.println("|"+dates[0]+"|");
            list.addTask(new Event(taskDescription, dates[0]));
        }
    }

    /**
     * Executes the finding of multiple available block period inside the TaskList object with the given duration.
     * @param list The TaskList object used to find free time with the given duration
     * @param ui The Ui object to display the find free time message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the message to display find free time chosen
     * @throws Exception Throws parse exception when there is an error parsing data
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) throws Exception {
        try {
            ArrayList<Pair<Date, Date>>  data = list.findFreeTimes(duration, MAX_NO_OF_SLOTS);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FixedDurationTask.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            fxmlLoader.<FixedDurationTask>getController().getData(data, taskDescription);
            stage.setTitle("Hello Better options");
            stage.showAndWait();

            FixedDurationTask controller = fxmlLoader.getController();
            commandAndTaskDetails = controller.returnData();
            //System.out.println("Command: "+commandAndTaskDetails.getKey() + "\nDetails: " + commandAndTaskDetails.getValue());

            addByType(list);


        } catch (IOException e){
            e.printStackTrace();
        }
        return ui.showFixedDurationTask(commandAndTaskDetails);

    }


}
