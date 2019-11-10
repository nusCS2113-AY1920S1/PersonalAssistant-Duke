package Commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Lookup table that provides date given week and day.
 */
public class LookupTable {
    private static Map<String, String> map = new HashMap<>();
    private final Logger LOGGER = DukeLogger.getLogger(LookupTable.class);
    private static LookupTable lookupTable;
    private static final int LENGTH_OF_LINE = 2;

    /**
     * Creates a lookup table object and read from Lookup.txt file.
     * @throws IOException when the Lookup.txt is not found
     */
    private LookupTable() {
        try {
            String line;
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("documents/Lookup.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length >= LENGTH_OF_LINE) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    this.map.put(key.trim().toLowerCase(), value);
                }
            }
            reader.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            LOGGER.severe("Lookup.txt not found");
        }
    }

    /**
     * Translate week,day to date.
     * @param weekAndDay week and day of activity in academic calender
     * @return date of activity
     */
    public String getValue(String weekAndDay) {
        return map.get(weekAndDay.toLowerCase().trim());
    }

    /**
     * Creates a single instance of LookupTable object.
     */
    public static LookupTable getInstance() {
        if (lookupTable == null) {
            synchronized (LookupTable.class) {
                if (lookupTable == null) {
                    lookupTable = new LookupTable();
                }
            }
        }
        return lookupTable;
    }
}
