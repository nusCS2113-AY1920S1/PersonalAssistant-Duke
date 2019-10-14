package duchess.model.calendar;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalendarEntry {
    private Map<LocalTime, List<String>> entries;

    /**
     * Initialises a TreeMap with keys of hourly increment from 0000 to 2300, and null values.
     */
    public CalendarEntry() {
        this.entries = new TreeMap<>();
        for (LocalTime counter = LocalTime.MIN; counter.isBefore(LocalTime.MIDNIGHT); counter = counter.plusHours(1)) {
            List<String> strList = new ArrayList<>();
            this.entries.put(counter, strList);
        }
    }
}
