package chronologer.parser;

import java.util.HashMap;

public class LastDay {
    private static HashMap<String, String> lastDates = new HashMap<String, String>();
    
    public LastDay() {
    
    }
    
    public static String getDate(String dayFromUser) {
        lastDates.put("MONDAYS","11/11/2019 ");
        lastDates.put("TUESDAYS","12/11/2019 ");
        lastDates.put("WEDNESDAYS","13/11/2019 ");
        lastDates.put("THURDAYS","14/11/2019 ");
        lastDates.put("FRIDAYS","15/11/2019 ");
        lastDates.put("SATURDAYS","16/11/2019 ");
        lastDates.put("SUNDAYS","17/11/2019 ");
        return lastDates.get(dayFromUser);
    }
}