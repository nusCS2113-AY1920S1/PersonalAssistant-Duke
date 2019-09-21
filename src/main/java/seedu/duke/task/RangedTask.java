package seedu.duke.task;

import seedu.duke.ui.Ui;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * This task type inherits from Task. It specifies an event at a particular time.
 */
public class RangedTask extends Task {
    
    /**
     * Unchanged time range from user input.
     */
    protected String time;

    /**
     * String that denotes by when the task should be completed.
     */
    protected String by;

    /**
     * String that denotes from when the task should be completed.
     */
    protected String from;

    /**
     * String that denotes a date time parsed by variable.
     */
    protected String dateBy;

    /**
     * String that denotes a date time parsed from variable.
     */
    protected String dateFrom;

    /**
     * Object of the Ui class that that is used to
     * communicate errors to the user.
     */
    private Ui ui = new Ui();

    /**
     * Initializes description and at variable.
     * 
     * @param time input string of time that includes from and by.
     */
    public RangedTask(String description, String time) {
        super(description);
        this.time = time;
        this.parser(time);
        this.dateBy = this.to_date(this.by);
        this.dateFrom = this.to_date(this.from);
    }

    /**
     * Splits the description into from and by.
     *
     * @param time input time string that includes from and by.
     */
    public void parser(String time) {
        this.from = time.split(" and ")[0];
        this.by = time.split(" and ")[1];
    }

    /**
     * Makes use of the DateTimeFormatter and LocalDateTime class to parse
     * the timeStrig to date time.
     *
     * @param timeString input string that needs to be parsed.
     * @return date/time parsed string
     */
    public String to_date(String timeString) {
        // splitting date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
        LocalDateTime parsedDate;
        try {
            parsedDate = LocalDateTime.parse(timeString, formatter);
        } catch (DateTimeParseException e) {
            ui.date_time_error();
            return timeString;
        }

        String suffix;
        switch (parsedDate.getDayOfMonth() % 10) {
        case 1:
            suffix = "st";
            break;
        case 2:
            suffix = "nd";
            break;
        case 3:
            suffix = "rd";
            break;
        default:
            suffix = "th";
            break;
        }

        if (parsedDate.getDayOfMonth() > 3 && parsedDate.getDayOfMonth() < 21) {
            suffix = "th";
        }

        DateTimeFormatter printFormat = DateTimeFormatter.ofPattern("d'"
            + suffix + "' 'of' MMMM uuuu',' h:mma", Locale.ENGLISH);

        String result = parsedDate.format(printFormat);

        return result;
    }

    /**
     * Overrides the toString method in Task to display task type and date time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[R]" + super.toString() + " (from: " + this.dateFrom
            + ", by: " + this.dateBy + ")";
    }


    /**
     * Overrides the toSaveFormat function to include task type and date time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "R|" + super.toSaveFormat() + "|" + this.time;
    }

    @Override
    public LocalDateTime getDateTime() {
        return null;
    }

    @Override
    public void setDateTime(LocalDateTime dateTime) {

    }

    @Override
    public void setDateTime(String dateTime) {

    }

    /**
     * Checks equality with another Event instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(RangedTask temp) {
        if (this.description == temp.description && this.by == temp.by
            && this.from == temp.from) {
            return true;
        }
        return false;
    }
}
