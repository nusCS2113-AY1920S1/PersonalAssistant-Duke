package duke.task.duketasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represent a deadline task and inherits all the fields and methods of Task parent class.
 */
public class Deadline extends Task {

    protected String by; // private instance variables
    protected Date date;
    protected SimpleDateFormat dateFormatter;

    /**
     * Constructor for class Deadline.
     * @param description String containing the description of the task
     * @param by String containing the date and time of the deadline for the task
     * @throws ParseException If date & time format is not in the format:dd/MM/yyyy HHmm
     */
    public Deadline(String description, String by) throws ParseException {
        super(description);
        this.by = by;
        taskType = TaskType.DEADLINE;
        date = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(by);
        dateFormatter = new SimpleDateFormat("d MMMM yyyy, hh:mm a");
    }

    /**
     * Converts user input command to a standardized format to store in file.
     * @return String containing the standardized format
     */
    @Override
    public String toSaveString() {
        return "D" + super.toSaveString() + " | " + by;
    }

    /**
     * Converts user input command to a standardized format in taskList.
     * @return String containing the standardized format
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dateFormatter.format(date) + ")";
    }

    @Override
    public Date getDateTime() {
        return date;
    }
}