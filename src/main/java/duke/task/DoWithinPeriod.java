package duke.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a tasks that is to be done within a time period.
 */
public class DoWithinPeriod extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Takes in the description and the time period of the task.
     * @param description String holding the description of the task
     * @param from LocalDateTime of the starting time of the task
     * @param to LocalDateTime of the ending time of the task
     */
    public DoWithinPeriod(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from =  from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[W]" + super.toString() + " (within: " + dateToString(from) + " - " + dateToString(to) + ")";
    }

    /**
     * Converts the input LocalDateTime to printable format in String.
     * @param dateTime LocalDateTime object to be converted to String
     * @return String format of the LocalDateTime
     */
    private String dateToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }
}