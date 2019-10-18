package dolla.command;

import dolla.Time;
import dolla.Ui;
import dolla.task.Task;
import dolla.task.TaskList;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * duke.command.AddCommand is a subclass of Command that acts as a skeleton
 * for all other commands that involve adding tasks to the duke.task.TaskList.
 */
public abstract class AddCommand extends Command {

    /**
     * Represents all the information given after the command in the input.
     * Needs to be split and placed into proper variables later on.
     */
    protected String taskDescription;
    protected LocalDateTime time;
    protected String dateTrigger;

    public String missingDescriptionString = "Please specify the task you want to add!";

    public AddCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * Returns true if the method runs without running into any error.
     * <p>
     *     This method splits and correctly assigns the task description and time from the given input.
     * </p>
     * <p>
     *     If the incorrect format is given in the input, the corresponding alert will be printed, and
     *     the method will then return false.
     * </p>
     * @return true if method runs successfully.
     * @see AddDeadlineCommand
     * @see AddEventCommand
     */
    public boolean splitDescTime() {
        String[] data = taskDescription.split(dateTrigger + " "); // data[0] os description, data[1] is the time
        try {
            time = Time.readDateTime(data[1]);
            taskDescription = data[0];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            Ui.printMsg("Please add '" + dateTrigger + " <date>' after your task to specify the event date.");
            return false;
        }  catch (DateTimeParseException e) {
            Ui.printDateTimeFormatError();
            return false;
        }
    }

    /**
     * Returns true if the method runs without running into any error.
     * <p>
     *     This method checks if any other task in the specified TaskList conflicts with the given time.
     * </p>
     * <p>
     *     If the incorrect format is given in the input, the corresponding alert will be printed, and
     *     the method will then return false.
     * </p>
     * @return true if method runs successfully.
     * @see AddDeadlineCommand
     * @see AddEventCommand
     */
    public boolean detectAnomalies(TaskList tasks, LocalDateTime time) {
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.getFromList(i);
            if (currTask.getDate().compareTo(time) == 0) {
                Ui.printTimeConflictError(tasks.getFromList(i));
                return false;
            }
        }
        return true;
    }
}
