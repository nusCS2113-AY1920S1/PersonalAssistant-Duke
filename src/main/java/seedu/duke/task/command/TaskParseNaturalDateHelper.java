package seedu.duke.task.command;

import javafx.scene.text.Text;
import javafx.util.Pair;
import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.task.entity.Task;
import seedu.duke.task.parser.TaskCommandParseHelper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

public class TaskParseNaturalDateHelper {

    /**
     * Checks if the input is a short form for a day of the week.
     *
     * @param input an input to be checked.
     * @return false if the input is not short form or not a day of the week.
     */
    private static boolean isCorrectNaturalDate(String input) {
        DayOfWeek[] dayOfWeeks = DayOfWeek.values();
        for (int i = 0; i < dayOfWeeks.length; i++) {
            String shortForm = dayOfWeeks[i].getDisplayName(TextStyle.SHORT, Locale.US);
            String fullForm = dayOfWeeks[i].getDisplayName(TextStyle.FULL, Locale.US);
            if (fullForm.equals(input) || shortForm.equals(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the input from a string that contains a day of the week to the next nearest date corresponding
     * to the day of the week. If they day has already passed in that week, the next date corresponding to the
     * day will be returned.
     *
     * @param parsedDay    an input that contains the day of the task to be done.
     * @param parsedTiming an input that contains the time of the task to be done.
     * @return dateTime that gives the date and time of the input.
     * @throws CommandParseHelper.CommandParseException an exception when the parsing is failed, most likely
     *                                                  due to a wrong format
     */
    public static LocalDateTime convertNaturalDate(String parsedDay, String parsedTiming)
            throws CommandParseHelper.CommandParseException {
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime;
        try {
            if (parsedTiming == null || parsedTiming.isEmpty()) { //if no timing is inputted, set time as 0000
                dateTime = date.atStartOfDay();
            } else {
                LocalTime timing = LocalTime.parse(parsedTiming, DateTimeFormatter.ofPattern("HHmm"));
                dateTime = date.atTime(timing);
            }
            DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
            String fullForm = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US);
            while (!fullForm.contains(parsedDay)) {
                dateTime = dateTime.plusDays(1);
                dayOfWeek = dateTime.getDayOfWeek();
                fullForm = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US);
            }
        } catch (DateTimeParseException e) {
            throw new CommandParseHelper.CommandParseException("Wrong Date Time format");
        }
        return dateTime;
    }

    /**
     * Gets the date in LocalDateTime format from the time string extracted.
     *
     * @param timeString time of the task in string format
     * @return time LocalDateTime format
     * @throws CommandParseHelper.CommandParseException an exception when the parsing is failed, most likely
     *                                                  due to a wrong format
     */
    public static LocalDateTime getDate(String timeString) throws CommandParseHelper.CommandParseException {
        Pair<String, String> dateTime = TaskCommandParseHelper.checkTimeString(timeString);
        String day = dateTime.getKey();
        String timing = dateTime.getValue();

        if (isCorrectNaturalDate(day)) {
            return convertNaturalDate(day, timing);
        } else {
            return Task.parseDate(timeString);
        }
    }
}
