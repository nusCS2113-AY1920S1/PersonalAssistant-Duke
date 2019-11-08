package Parser;

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

    /**
     * Parses any date that is tagged with event.
     * @param input The date input
     * @return The String array containing all the dates.
     * @throws ParseException On wrong date format to parse.
     */
    public static String[] EventParse(String input) throws ParseException, DukeInvalidDateTimeException {

        dateTimeStringSplit = input.trim().split("/from");
        String weekDate = "";
        dateStringSplit = dateTimeStringSplit[0].trim().split(" ");
        weekDate = dateStringSplit[0];
        weekDate = WeekFormatParse.acadWeekToString(weekDate,dateTimeStringSplit[0].trim());
        Date date = eventDateInputFormat.parse(weekDate.trim());
        timeStringSplit = dateTimeStringSplit[1].split("/to");
        Date startTime = eventTimeInputFormat.parse(timeStringSplit[0].trim());
        Date endTime = eventTimeInputFormat.parse(timeStringSplit[1].trim());
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

        dateTimeStringSplit = input.trim().split(" ");
        String weekDate = new String();
        String command_split = new String();
        dateStringSplit = dateTimeStringSplit[0].trim().split(" ");
        weekDate = dateStringSplit[0];
        command_split = input.substring(0,input.length()-4);
        weekDate = WeekFormatParse.acadWeekToString(weekDate,command_split.trim());
        String time = input.substring(input.length()- 4).trim();
        weekDate = weekDate + time;
        Date date = deadlineInputFormat.parse(weekDate);
        String dateString = dateOutputFormat.format(date);
        String timeString = timeOutputFormat.format(date);
        String[] out = {dateString,timeString};
        return out;

    }

    /**
     * Parses any date that is tagged as recurring.
     * @param input The date input
     * @return The String array containing all the dates.
     * @throws ParseException On wrong date format to parse.
     */
    public static String[] recurringEventParse(String input) throws ParseException, DukeInvalidDateTimeException {

        dateTimeStringSplit = input.trim().split("/from");
        dateStringSplit = dateTimeStringSplit[0].split("/to");
        String[] startDateStringSplit = dateStringSplit[0].trim().split(" ");
        String startWeekDate = startDateStringSplit[0].trim();

        startWeekDate = WeekFormatParse.acadWeekToString(startWeekDate, dateStringSplit[0].trim());
        String[] endDateStringSplit = dateStringSplit[1].trim().split(" ");
        String endWeekDate = endDateStringSplit[0].trim();

        endWeekDate = WeekFormatParse.acadWeekToString(endWeekDate, dateStringSplit[1].trim());
        Date startDate = eventDateInputFormat.parse(startWeekDate);
        Date endDate = eventDateInputFormat.parse(endWeekDate);
        String startDateString = dateOutputFormat.format(startDate);
        String endDateString = dateOutputFormat.format(endDate);

        timeStringSplit = dateTimeStringSplit[1].split("/to");
        Date startTime = eventTimeInputFormat.parse(timeStringSplit[0].trim());
        Date endTime = eventTimeInputFormat.parse(timeStringSplit[1].trim());
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

        dateTimeStringSplit = input.trim().split(" /on ");
        String[] taskDateTimeStringSplit = dateTimeStringSplit[0].trim().split(" ");
        String weekDate = taskDateTimeStringSplit[0].trim();
        String deadlineDate = dateTimeStringSplit[0].substring(0, dateTimeStringSplit[0].length() - 4);
        deadlineDate = WeekFormatParse.acadWeekToString(weekDate , deadlineDate);
        String time = dateTimeStringSplit[0].substring(dateTimeStringSplit[0].length()- 4);
        deadlineDate = deadlineDate + time;
        String[] reminderDateTimeStringSplit = dateTimeStringSplit[1].trim().split(" ");
        weekDate = reminderDateTimeStringSplit[0].trim();
        String reminderDate = dateTimeStringSplit[1].substring(0, dateTimeStringSplit[1].length() - 4);
        reminderDate = WeekFormatParse.acadWeekToString(weekDate, reminderDate);
        time = dateTimeStringSplit[1].substring(dateTimeStringSplit[1].length()- 4);
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
