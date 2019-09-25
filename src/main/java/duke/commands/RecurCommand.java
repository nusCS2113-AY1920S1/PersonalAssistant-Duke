package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.enums.TaskType;
import duke.exceptions.BadInputException;
import duke.items.DateTime;
import duke.items.Deadline;
import duke.items.Event;
import duke.items.Task;
import duke.enums.CommandType;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Command object for scheduling recurring tasks.
 * Requires recurInterval, numberOfRecur and startDate
 */
public class RecurCommand extends Command {

    private int recurInterval; //number of days
    private int numberOfRecur;
    private String details;
    private String description;

    /**
     * Recurr command stores the recurrence parameters.
     * @param type is CommandType
     * @param description is Task Description
     * @param details is the date and time of task
     * @param recurInterval is the recurrence interval in days
     * @param numberOfRecur is the number of recurrence
     */
    public  RecurCommand(CommandType type, String description, String details, int recurInterval, int numberOfRecur) {
        super(type);
        this.recurInterval = recurInterval;
        this.details = details;
        this.numberOfRecur = numberOfRecur;
        this.description = description;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws BadInputException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Calendar startDate;
        Calendar endDate;

        if (type == CommandType.EVENT) {
            String[] eventDetails = details.split(" to ");

            startDate = new DateTime(eventDetails[0]).getAt();
            endDate = new DateTime(eventDetails[1]).getAt();
        } else {
            startDate = new DateTime(details).getAt();

            // This should not do anything, but intellij wants it to be initialised properly for the switch below
            endDate = new DateTime(details).getAt();
        }

        for (int i = 0; i < numberOfRecur; ++i) {
            DateTime doEnd;
            DateTime doBy;

            startDate.add(Calendar.DATE, recurInterval);
            doBy = new DateTime(sdf.format(startDate.getTime()));

            if (super.type == CommandType.EVENT) {
                endDate.add(Calendar.DATE, recurInterval);
                doEnd = new DateTime(sdf.format(endDate.getTime()));
                list.addItem(TaskType.EVENT, description, doBy, doEnd);
            } else {
                list.addItem(TaskType.DEADLINE, description, doBy);
            }
        }
    }

}






