package duke.tasks;

import duke.exceptions.DukeInvalidTimeException;
import duke.util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FixedDurationTasks extends Task {
    private LocalDateTime dateTime;

    /**
     * Constructor for FixedDurationTasks class, using String Varargs.
     * @param input Parsed user input containing task name and duration.
     */

    public FixedDurationTasks(String... input) throws DukeInvalidTimeException {
        super(input[0]);
        dateTime = DateTimeParser.getStringToDate(input[input.length - 1]);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime localDateTime) {
        this.dateTime = localDateTime;
    }

    @Override
    public String writingFile() {
        return "F"
                + "|"
                + super.writingFile()
                + "|"
                + dateTime.format(DateTimeFormatter.ofPattern("hh:mm"));
    }

    @Override
    public String toString() {
        return "[F]"
                + super.toString()
                + " (needs: "
                + dateTime.format(DateTimeFormatter.ofPattern("hh:mm"))
                + ")";
    }
}

