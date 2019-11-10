package chronologer.parser;

import java.time.DayOfWeek;
import java.util.HashMap;

import chronologer.exception.ChronologerException;
import chronologer.exception.MyLogger;
import chronologer.ui.UiMessageHandler;

//@@author hanskw4267
/**
 * Contains the dates of the last days of the semester.
 * 
 * @author Hans kurnia
 * @version 1.0
 */
public class LastDay {
    private static HashMap<String, DayOfWeek> days = new HashMap<String, DayOfWeek>();
    private static MyLogger logger = new MyLogger("chronologer.parser.LastDay", "ParserErrors");

    public LastDay() {

    }

    /**
     * Gets the last day's date of the semester.
     * 
     * @param dayFromUser day of the week
     * @return date of the last day
     * @throws ChronologerException invalid day was given
     */
    public static DayOfWeek getDay(String dayFromUser) throws ChronologerException {
        days.put("MON", DayOfWeek.MONDAY);
        days.put("TUE", DayOfWeek.TUESDAY);
        days.put("WED", DayOfWeek.WEDNESDAY);
        days.put("THU", DayOfWeek.THURSDAY);
        days.put("FRI", DayOfWeek.FRIDAY);
        days.put("SAT", DayOfWeek.SATURDAY);
        days.put("SUN", DayOfWeek.SUNDAY);

        String formattedDay = formatDay(dayFromUser);
        return days.get(formattedDay);
    }

    private static String formatDay(String dayFromUser) throws ChronologerException {
        try {
            String formattedDay = dayFromUser.substring(0, 3);
            return formattedDay;
        } catch (IndexOutOfBoundsException e) {
            logger.writeLog(e.toString(), "chronologer.parser.LastDay", dayFromUser);
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }
}