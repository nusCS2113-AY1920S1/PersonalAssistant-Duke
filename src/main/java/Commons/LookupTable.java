package Commons;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lookup table that provides date given week and day
 */
public class LookupTable {
    private static Map<String, String> map = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(LookupTable.class.getName());

    /**
     * Creates a lookup table object and read from Lookup.txt file
     * @throws IOException
     */
    public LookupTable() {
        try {
            String line;
            // InputStream is = getClass().getResourceAsStream("Lookup.txt");
            //InputStreamReader isr = new InputStreamReader(is);
            //BufferedReader reader = new BufferedReader(isr);
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + "data" + File.separator + "Lookup.txt"));

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length >= 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    this.map.put(key.trim().toLowerCase(), value);
                }
            }
            reader.close();
            //isr.close();
            //is.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
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

}
