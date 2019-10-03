package owlmoney.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import owlmoney.logic.exception.DukeException;
/**
 * Class representing a recurring task that will occur at the same time weekly.
 */

public class RecurringTask extends Task {
    private LocalDateTime at;
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy hh:mm a");

    /**
     * Initializes a recurring task from its description and its time.
     *
     * @param description A description of the recurring task.
     * @param at The time at which this recurring task happens.
     */
    RecurringTask(String description, LocalDateTime at) {
        super(description);
        this.at = at;
    }

    /**
     * Creates this instance of an Recurring Task object.
     *
     * @param data The raw data to be parsed by {@link #parseRecurringTaskDesc(String)}
     *     and {@link #parseRecurringTaskTime(String)}.
     *
     * @return a new Recurring Task that has description and time properly parsed
     *     and sanitised.
     * @throws DukeException when any of the parsing fails to conform with standards.
     */
    public static RecurringTask create(String data) throws DukeException {
        String description = parseRecurringTaskDesc(data);
        LocalDateTime at = parseRecurringTaskTime(data);
        return new RecurringTask(description, at);
    }

    /**
     * Parses the given data and returns the description of the recurring task.
     *
     * @param data The raw data, which should contain "/at".
     * @return description that has been sanitised.
     * @throws DukeException if date does not conform to standards.         s
     */
    private static String parseRecurringTaskDesc(String data) throws DukeException {
        if (data.isEmpty() || data.isBlank()) {
            throw new DukeException("Description or date cannot be empty or blank spaces only");
        }
        if (!data.contains(" /at ")) {
            throw new DukeException("Recurring task must contain an end date using /at ");
        }
        if ("recurring".equals(data)) {
            throw new DukeException("The description of a recurring task cannot be empty.");
        }
        String[] splitInput = data.split(" /at ");
        if (data.startsWith("recurring /at")) {
            throw new DukeException("The description of a recurring task cannot be empty.");
        }
        if (splitInput.length == 1) {
            throw new DukeException("The recurring task requires an end date/time after specifying /at"
                    + ". Make sure to use <space>/at<space><date>");
        }
        int index = data.lastIndexOf(" /at ");
        String description = data.substring(0, index);
        if (description.isBlank()) {
            throw new DukeException("The description of a recurring task cannot be "
                    + "empty or space even when /at is correct");
        }
        description = description.trim();
        return description;
    }

    /**
     * Parses the given data and returns the time of the recurring task in proper date format.
     *
     * @param data The raw data, which should contain "/at" in its middle followed by
     *             the recurring task time specified in DD/MM/YYYY HHMM.
     * @return date formatted in proper DD/MM/YYYY HHMM format.
     * @throws DukeException if date does not conform to standards.
     */
    private static LocalDateTime parseRecurringTaskTime(String data) throws DukeException {
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
            throw new DukeException("Time must be in the format DD/MM/YYYY HHMM format");
        }
    }

    /**
     * Returns a string representation of this recurring task.
     *
     * @return The desired string representation with more elaborated date formatting.
     */
    @Override
    public String toString() {
        checkRecurringTaskIsAfterCurrent();
        return "[R]" + super.toString()
                + " (at: " + this.at.format(displayFormatter) + ")";
    }

    /**
     * Updates the recurring task date to the following week.
     */
    @Override
    void setDate(LocalDateTime newDate) {
        this.at = newDate;
    }

    /**
     * Checks if current recurring task date is before the current date time.
     * If the date needs to be updated, the done status will also reset to undone.
     */
    public void checkRecurringTaskIsAfterCurrent() {
        LocalDateTime storedDate = getDateTime();
        LocalDateTime currentDate = LocalDateTime.now();
        while (storedDate.isBefore(currentDate)) {
            LocalDateTime newDate = storedDate.plusDays(7);
            setDate(newDate);
            if (isDone()) {
                markUnDone();
            }
            storedDate = getDateTime();
            currentDate = LocalDateTime.now();
        }
    }

    /**
     * Exports this recurring task for saving to disk.
     *
     * @return A string representation of this recurring task containing the task type "E".
     */
    @Override
    public String export() {
        return "R | " + super.export() + super.getDescription().length() + " | " + super.getDescription()
                + " | " + this.at.format(inputFormatter).length() + " | " + this.at.format(inputFormatter);
    }

    /**
     * Returns a LocalDateTime of this recurring task.
     *
     * @return The date and time of this recurring task.
     */

    @Override
    public LocalDateTime getDateTime() {
        return this.at;
    }
}
