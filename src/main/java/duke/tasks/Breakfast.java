package duke.tasks;

/**
 * breakfast is a public class that extends from meal.
 */
public class Breakfast extends Meal {

    /**
     * This is the constructor of breakfast object.
     * @param description the description of the breakfast object
     */
    public Breakfast(String description, String details) {
        super(description);
        super.type = "B";
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
     * This is the secondary constructor of breakfast object for storage parsing.
     * @param description the description of the breakfast object
     */
    public Breakfast(String description, String[] details) {
        super(description);
        super.type = "B";
        for (int i = 0; i < details.length; i += 2) {
            nutritionValue.put(details[i], Integer.valueOf(details[i + 1]));
        }
    }

    /**
     * this function overrides the toString() function in meal to represent the full description of a breakfast object.
     * @return <code>"[B]" + super.toString()</code>
     */
    @Override
    public String toString() {
        return "[B]" + super.toString();
    }
}
