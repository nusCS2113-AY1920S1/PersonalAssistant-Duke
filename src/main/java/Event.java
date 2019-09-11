import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents an Event, a {@link Task} which is happening at a specific Date and time
 */
public class Event extends Task {
    private String at;
    private Date date;

    public Event(String description, String at) {
        super(description);
        this.at = at;
        this.date = getDate(at);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + getDateString(date) + ")";
    }
     /**
     * Returns the String representation of the {@link Event} in format compatible to be easily read and written in a text file on the hard disc
     * @return String used to print the {@link Task } in the text file
     */
    public String printInFile() {
        return this.isDone() ? "E|1|" + getDescription() + "|" + this.getDateString(date) : "E|0|" + this.getDescription() + "|" + getDateString(date);
    }

    /**
     * Returns the {@link Date } instance as a String to be printed in the file
     * @param date the {@link Date} at which the event is happening
     * @return String the date for the event
     */
    private String getDateString(Date date) {
        if (date == null)
            return at;
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String pattern = at.length() > 11 ? "d'" + getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy, ha " : "d'" + getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}