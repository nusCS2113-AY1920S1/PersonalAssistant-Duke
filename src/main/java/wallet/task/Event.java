package wallet.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {
    /**
     * The date of the event.
     */
    private Date date;

    /**
     * Constructs a new Event object.
     *
     * @param description The description of the task.
     * @param date The date of the event.
     */
    public Event(String description, Date date) {
        super(description);
        this.date = date;
    }

    /**
     * Returns the date of event task.
     *
     * @return date of event task.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Outputs the string with the correct format for printing to UI.
     *
     * @return The string formatted for printing to UI.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + new SimpleDateFormat("dd MMM yyyy h:mma").format(date) + ")";
    }

    /**
     * Outputs the string with the correct format for writing to output file.
     *
     * @return The string formatted for writing to output file.
     */
    @Override
    public String writeToFile() {
        return "E," + super.writeToFile() + "," + new SimpleDateFormat("dd MMM yyyy h:mma").format(date);
    }
}