package duke.modules;

import duke.exceptions.ModInvalidTimeException;
import duke.util.DateTimeParser;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Cca extends TaskWithWeeklyPeriod {

    /**
     * Constructor for Cca module.
     * @param task description
     * @param beginString begin time
     * @param endString end time
     * @param dayOfWeek day of week when the cca takes place
     * @throws ModInvalidTimeException when input time is invalid
     */
    public Cca(String task, String beginString, String endString, String dayOfWeek) throws ModInvalidTimeException {
        super(task, DayOfWeek.valueOf(dayOfWeek));
        LocalTime begin = DateTimeParser.getStringToDate(beginString).toLocalTime();
        LocalTime end = DateTimeParser.getStringToDate(endString).toLocalTime();
        this.setPeriod(begin, end);
    }

    @Override
    public String writingFile() {
        return "C"
                + "|"
                + super.writingFile()
                + "|"
                + this.getBeginTime().format(DateTimeFormatter.ofPattern("[HH:mm]"))
                + "|"
                + this.getEndTime().format(DateTimeFormatter.ofPattern("[HH:mm]"))
                + "|"
                + this.getDayOfWeek();
    }

    @Override
    public String toString() {
        return "[C]"
                + super.toString()
                + " ("
                + this.getBeginTime().format(DateTimeFormatter.ofPattern("[HH:mm]"))
                + " - "
                + this.getEndTime().format(DateTimeFormatter.ofPattern("[HH:mm]"))
                + " on "
                + this.getDayOfWeek()
                + ")";
    }
}
