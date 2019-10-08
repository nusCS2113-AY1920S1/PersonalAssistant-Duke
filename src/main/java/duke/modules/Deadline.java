package duke.modules;

import duke.exceptions.ModInvalidTimeException;
import duke.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime dateTime;

    /**
     * Constructor for Deadline class, using String Varargs.
     * @param input Parsed user string input, first input being name,
     *              second input being the date.
     */
    public Deadline(String... input) throws ModInvalidTimeException {
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
        return "D"
            + "|"
            + super.writingFile()
            + "|"
            + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    @Override
    public String toString() {
        return "[D]"
            + super.toString()
            + " (by: "
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
