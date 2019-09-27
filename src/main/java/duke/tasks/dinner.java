package duke.tasks;

/**
 * dinner is a public class that inherits from abstract class meal
 * A dinner object encapsulates the String that express deadline date
 */
public class dinner extends meal {

    /**
     * This is the constructor of dinner object
     * @param description the description of the dinner object
     */
    public dinner(String description, String details) {
        super(description);
        super.type = "D";
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
     * This is the secondary constructor of dinner object for storage parsing
     * @param description the description of the breakfast object
     */
    public dinner(String description, String[] details){
        super(description);
        super.type = "D";
        for (int i = 0; i < details.length; i += 2) {
            nutritionValue.put(details[i], Integer.valueOf(details[i + 1]));
        }
    }

    /**
     * this function overrides the toString() function in meal to represent the full description of a dinner object
     * @return <code>"[D]" + super.toString()</code>
     */
    @Override
    public String toString() {
        return "[D]" + super.toString();
    }
}
