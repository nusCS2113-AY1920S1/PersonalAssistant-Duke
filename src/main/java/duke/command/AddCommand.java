package duke.command;

import duke.Time;
import duke.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * duke.command.AddCommand is a subclass of Command that acts as a skeleton
 * for all other commands that involve adding tasks to the duke.task.TaskList.
 */
public abstract class AddCommand extends Command{

    /**
     * Represents all the information given after the command in the input.
     * Needs to be split and placed into proper variables later on.
     */
    protected String taskDescription;
    protected LocalDateTime time;
    protected String dateTrigger;

    public String MissingDescriptionString = "Please specify the task you want to add!";

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
        } catch(ArrayIndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>();
            msg.add("Please add '" + dateTrigger + " <date>' after your task to specify the event date." );
            Ui.printMsg(msg);
            return false;
        }  catch(DateTimeParseException e) {
            Ui.printDateTimeFormatError();
            return false;
        }
    }
}
