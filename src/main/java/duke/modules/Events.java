package duke.modules;

import duke.exceptions.ModInvalidTimeException;
import duke.exceptions.ModInvalidTimePeriodException;
import duke.util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends TaskWithSpanningPeriod {

    /**
     * Constructor for Events class, using String Varargs.
     * @param input Parsed user input containing task name and time.
     */
    public Events(String... input) throws ModInvalidTimeException, ModInvalidTimePeriodException {
        super(input[0]);
        LocalDateTime dateTime = DateTimeParser.getStringToDate(input[input.length - 1]);
        this.period.setPeriod(dateTime, dateTime);
    }

    @Override
    public String writingFile() {
        return "E"
                + "|"
                + super.writingFile()
                + "|"
                + ((LocalDateTime) this.getBegin()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (at: "
                + ((LocalDateTime) this.getBegin()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + ")";
    }
}
