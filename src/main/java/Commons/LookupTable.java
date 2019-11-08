package Commons;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lookup table that provides date given week and day
 */
public class LookupTable {
    private static Map<String, String> map = new HashMap<>();
    private final Logger LOGGER = DukeLogger.getLogger(LookupTable.class);
    private static LookupTable lookupTable;

    /**
     * Creates a lookup table object and read from Lookup.txt file
     * @throws IOException
     */
    private LookupTable() {
        try {
            String line;
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("documents/Lookup.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length >= 2) {
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
     * Translate week,day to date
     * @param week_day week and day of activity in academic calender
     * @return date of activity
     */
    public String getValue(String week_day){
       return map.get(week_day.toLowerCase().trim());//week_day in the format of (week x day x)
    }

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
