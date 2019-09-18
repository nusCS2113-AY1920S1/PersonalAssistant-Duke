package seedu.duke.task;

import seedu.duke.ui.Ui;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * This task type inherits from Task. It specifies an event at a particular time.
 */
public class Event extends Task {

    /**
     * String that denotes when the event is going to happen.
     */
    protected String at;

    /**
     * String that denotes a date time parsed at variable.
     */
    protected String dateAt;

    /**
     * Object of the Ui class that that is used to
     * communicate errors to the user.
     */
    private Ui ui = new Ui();

    /**
     * Initializes description and at variable.
     */
    public Event(String description, String at) {
        super(description);
        this.at = at;
        this.to_date();
    }

    /**
     * Makes use of the DateTimeFormatter and LocalDateTime class to parse
     * the user input date time and initializes the date_at member variable.
     */
    public void to_date() {
        // splitting date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
        LocalDateTime parsedDate;
        try {
            parsedDate = LocalDateTime.parse(this.at, formatter);
        } catch (DateTimeParseException e) {
            ui.date_time_error();
            this.dateAt = this.at;
            return;
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

        this.dateAt = parsedDate.format(printFormat);
    }

    /**
     * Overrides the toString method in Task to display task type and date time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[E]" + super.toString() + " (at: " + this.dateAt + ")";
    }


    /**
     * Overrides the toSaveFormat function to include task type and date time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "E|" + super.toSaveFormat() + "|" + this.at;
    }

    /**
     * Checks equality with another Event instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(Event temp) {
        if (this.description == temp.description && this.at == temp.at
                && this.dateAt == temp.dateAt) {
            return true;
        }
        return false;
    }

    /**
     * Overrides the getDateTime method in Task to obtain the event's date and time.
     *
     * @return date and time of event of type LocalDateTime.
     */
    @Override
    public LocalDateTime getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
        return LocalDateTime.parse(this.at, formatter);
    }

    /**
     * Overrides the setDateTime method in Task to set the event's date and time.
     *
     * @param dateTime the date and time of the event of type LocalDateTime.
     */
    @Override
    public void setDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
        at = dateTime.format(formatter);
        this.to_date();
    }

    /**
     * Overrides the setDateTime method in Task to set the event's date and time.
     *
     * @param dateTime string of the date and time of the event.
     */
    @Override
    public void setDateTime(String dateTime) {
        at = dateTime;
        this.to_date();
    }
}
