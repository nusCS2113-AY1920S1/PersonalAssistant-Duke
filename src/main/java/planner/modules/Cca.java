package planner.modules;

import planner.exceptions.ModInvalidIndexException;
import planner.exceptions.ModInvalidTimeException;
import planner.util.DateTimeParser;
import planner.util.TimePeriodWeekly;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Cca extends TaskWithMultipleWeeklyPeriod {

    /**
     * Constructor for Cca module.
     * @param task description
     * @param beginString begin time
     * @param endString end time
     * @param dayOfWeek day of week when the cca takes place
     * @throws ModInvalidTimeException when input time is invalid
     */
    public Cca(String task, String beginString, String endString, String dayOfWeek) throws ModInvalidTimeException {
        super(task, DayOfWeek.valueOf(dayOfWeek.toUpperCase()));
        LocalTime begin = DateTimeParser.getStringToDate(beginString).toLocalTime();
        LocalTime end = DateTimeParser.getStringToDate(endString).toLocalTime();
        try {
            this.setPeriod(0, begin, end);
        } catch (ModInvalidIndexException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String writingFile() {
        StringBuilder prefix = new StringBuilder("C|" + super.writingFile());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[HH:mm]");
        for (TimePeriodWeekly period: this.getPeriods()) {
            prefix.append("|")
                    .append(period.getBeginTime().format(formatter))
                    .append("~")
                    .append(period.getEndTime()
                            .format(formatter)).append("~")
                    .append(period.getDayOfWeek());
        }
        return prefix.toString();
    }

    @Override
    public String toString() {
        StringBuilder prefix = new StringBuilder("[C]" + super.toString() + " | ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[HH:mm]");
        for (TimePeriodWeekly period: this.getPeriods()) {
            prefix.append(period.getBeginTime().format(formatter))
                    .append(" - ")
                    .append(period.getEndTime()
                            .format(formatter)).append(" on ")
                    .append(period.getDayOfWeek())
                    .append(", ");
        }
        return prefix.toString().substring(0, prefix.length() - 2);
    }
}
