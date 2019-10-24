package Interface;


import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Lookup table that provides date given week and day
 */
public class LookupTable {
    private static Map<String, String> map = new HashMap<>();

    /**
     * Creates a lookup table object and read from Lookup.txt file
     * @throws IOException
     */
    public LookupTable() throws IOException {
        String line;
        // InputStream is = getClass().getResourceAsStream("Lookup.txt");
         //InputStreamReader isr = new InputStreamReader(is);
         //BufferedReader reader = new BufferedReader(isr);
        ClassLoader loader = this.getClass().getClassLoader(); // or YourClass.class.getClassLoader()
        URL resourceUrl = loader.getResource("documents/Lookup.txt");
        BufferedReader reader = null;
        if (resourceUrl != null) {
            reader = new BufferedReader(new FileReader(resourceUrl.getFile()));
        }

        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(":", 2);
            if (parts.length >= 2)
            {
                String key = parts[0].trim();
                String value = parts[1].trim();
                this.map.put(key.trim().toLowerCase(), value);
            }
        }
        reader.close();
        //isr.close();
        //is.close();
    }


    /**
     * Translate week,day to date
     * @param week_day week and day of activity in academic calender
     * @return date of activity
     */
    public String getDate(String week_day){
        String out = map.get(week_day.toLowerCase().trim());//week_day in the format of (week x day x)
        return out;
    }

    public String getDates(String week){
        return map.get(week);
    }

    public String getWeek(String date){
        String out = map.get(date);
        return out;
    }
}
