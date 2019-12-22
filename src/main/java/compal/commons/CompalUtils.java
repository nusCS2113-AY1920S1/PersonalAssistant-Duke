package compal.commons;

import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CompalUtils {
    //@@author yueyeah
    /**
     * Converts a date string to a Date object.
     *
     * @param dateStr The date string to be converted.
     * @return The date string in the form of a Date object.
     */
    public static Date stringToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //@@author yueyeah
    /**
     * Converts a Date object to a date string. Correct type for creating a Task object.
     *
     * @param date The date in the form of a Date object.
     * @return The date in the form of a String object.
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    //@@author yueyeah
    /**
     * Finds out if a start time is before an end time. If the start time is after end time,
     * it is likely that the end time is on the next day.
     *
     * @param startTimeString The start time in the form of a String object.
     * @param endTimeString The end time in the form of a String object.
     * @return True if the start time is before the end time, False if not.
     */
    public static boolean isTimeInSequence(String startTimeString, String endTimeString) {
        int startTimeInt = Integer.valueOf(startTimeString);
        int endTimeInt = Integer.valueOf(endTimeString);
        return (startTimeInt < endTimeInt);
    }

    //@@author yueyeah
    /**
     * Increases date by week, to assign event slots for each week.
     *
     * @param initialDate The date to increment
     * @return Final date one week later than initialDate
     */
    public static Date incrementDateByDays(Date initialDate, int numOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(Calendar.DATE, numOfDays);
        Date finalDate = calendar.getTime();
        return finalDate;
    }

    /**
     * Read the raw json from the json url given.
     *
     * @param url json url.
     * @return Raw Json.
     * @throws IOException To print out the entire stack trace of the exception.
     * @throws JSONException To print out the entire stack trace of the exception.
     */
    public static JSONObject readJsonFromNusmods(String url) throws IOException {
        InputStream inputStream = new URL(url).openStream();
        JSONObject moduleJson = new JSONObject();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                    Charset.defaultCharset()));
            StringBuilder stringBuilder = new StringBuilder();
            int currChar;
            while (true) {
                currChar = bufferedReader.read();
                if (currChar == -1) {
                    break;
                }
                stringBuilder.append((char) currChar);
            }
            moduleJson = new JSONObject(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return moduleJson;
    }
}
