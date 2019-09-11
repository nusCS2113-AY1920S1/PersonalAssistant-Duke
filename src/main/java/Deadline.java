import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents a deadline {@link Task } specified by the due {@link Date}
 */
public class Deadline extends Task {
    private String by;
    private Date date;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.date = getDate(by);
    }

    @Override
    public String toString() {
        return (getDate(by) == null) ? "[D]" + super.toString() + "(by: " + by + ")" : "[D]" + super.toString() + "(by: " + getDateString(date) + ")";
    }

    /**
     * Returns the data by which the deadline must be met
     * @return date the final {@link Date } for finishing the Deadline
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the {@link Date } instance as a String to be printed in the file
     * @param date deadline {@link Date} for finishing the task
     * @return String the date for the deadline
     */
    private String getDateString(Date date) {
        if (date == null)
            return by;
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String pattern = by.length() > 11 ? "d'" + getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy, ha " : "d'" + getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * Returns the String representation of the {@link Deadline} in format compatible to be easily read and written in a text file on the hard disc
     * @return String used to print the {@link Task } in the text file
     */
    public String printInFile() {
        return this.isDone() ? "D|1|" + this.getDescription() + "|" + this.getDateString(date) : "D|0|" + this.getDescription() + "|" + this.getDateString(date);
    }
}