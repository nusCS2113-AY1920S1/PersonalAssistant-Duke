package Parser;

import Commons.DukeConstants;
import Commons.LookupTable;
import DukeExceptions.DukeInvalidDateTimeException;
import DukeExceptions.DukeInvalidFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class parses all date and time for BetterDuke.
 **/
public class DateTimeParser {
    private static String[] dateTimeStringSplit;
    private static String[] dateStringSplit;
    private static String[] timeStringSplit;
    private static LookupTable lookupTable = LookupTable.getInstance();
    private static SimpleDateFormat eventDateInputFormat = new SimpleDateFormat("dd/MM/yyyy"); //format date for event
    private static SimpleDateFormat eventTimeInputFormat = new SimpleDateFormat("HHmm"); //format time for event
    private static SimpleDateFormat dateOutputFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private static SimpleDateFormat timeOutputFormat = new SimpleDateFormat("hh:mm a");
    private static SimpleDateFormat deadlineInputFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static SimpleDateFormat deadlineDateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
    private static final int LENGTH_OF_TIME_FORMAT = 4;

    /**
     * Parses any date that is tagged with event.
     * @param input The date input
     * @return The String array containing all the dates.
     * @throws ParseException On wrong date format to parse.
     */
    public static String[] EventParse(String input) throws ParseException, DukeInvalidDateTimeException {

        dateTimeStringSplit = input.trim().split(DukeConstants.EVENT_DATE_SPLIT_KEYWORD);
        String weekDate = "";
        dateStringSplit = dateTimeStringSplit[0].trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        weekDate = dateStringSplit[0];
        String dateOfTask = dateTimeStringSplit[0].trim();
        weekDate = WeekFormatParse.acadWeekToString(weekDate, dateOfTask);
        Date date = eventDateInputFormat.parse(weekDate.trim());
        timeStringSplit = dateTimeStringSplit[1].split(DukeConstants.EVENT_TIME_SPLIT_KEYWORD);
        String startTimeOfTask = timeStringSplit[0].trim();
        Date startTime = eventTimeInputFormat.parse(startTimeOfTask);
        String endTimeOfTask = timeStringSplit[1].trim();
        Date endTime = eventTimeInputFormat.parse(endTimeOfTask);
        String dateString = dateOutputFormat.format(date);
        String startTimeString = timeOutputFormat.format(startTime);
        String endTimeString = timeOutputFormat.format(endTime);
        String[] out = {dateString,startTimeString,endTimeString};
        return out;
    }

    /**
     * Parses any date that is tagged with deadline.
     * @param input The date input
     * @return The String array containing all the dates.
     * @throws ParseException On wrong date format to parse.
     */
    public static String[] DeadlineParse(String input) throws ParseException, DukeInvalidDateTimeException {

        dateTimeStringSplit = input.trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        String weekDate = new String();
        String commandSplit = new String();
        dateStringSplit = dateTimeStringSplit[0].trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        weekDate = dateStringSplit[0];
        int dateOfTask =  input.length()-LENGTH_OF_TIME_FORMAT;
        commandSplit = input.substring(0, dateOfTask);
        weekDate = WeekFormatParse.acadWeekToString(weekDate,commandSplit.trim());
        String time = input.substring(dateOfTask).trim();
        weekDate = weekDate + " " + time;
        Date date = deadlineInputFormat.parse(weekDate);
        String dateString = dateOutputFormat.format(date);
        String timeString = timeOutputFormat.format(date);
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
        String[] startDateStringSplit = dateStringSplit[0].trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        String startWeekDate = startDateStringSplit[0].trim();
        String startDateOfTask = dateStringSplit[0].trim();
        startWeekDate = WeekFormatParse.acadWeekToString(startWeekDate, startDateOfTask);
        String[] endDateStringSplit = dateStringSplit[1].trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        String endWeekDate = endDateStringSplit[0].trim();
        String endDateOfTask = dateStringSplit[1].trim();
        endWeekDate = WeekFormatParse.acadWeekToString(endWeekDate, endDateOfTask);
        Date startDate = eventDateInputFormat.parse(startWeekDate);
        Date endDate = eventDateInputFormat.parse(endWeekDate);
        String startDateString = dateOutputFormat.format(startDate);
        String endDateString = dateOutputFormat.format(endDate);
        timeStringSplit = dateTimeStringSplit[1].split(DukeConstants.EVENT_TIME_SPLIT_KEYWORD);
        String startTimeOfTask = timeStringSplit[0].trim();
        Date startTime = eventTimeInputFormat.parse(startTimeOfTask);
        String endTimeOfTask = timeStringSplit[1].trim();
        Date endTime = eventTimeInputFormat.parse(endTimeOfTask);
        String startTimeString = timeOutputFormat.format(startTime);
        String endTimeString = timeOutputFormat.format(endTime);
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
        String[] taskDateTimeStringSplit = dateTimeStringSplit[0].trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        String weekDate = taskDateTimeStringSplit[0].trim();
        int deadlineDateLength = dateTimeStringSplit[0].length()-LENGTH_OF_TIME_FORMAT;
        String deadlineDate = dateTimeStringSplit[0].substring(0, deadlineDateLength);
        deadlineDate = WeekFormatParse.acadWeekToString(weekDate, deadlineDate);
        String time = dateTimeStringSplit[0].substring(deadlineDateLength);
        deadlineDate = deadlineDate + time;
        String[] reminderDateTimeStringSplit = dateTimeStringSplit[1].trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        weekDate = reminderDateTimeStringSplit[0].trim();
        int reminderDateLength = dateTimeStringSplit[1].length()-LENGTH_OF_TIME_FORMAT;
        String reminderDate = dateTimeStringSplit[1].substring(0, reminderDateLength);
        reminderDate = WeekFormatParse.acadWeekToString(weekDate, reminderDate);
        time = dateTimeStringSplit[1].substring(reminderDateLength);
        reminderDate = reminderDate + time;
        Date dateOfTask = deadlineInputFormat.parse(deadlineDate);
        String dateString = dateOutputFormat.format(dateOfTask);
        String timeString = timeOutputFormat.format(dateOfTask);
        String[] dateTime = {dateString, timeString, reminderDate};
        return dateTime;
    }

    public static Date deadlineInputStringToDate(String date) throws ParseException {
        return deadlineInputFormat.parse(date);
    }

    public static Date deadlineTaskStringToDate(String date) throws ParseException {
        return deadlineDateFormat.parse(date);
    }
}
