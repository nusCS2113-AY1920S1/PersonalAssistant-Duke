package duke.tasks;

import duke.exceptions.DukeInvalidTimeException;
import duke.exceptions.DukeInvalidTimePeriodException;
import duke.util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DoWithin extends Task {

    /**
     * Constructor for do With In tasks.
     * @param description Task name.
     * @param beginString Starting time.
     * @param endString Ending time.
     * @throws DukeInvalidTimeException when the time input cannot be parsed.
     * @throws DukeInvalidTimePeriodException when the period input is invalid.
     */
    public DoWithin(String description, String beginString, String endString)
            throws DukeInvalidTimeException, DukeInvalidTimePeriodException {
        super(description);
        LocalDateTime begin = DateTimeParser.getStringToDate(beginString);
        LocalDateTime end = DateTimeParser.getStringToDate(endString);
        this.period.setPeriod(begin, end);
    }

    @Override
    public String writingFile() {
        return "W"
                + "|"
                + super.writingFile()
                + "|"
                + this.getBegin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + "|"
                + this.getEnd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    @Override
    public String toString() {
        return "[W]"
                + super.toString()
                + " (begin: "
                + this.getBegin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + ", end: "
                + this.getEnd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + ")";
    }
}
