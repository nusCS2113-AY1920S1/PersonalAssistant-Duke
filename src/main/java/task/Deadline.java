package task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task{
    /**
     * The deadline of the task.
     */
    private Date by;

    /**
     * Constructs a new Deadline object.
     * @param description The description of the task
     * @param by The deadline of the task
     */
    public Deadline(String description, Date by) {
        super(description);
        this.by = by;
    }

    /**
     *
     * @return date of deadline task
     */

    public Date getDate(){
        return by;
    }

    /**
     * Outputs the string with the correct format for printing to UI
     * @return The string formatted for printing to UI
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + new SimpleDateFormat("dd MMM yyyy h:mma").format(by) + ")";
    }

    /**
     * Outputs the string with the correct format for writing to output file
     * @return The string formatted for writing to output file
     */
    @Override
    public String writeToFile(){
        return "D," + super.writeToFile() + "," + new SimpleDateFormat("dd MMM yyyy h:mma").format(by);
    }
}
