package duke.modules.data;

import duke.exceptions.ModInvalidTimeException;
import duke.modules.TaskWithWeeklyPeriod;
import duke.util.DateTimeParser;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ModuleTime extends TaskWithWeeklyPeriod {
    /**
     * Constructor for the lecture and tutorial times for a Module.
     * @param task module code
     * @param beginString begin time
     * @param endString end time
     * @param dayOfWeek day of the week
     * @throws ModInvalidTimeException when input time is valid
     */
    public ModuleTime(String task, String beginString, String endString,
                      String dayOfWeek) throws ModInvalidTimeException {
        super(task, DayOfWeek.valueOf(String.valueOf(dayOfWeek)));
        LocalTime begin = DateTimeParser.getStringToDate(beginString).toLocalTime();
        LocalTime end = DateTimeParser.getStringToDate(endString).toLocalTime();
        this.setPeriod(begin, end);
    }

    @Override
    public String writingFile() {
        return this.getTask()
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
        return this.getTask()
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
