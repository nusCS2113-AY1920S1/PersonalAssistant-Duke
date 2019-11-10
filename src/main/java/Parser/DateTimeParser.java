package Parser;


import Commons.DukeConstants;
import DukeExceptions.DukeInvalidDateTimeException;
import java.text.ParseException;
import java.util.Date;

/**
 * This class parses all date and time for BetterDuke.
 **/
public class DateTimeParser {
    private static String[] dateTimeStringSplit;
    private static String[] dateStringSplit;
    private static String[] timeStringSplit;



    /**
     * Parses any date that is tagged with event.
     * @param input The date input
     * @return The String array containing all the dates.
     * @throws ParseException On wrong date format to parse.
     */
    public static String[] eventParse(String input) throws ParseException, DukeInvalidDateTimeException {

        dateTimeStringSplit = input.trim().split(DukeConstants.EVENT_DATE_SPLIT_KEYWORD);
        String weekDate = DukeConstants.NO_FIELD;
        dateStringSplit = dateTimeStringSplit[0].trim().split(DukeConstants.BLANK_SPACE);
        weekDate = dateStringSplit[0];
        String dateOfTask = dateTimeStringSplit[0].trim();
        weekDate = WeekFormatParse.acadWeekToString(weekDate, dateOfTask);
        Date date = DukeConstants.EVENT_DATE_INPUT_FORMAT.parse(weekDate.trim());
        timeStringSplit = dateTimeStringSplit[1].split(DukeConstants.EVENT_TIME_SPLIT_KEYWORD);
        String startTimeOfTask = timeStringSplit[0].trim();
        Date startTime = DukeConstants.EVENT_TIME_INPUT_FORMAT.parse(startTimeOfTask);
        String endTimeOfTask = timeStringSplit[1].trim();
        Date endTime = DukeConstants.EVENT_TIME_INPUT_FORMAT.parse(endTimeOfTask);
        String dateString = DukeConstants.DAY_DATE_FORMAT.format(date);
        String startTimeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(startTime);
        String endTimeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(endTime);
        String[] out = {dateString,startTimeString,endTimeString};
        return out;
    }

    /**
     * Parses any date that is tagged with deadline.
     * @param input The date input
     * @return The String array containing all the dates.
     * @throws ParseException On wrong date format to parse.
     */
    public static String[] deadlineParse(String input) throws ParseException, DukeInvalidDateTimeException {

        dateTimeStringSplit = input.trim().split(DukeConstants.BLANK_SPACE);
        String weekDate;
        String commandSplit;
        dateStringSplit = dateTimeStringSplit[0].trim().split(DukeConstants.BLANK_SPACE);
        weekDate = dateStringSplit[0];
        int dateOfTask =  input.length() - DukeConstants.LENGTH_OF_TIME_FORMAT;
        commandSplit = input.substring(0, dateOfTask);
        weekDate = WeekFormatParse.acadWeekToString(weekDate,commandSplit.trim());
        String time = input.substring(dateOfTask).trim();
        weekDate = weekDate.trim() + DukeConstants.BLANK_SPACE + time;
        Date date = DukeConstants.DEADLINE_INPUT_FORMAT.parse(weekDate);
        String dateString = DukeConstants.DAY_DATE_FORMAT.format(date);
        String timeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(date);
        String[] out = {dateString, timeString};
        return out;
    }

    /**
     * Parses any date that is tagged as recurring.
     * @param input The date input
     * @return The String array containing all the dates.
     * @throws ParseException On wrong date format to parse.
     */
    public static String[] recurringEventParse(String input) throws ParseException, DukeInvalidDateTimeException {

        dateTimeStringSplit = input.trim().split(DukeConstants.EVENT_DATE_SPLIT_KEYWORD);
        dateStringSplit = dateTimeStringSplit[0].split(DukeConstants.EVENT_TIME_SPLIT_KEYWORD);
        String[] startDateStringSplit = dateStringSplit[0].trim().split(DukeConstants.BLANK_SPACE);
        String startWeekDate = startDateStringSplit[0].trim();
        String startDateOfTask = dateStringSplit[0].trim();
        startWeekDate = WeekFormatParse.acadWeekToString(startWeekDate, startDateOfTask);
        String[] endDateStringSplit = dateStringSplit[1].trim().split(DukeConstants.BLANK_SPACE);
        String endWeekDate = endDateStringSplit[0].trim();
        String endDateOfTask = dateStringSplit[1].trim();
        endWeekDate = WeekFormatParse.acadWeekToString(endWeekDate, endDateOfTask);
        Date startDate = DukeConstants.EVENT_DATE_INPUT_FORMAT.parse(startWeekDate);
        Date endDate = DukeConstants.EVENT_DATE_INPUT_FORMAT.parse(endWeekDate);
        String startDateString = DukeConstants.DAY_DATE_FORMAT.format(startDate);
        String endDateString = DukeConstants.DAY_DATE_FORMAT.format(endDate);
        timeStringSplit = dateTimeStringSplit[1].split(DukeConstants.EVENT_TIME_SPLIT_KEYWORD);
        String startTimeOfTask = timeStringSplit[0].trim();
        Date startTime = DukeConstants.EVENT_TIME_INPUT_FORMAT.parse(startTimeOfTask);
        String endTimeOfTask = timeStringSplit[1].trim();
        Date endTime = DukeConstants.EVENT_TIME_INPUT_FORMAT.parse(endTimeOfTask);
        String startTimeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(startTime);
        String endTimeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(endTime);
        String[] out = {startDateString, endDateString, startTimeString, endTimeString};
        return out;
    }

    /**
     * Parses any date that is tagged with reminder.
     * @param input The date input
     * @return The String array containing all the dates.
     * @throws ParseException On wrong date format to parse.
     */
    public static String[] remindDateParse(String input) throws ParseException, DukeInvalidDateTimeException {

        dateTimeStringSplit = input.trim().split(DukeConstants.REMIND_DATE_DEADLINE_DATE_SPLIT_KEYWORD);
        String[] taskDateTimeStringSplit = dateTimeStringSplit[0].trim().split(DukeConstants.BLANK_SPACE);
        String weekDate = taskDateTimeStringSplit[0].trim();
        int deadlineDateLength = dateTimeStringSplit[0].length() - DukeConstants.LENGTH_OF_TIME_FORMAT;
        String deadlineDate = dateTimeStringSplit[0].substring(0, deadlineDateLength);
        deadlineDate = WeekFormatParse.acadWeekToString(weekDate, deadlineDate);
        String time = dateTimeStringSplit[0].substring(deadlineDateLength);
        deadlineDate = deadlineDate + time;
        String[] reminderDateTimeStringSplit = dateTimeStringSplit[1].trim().split(DukeConstants.BLANK_SPACE);
        weekDate = reminderDateTimeStringSplit[0].trim();
        int reminderDateLength = dateTimeStringSplit[1].length() - DukeConstants.LENGTH_OF_TIME_FORMAT;
        String reminderDate = dateTimeStringSplit[1].substring(0, reminderDateLength);
        reminderDate = WeekFormatParse.acadWeekToString(weekDate, reminderDate);
        time = dateTimeStringSplit[1].substring(reminderDateLength);
        reminderDate = reminderDate + time;
        Date dateOfTask = DukeConstants.DEADLINE_INPUT_FORMAT.parse(deadlineDate);
        String dateString = DukeConstants.DAY_DATE_FORMAT.format(dateOfTask);
        String timeString = DukeConstants.TWELVE_HOUR_TIME_FORMAT.format(dateOfTask);
        String[] dateTime = {dateString, timeString, reminderDate};
        return dateTime;
    }

    public static Date deadlineInputStringToDate(String date) throws ParseException {
        return DukeConstants.DEADLINE_INPUT_FORMAT.parse(date);
    }

    public static Date deadlineTaskStringToDate(String date) throws ParseException {
        return DukeConstants.DEADLINE_DATE_FORMAT.parse(date);
    }
}
