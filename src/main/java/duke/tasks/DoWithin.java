package duke.tasks;

import duke.exceptions.DukeInvalidTimeException;
import duke.exceptions.DukeInvalidTimePeriodException;
import duke.util.DateTimeParser;
import duke.util.TimePeriod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DoWithin extends Task {

    private TimePeriod period;

    /**
     * Constructor for do With In tasks.
     * @param description Task name.
     * @param beginString Starting time.
     * @param endString Ending time.
     * @throws DukeInvalidTimeException when the time input cannot be parsed.
     * @throws DukeInvalidTimePeriodException when the period input is invalid.
     */
    public DoWithin(String description, String beginString, String endString)
            throws DukeInvalidTimeException,
            DukeInvalidTimePeriodException {
        super(description);
        LocalDateTime begin = DateTimeParser.getStringToDate(beginString);
        LocalDateTime end = DateTimeParser.getStringToDate(endString);
        period = new TimePeriod(begin, end);
    }

    public TimePeriod getPeriod() {
        return period;
    }

    public void setDateTime(LocalDateTime begin, LocalDateTime end) throws DukeInvalidTimePeriodException {
        this.period = new TimePeriod(begin, end);
    }

    @Override
    public String writingFile() {
        return "W"
                + "|"
                + super.writingFile()
                + "|"
                + period.getBegin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + "|"
                + period.getEnd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    @Override
    public String toString() {
        return "[W]"
                + super.toString()
                + " (begin: "
                + period.getBegin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + ", end: "
                + period.getEnd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
                + ")";
    }

    public boolean isClashing(LocalDateTime localDateTime) {
        return this.period.isClashing(localDateTime);
    }

    public boolean isClashing(TimePeriod timePeriod) {
        return this.period.isClashing(timePeriod);
    }

    public boolean isClashing(DoWithin other) {
        return this.period.isClashing(other.getPeriod());
    }
}
