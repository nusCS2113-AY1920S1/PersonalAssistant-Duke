package duke.tasks;

import duke.exceptions.DukeInvalidTimeException;
import duke.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Events extends Task {
    private LocalDateTime dateTime;

    /**
     * Constructor for Events class, using String Varargs.
     * @param input Parsed user input containing task name and time.
     */
    public Events(String... input) throws DukeInvalidTimeException {
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
        return "E"
                + "|"
                + super.writingFile()
                + "|"
                + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (at: "
                + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + ")";
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

}
