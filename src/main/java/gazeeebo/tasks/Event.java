package gazeeebo.tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//@@author yueyuu

/**
 * An event object.
 */
public class Event extends Task {
    public LocalDate date;
    public LocalTime start;
    public LocalTime end;

    private static DateTimeFormatter fmtED = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //formatter for date
    private static DateTimeFormatter fmtET = DateTimeFormatter.ofPattern("HH:mm:ss"); //formatter for time

    /**
     * Creates an event object given a description, date and time.
     *
     * @param description the description of the event
     * @param at contains the date and time of the event
     * @throws DateTimeParseException if the date and time is in the wrong format
     * @throws ArrayIndexOutOfBoundsException if the date and/or time is not provided
     */
    public Event(String description, String at) throws DateTimeParseException, ArrayIndexOutOfBoundsException {
        super(description);
        String[] dateTime = at.split(" ");
        String[] time = dateTime[1].split("-");

        this.date = LocalDate.parse(dateTime[0], fmtED);
        this.start = LocalTime.parse(time[0], fmtET);
        this.end = LocalTime.parse(time[1], fmtET);
    }

    @Override
    public String toString() {
        return "E" + "|" + super.getStatusIcon() + "|" + super.description + "|" + "at: " + fmtED.format(date)
                + " " + this.start.format(fmtET) + "-" + this.end.format(fmtET);
    }

    /**
     * Formats the event's description, date and time into the correct format to be printed.
     *
     * @return the event as a formatted string
     */
    public String listFormat() {
        String dateString = date.format(DateTimeFormatter.ofPattern("dd LLL yyyy"));

        return "[E]" + "[" + super.getStatusIcon() + "]" + super.description + "(at:" + dateString + " "
                + this.start.format(fmtET) + "-" + this.end.format(fmtET) + ")";
    }

}