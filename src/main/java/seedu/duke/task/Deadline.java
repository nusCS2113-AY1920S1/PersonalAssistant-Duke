package seedu.duke.task;

import seedu.duke.ui.Ui;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * This task type inherits from Task.
 * It specifies a day before which a task should be completed.
 */
public class Deadline extends Task {
    /**
     * String that denotes when the deadline is due.
     */
    protected String by;

    /**
     * String that denotes variable by converted
     * to a parsed date time format.
     */
    protected String dateBy;

    /**
     * Ui instance to communicate errors.
     */
    private Ui ui = new Ui();

    /**
     * Initializes description and by.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.translate_date();
    }

    /**
     * Makes use of the DateTimeFormatter and LocalDateTime class
     * to parse the user input date time and initializes the
     * dateBy member variable.
     */
    public void translate_date() {
        // splitting date into indi
        DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
        LocalDateTime parsedDate;
        try {
            parsedDate = LocalDateTime.parse(this.by, formatter);
        } catch (DateTimeParseException e) {
            ui.date_time_error();
            this.dateBy = this.by;
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

        this.dateBy = parsedDate.format(printFormat);
    }

    /**
     * Overrides the toString method in Task to display task type and date time.
     *
     * @return a string with the target info.
     */
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dateBy + ")";
    }

    /**
     * Overrides the toSaveFormat function to include task type and date time.
     *
     * @return a string with pipe separated info.
     */
    public String toSaveFormat() {
        return "D|" + super.toSaveFormat() + "|" + this.by;
    }

    /**
     * Checks equality with another Deadline instance.
     *
     * @param temp the instance to compare against.
     * @return true or false to the comparison.
     */
    public boolean equals(Deadline temp) {
        if (this.description == temp.description && this.by == temp.by
            && this.dateBy == temp.dateBy) {
            return true;
        }
        return false;
    }

    /**
     * Overrides the getDateTime method in Task to obtain the deadline date and time.
     *
     * @return date and time of deadline of type LocalDateTime.
     */
    @Override
    public LocalDateTime getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
        return LocalDateTime.parse(this.by, formatter);
    }

    /**
     * Overrides the setDateTime method in Task to set the deadline's date and time.
     *
     * @param dateTime the date and time of the deadline of type LocalDateTime.
     */
    @Override
    public void setDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
        by = dateTime.format(formatter);
        this.translate_date();
    }

    /**
     * Overrides the setDateTime method in Task to set the deadline's date and time.
     *
     * @param dateTime string of the date and time of the deadline.
     */
    @Override
    public void setDateTime(String dateTime) {
        by = dateTime;
        this.translate_date();
    }



}