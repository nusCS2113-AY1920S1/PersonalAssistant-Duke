package Commands;

import Interface.Storage;
import Interface.Ui;
import JavaFx.AlertBox;
import Tasks.TaskList;
import javafx.scene.control.Alert;

/**
 * Represent the command that deals with tasks that need to be done within a certain period
 */
public class DoWithinPeriodTasksCommand extends Command {

    private final String taskDescription;
    private final String startDate;
    private final String endDate;
    private final boolean isValid;

    /**
     * Creates a DoWithinPeriodTasksCommand object.
     * @param taskDescription The task description given
     * @param startDate The start date given
     * @param endDate The end date given
     * @param isValid is used to store the validity of user's input date
     */
    public DoWithinPeriodTasksCommand (String taskDescription, String startDate, String endDate, boolean isValid) {
        this.taskDescription = taskDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isValid = isValid;
    }

    /**
     * Executes the adding of Do Within Period Tasks object into TaskList object and displays.
     * AlertBox to show reminder.
     * @param events The events TaskList object that contain event tasks
     * @param deadlines The deadlines TaskList object that contain deadline tasks
     * @param ui The Ui object to display the add task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display reminder message
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, Ui ui, Storage storage) {
        if (isValid) {
            AlertBox.display("Reminder Alert", "You have a task to do within a period.",
                    taskDescription + " (from " + startDate + " to " + endDate + ")", Alert.AlertType.INFORMATION);
        }

        return ui.showReminder(taskDescription, startDate, endDate, isValid);
    }
}
