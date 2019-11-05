package Parser;

import Commons.LookupTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeekFormatParse {
    private static LookupTable LT = new LookupTable();

    public static String acadWeekToString (String weekDate, String date) {
        if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")) {

            weekDate = LT.getValue(date) + " ";
        } else {
            weekDate = date;
        }
        return weekDate;
    }

}
