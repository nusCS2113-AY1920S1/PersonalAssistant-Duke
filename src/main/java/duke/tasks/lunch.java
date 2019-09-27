package duke.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * lunch is a public class that inherits form abstract class meal.
 * A lunch object encapsulates the String that expresses the duration of the lunch
 */
public class lunch extends meal {

    /**
     * This is the constructor of lunch object.
     * @param description the description of the lunch object
     */
    public lunch(String description, String details) {
        super(description);
        super.type = "L";
        if (details.trim().length() != 0) {
            String[] chunk = details.split("/");
            for (String data : chunk) {
                if (data.trim().length() != 0) {
                    String[] partitionedData = data.split(" ", 2);
                    String nutrient = partitionedData[0];
                    int value = Integer.valueOf(partitionedData[1].trim());
                    nutritionValue.put(nutrient, value);
                }
            }
        }
    }

    /**
     * This is the secondary constructor of lunch object for storage parsing.
     * @param description the description of the lunch object
     */
    public lunch(String description, String[] details){
        super(description);
        super.type = "L";
        for (int i = 0; i < details.length; i += 2) {
            nutritionValue.put(details[i], Integer.valueOf(details[i + 1]));
        }
    }

    /**
     * this function overrides the toString() function in meal to represent the full description of a lunch object.
     * @return <code>"[L]" + super.toString()</code>
     */
    @Override
    public String toString() {
        return "[L]" + super.toString();
    }
}