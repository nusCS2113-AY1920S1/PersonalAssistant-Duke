package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;

/**
 * Class representing an event that will occur at or around a specified time.
 */
public class Event extends Task {
    private final LocalDateTime at;
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy hh:mm a");

    /**
     * Initializes an Event from its description and its time.
     *
     * @param description A description of the event.
     * @param at The time at which this event happens.
     */
    Event(String description, LocalDateTime at) {
        super(description);
        this.at = at;
    }

    /**
     * Creates this instance of an Event object.
     *
     * @param data The raw data to be parsed by {@link #parseEventDesc(String)}
     *     and {@link #parseEventTime(String)}.
     *
     * @return a new Event task that has description and event time properly parsed
     *     and sanitised.
     * @throws DukeException when any of the parsing fails to conform with standards.
     */
    public static Event create(String data) throws DukeException {
        String description = parseEventDesc(data);
        LocalDateTime at = parseEventTime(data);
        return new Event(description, at);
    }

    /**
     * Parses the given data and returns the description of the event.
     *
     * @param data The raw data, which should contain "/at".
     * @return description that has been sanitised.
     * @throws DukeException if date does not conform to standards.         s
     */
    private static String parseEventDesc(String data) throws DukeException {
        if (data.isEmpty() || data.isBlank()) {
            throw new DukeException("Description or date cannot be empty or blank spaces only");
        }
        if (!data.contains(" /at ")) {
            throw new DukeException("Event must contain an end date using /at ");
        }
        if ("event".equals(data)) {
            throw new DukeException("The description of an event cannot be empty.");
        }
        String[] splitInput = data.split(" /at ");
        if (data.startsWith("event /at")) {
            throw new DukeException("The description of an event cannot be empty.");
        }
        if (splitInput.length == 1) {
            throw new DukeException("The event requires an end date/time after specifying /at"
                    + ". Make sure to use <space>/at<space><date>");
        }
        int index = data.lastIndexOf(" /at ");
        String description = data.substring(0, index);
        if (description.isBlank()) {
            throw new DukeException("The description of an event cannot be "
                    + "empty or space even when /at is correct");
        }
        description = description.trim();
        return description;
    }

    /**
     * Parses the given data and returns the time of the event in proper date format.
     *
     * @param data The raw data, which should contain "/at" in its middle followed by
     *             the event time specified in DD/MM/YYYY HHMM.
     * @return date formatted in proper DD/MM/YYYY HHMM format.
     * @throws DukeException if date does not conform to standards.
     */
    private static LocalDateTime parseEventTime(String data) throws DukeException {
        int index = data.lastIndexOf(" /at ");
        String date = data.substring(index + 5, data.length()); //+5 because of _/at_
        if (date.isBlank() || date.isEmpty()) {
            throw new DukeException("The time cannot be empty or space bar");
        }

        String dateRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        String timeRegex = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
        String[] dateTime = date.split(" ", 2);
        if (dateTime.length != 2) {
            throw new DukeException("The format is wrong, please try in DD/MM/YYYY HHMM format");
        }
        String dateOnly = dateTime[0].trim();
        String timeOnly = dateTime[1].trim();

        if (!dateOnly.matches(dateRegex)) {
            throw new DukeException("The date format is wrong, please try in DD/MM/YYYY format");
        }
        if (!timeOnly.matches(timeRegex)) {
            throw new DukeException("The time format is wrong, please try again in HHMM format");
        }

        try {
            return LocalDateTime.parse(date, inputFormatter);
        } catch (DateTimeParseException e) {
            throw new DukeException("Time must be in the format day#/month#/yyyy hhmm.");
        }
    }

    /**
     * Returns a string representation of this event.
     *
     * @return The desired string representation with more elaborated date formatting.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (at: " + this.at.format(displayFormatter) + ")";
    }

    /**
     * Exports this event for saving to disk.
     *
     * @return A string representation of this event containing the task type "E".
     */
    @Override
    public String export() {
        return "E | " + super.export() + super.getDescription().length() + " | " + super.getDescription()
                + " | " + this.at.format(inputFormatter).length() + " | " + this.at.format(inputFormatter);
    }
}
