package Parser;

import Interface.LookupTable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a Date time
 **/

public class DateTimeParser {
    private static String[] dateTimeStringSplit;
    private static String[] dateStringSplit;
    private static String[] timeStringSplit;
    private static LookupTable LT;

    private SimpleDateFormat eventDateInputFormat = new SimpleDateFormat("dd/MM/yyyy"); //format date for event
    private SimpleDateFormat eventTimeInputFormat = new SimpleDateFormat("HHmm"); //format time for event
    private SimpleDateFormat dateOutputFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private SimpleDateFormat timeOutputFormat = new SimpleDateFormat("hh:mm a");
    private SimpleDateFormat deadlineInputFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");


    static {
        try {
            LT = new LookupTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] EventParse(String input) throws ParseException {
        // date from time /to time
        dateTimeStringSplit = input.split("/from");
        //dateTimeStringSplit[0] is "date" or "week X day", dateTimeStringSplit[1] is "time /to time"
        String weekDate = "";
        dateStringSplit = dateTimeStringSplit[0].trim().split(" "); //dateStringSplit[0] can be week
        weekDate = dateStringSplit[0];
        if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")){
            weekDate = LT.getValue(dateTimeStringSplit[0].trim());
        } else {
            weekDate = dateTimeStringSplit[0].trim();
        }
        Date date = eventDateInputFormat.parse(weekDate.trim());
        timeStringSplit = dateTimeStringSplit[1].split("/to");
        Date startTime = eventTimeInputFormat.parse(timeStringSplit[0].trim());
        Date endTime = eventTimeInputFormat.parse(timeStringSplit[1].trim());
        String dateString = dateOutputFormat.format(date);
        String startTimeString = timeOutputFormat.format(startTime);
        String endTimeString = timeOutputFormat.format(endTime);
        String[] out = {dateString,startTimeString,endTimeString};

        return  out;
    }

    public String[] DeadlineParse(String input) throws ParseException {
        // date time
        dateTimeStringSplit = input.split(" ");
        String weekDate = "";
        dateStringSplit = dateTimeStringSplit[0].trim().split(" ");
        weekDate = dateStringSplit[0];
        if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")){
            weekDate = input.substring(0,input.length()- 4); // week x day y
            String time = input.substring(input.length()- 4); // time E.g 0300
            weekDate = LT.getValue(weekDate) + " " + time;
        } else {
            weekDate = dateTimeStringSplit[0];
        }
        Date date = deadlineInputFormat.parse(weekDate);
        String dateString = dateOutputFormat.format(date);
        String timeString = timeOutputFormat.format(date);
        String[] out = {dateString,timeString};
        return out;

    }

    public String RecurParse(String input) throws ParseException {
        //1/10/2019 /to 15/11/2019 /from 1500 /to 1700"
        dateTimeStringSplit = input.split("/from"); //dateTimeStringSplit[0] = startDate to endDate
        dateStringSplit = dateTimeStringSplit[0].split("/to"); //dateStringSplit[0] = startDate (2/2/2019 or week X day)
        String[] startDateStringSplit = dateStringSplit[0].trim().split(" "); //startDateStringSplit[0] = week
        String startWeekDate = startDateStringSplit[0].trim();
        if (startWeekDate.equalsIgnoreCase("reading") || startWeekDate.equalsIgnoreCase("exam")
                || startWeekDate.equalsIgnoreCase("week") || startWeekDate.equalsIgnoreCase("recess")) {
            startWeekDate = LT.getValue(dateStringSplit[0].trim());
        } else {
            startWeekDate = dateStringSplit[0].trim();
        }
        String[] endDateStringSplit = dateStringSplit[1].trim().split(" ");
        String endWeekDate = endDateStringSplit[0].trim();
        if (endWeekDate.equalsIgnoreCase("reading") || endWeekDate.equalsIgnoreCase("exam")
                || endWeekDate.equalsIgnoreCase("week") || endWeekDate.equalsIgnoreCase("recess")) {
            endWeekDate = LT.getValue(dateStringSplit[1].trim());
        } else {
            endWeekDate = dateStringSplit[1].trim();
        }
        Date startDate = eventDateInputFormat.parse(startWeekDate);
        Date endDate = eventDateInputFormat.parse(endWeekDate);

        timeStringSplit = dateTimeStringSplit[1].split("/to"); //timeStringSplit[0] = startTime
        Date startTime = eventTimeInputFormat.parse(timeStringSplit[0].trim());
        Date endTime = eventTimeInputFormat.parse(timeStringSplit[1].trim());
        String startTimeString = timeOutputFormat.format(startTime);
        String endTimeString = timeOutputFormat.format(endTime);
        return ;
        //return new RecurringCommand(split[0].trim(),startDate, endDate, startTimeString, endTimeString);
    }
    public String RemindParse(String input) throws ParseException {
        // week 9 fri 1500 /to week 9 thu 1500"
        dateTimeStringSplit = input.split("/to"); //dateTimeStringSplit[0] = week 9 fri 1500
        String[] taskDateTimeStringSplit = dateTimeStringSplit[0].trim().split(" ");
        String weekDate = taskDateTimeStringSplit[0].trim();
        if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")) {
            weekDate = dateTimeStringSplit[0].substring(0,dateTimeStringSplit[0].length()- 4);
            String time = dateTimeStringSplit[0].substring(dateTimeStringSplit[0].length()- 4);
            weekDate = LT.getValue(weekDate) + " " + time;
        } else {
            weekDate = dateTimeStringSplit[0];
        }

        String[] reminderDateTimeStringSplit = dateTimeStringSplit[1].trim().split(" "); //dateTimeStringSplit[1] = week 9 thu 1500
        String reminderDate = reminderDateTimeStringSplit[0].trim();
        if (reminderDate.equalsIgnoreCase("reading") || reminderDate.equalsIgnoreCase("exam")
                || reminderDate.equalsIgnoreCase("week") || reminderDate.equalsIgnoreCase("recess")) {
            reminderDate = dateTimeStringSplit[1].substring(0,dateTimeStringSplit[1].length()- 4);
            String time = dateTimeStringSplit[1].substring(dateTimeStringSplit[1].length()- 4);
            reminderDate = LT.getValue(reminderDate) + " " + time;
        } else {
            reminderDate = dateTimeStringSplit[1];
        }

        Date dateOfTask = deadlineInputFormat.parse(weekDate);
        Date dateOfReminder = deadlineInputFormat.parse(reminderDate);
        String dateString = dateOutputFormat.format(dateOfTask);
        String timeString = timeOutputFormat.format(dateOfTask);
        return ;
        //return new RemindCommand(new Deadline(description, dateString, timeString), dateOfReminder, set);
    }

}
