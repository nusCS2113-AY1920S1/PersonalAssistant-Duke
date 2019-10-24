package seedu.duke.task.command;

import seedu.duke.CommandParseHelper;
import seedu.duke.task.entity.Task;

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
            String day = dayOfWeeks[i].getDisplayName(TextStyle.SHORT, Locale.US);
            if (day.equals(input)) {
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
     * @throws CommandParseHelper.UserInputException an exception when the parsing is failed, most likely due
     *                                               to a wrong format
     */
    private static LocalDateTime convertNaturalDate(String parsedDay, String parsedTiming)
            throws CommandParseHelper.UserInputException {
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
            String day = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);
            while (!day.contains(parsedDay)) {
                dateTime = dateTime.plusDays(1);
                dayOfWeek = dateTime.getDayOfWeek();
                day = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);
            }
        } catch (DateTimeParseException e) {
            throw new CommandParseHelper.UserInputException("Wrong Date Time format");
        }
        return dateTime;
    }

    /**
     * Gets the date in LocalDateTime format from the time string extracted.
     *
     * @param timeString time of the task in string format
     * @return time LocalDateTime format
     * @throws CommandParseHelper.UserInputException an exception when the parsing is failed, most likely due
     *                                               to a wrong format
     */
    public static LocalDateTime getDate(String timeString) throws CommandParseHelper.UserInputException {
        String day = null;
        String timing = null;
        if (!timeString.contains("/") && !timeString.isEmpty()) {
            String timeStr = timeString.substring(0, 1).toUpperCase() + timeString.substring(1).toLowerCase();
            if (timeStr.contains(" ")) {
                String[] tokens = timeStr.split("\\s+", 3);
                day = tokens[0];
                timing = tokens[1];
            } else {
                day = timeStr;
            }
        }
        if (isCorrectNaturalDate(day)) {
            return convertNaturalDate(day, timing);
        } else {
            return Task.parseDate(timeString);
        }
    }
}
