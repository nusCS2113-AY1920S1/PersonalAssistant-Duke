package duke.task;

import duke.core.DateTimeParser;
import duke.core.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a event. It is
 * extended from the Task class.
 */
public class Event extends Task {
    /**
     * A string that represents the time of the event.
     */
    private String dateTime;
    private String dateTimeEnglish;

    /**
     * Constructs a Event object. Date and time are parsed and
     * stored in dateTime field if input is of "dd/MM/yyyy HHmm"
     * format.
     *
     * @param description A string that saves the description of the task.
     * @param dateTime          A string that specifies the time of the event.
     */
    public Event(String description, String dateTime) throws DukeException {
        super(description);
        super.updateLocalDateTime(dateTime);
        this.dateTime = dateTime;
        dateTimeEnglish = DateTimeParser.convertToEnglishDateTime(dateTime);
    }

    /**
     * Returns a string pattern to the user output
     *
     * @return A string which displays the type,
     * description and deadline of the task.
     */
    @Override
    public String toString() {

        if (recurringTask != null) {
            DateTimeFormatter newDateFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HHmm");
            String newDate = recurringTask.recurringTaskTimeUpdate(this).format(newDateFormatter);
            this.dateTime = newDate;
            try {
                this.dateTimeEnglish = DateTimeParser.convertToEnglishDateTime(dateTime);
            } catch (DukeException e) {
                System.out.println("I couldn't convert your given time. " + e);
            }
        }
        return "[E]"
                + super.printStatus()
                + " (at: "
                + dateTimeEnglish + ")";
    }

    /**
     * Returns a string with the following format to be stored in a local file
     *
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt() {
        String frequency = "ONCE";
        if (isTaskRecurring()) {
            frequency = recurringTask.getFrequency().toString();
        }
        return "E | "
                + (isDone() ? "1" : "0")
                + " | "
                + getDescription()
                + " | "
                + dateTime
                + " | "
                + frequency;
    }
}