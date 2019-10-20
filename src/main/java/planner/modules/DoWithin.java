package planner.modules;

import planner.exceptions.ModInvalidTimeException;
import planner.exceptions.ModInvalidTimePeriodException;
import planner.util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DoWithin extends TaskWithSpanningPeriod {

    /**
     * Constructor for do With In tasks.
     * @param description Task name.
     * @param beginString Starting time.
     * @param endString Ending time.
     * @throws ModInvalidTimeException when the time input cannot be parsed.
     * @throws ModInvalidTimePeriodException when the period input is invalid.
     */
    public DoWithin(String description, String beginString, String endString)
            throws ModInvalidTimeException,
            ModInvalidTimePeriodException {
        super(description);
        LocalDateTime begin = DateTimeParser.getStringToDate(beginString);
        LocalDateTime end = DateTimeParser.getStringToDate(endString);
        this.setPeriod(begin, end);
    }

    @Override
    public String writingFile() {
        return "W"
                + "|"
                + super.writingFile()
                + "|"
                + ((LocalDateTime) this.getBegin()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + "|"
                + ((LocalDateTime) this.getEnd()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    @Override
    public String toString() {
        return "[W]"
                + super.toString()
                + " (begin: "
                + ((LocalDateTime) this.getBegin()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + ", end: "
                + ((LocalDateTime) this.getEnd()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + ")";
    }
}
