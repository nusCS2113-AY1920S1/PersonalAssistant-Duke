package chronologer.parser;

import java.util.HashMap;

/**
 * Contains the dates of the last days of the semester.
 * 
 * @author Hans kurnia
 * @version 1.0
 */
public class LastDay {
    private static HashMap<String, String> lastDates = new HashMap<String, String>();

    public LastDay() {

    }

    /**
     * Gets the last day's date of the semester.
     * 
     * @param dayFromUser day of the week
     * @return date of the last day
     */
    public static String getDate(String dayFromUser) {
        lastDates.put("MON", "11/11/2019 ");
        lastDates.put("TUE", "12/11/2019 ");
        lastDates.put("WED", "13/11/2019 ");
        lastDates.put("THU", "14/11/2019 ");
        lastDates.put("FRI", "15/11/2019 ");
        lastDates.put("SAT", "16/11/2019 ");
        lastDates.put("SUN", "17/11/2019 ");

        String formattedDay = formatDay(dayFromUser);
        return lastDates.get(formattedDay);
    }

    private static String formatDay(String dayFromUser) {
        return dayFromUser.substring(0, 3);
    }
}