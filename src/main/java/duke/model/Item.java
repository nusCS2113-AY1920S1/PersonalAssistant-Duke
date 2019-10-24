package duke.model;

/**
 * item is a public class that extends from meal.
 */
public class Item extends Meal {

    /**
     * This is the constructor of item object, which is used to store default meal info.
     * @param description the description of the item object
     */
    public Item(String description, String details) {
        super(description, details);
        super.type = "S";
    }

    /**
     * This is the secondary constructor of item object for storage parsing.
     * @param description the description of the item object
     */
    public Item(String description, String[] details) {
        super.description = description;
        for (int i = 0; i < details.length; i += 2) {
            super.nutritionValue.put(details[i], Integer.valueOf(details[i + 1]));
        }
    }

    /**
     * This function overrides the toString() function in the object class.
     * @return the description and info of the meal
     */
    @Override
    public String toString() {
        String temp = "";
        for (String i : nutritionValue.keySet()) {
            temp += i + ":" + nutritionValue.get(i) + " ";
        }
        return this.description + " | " + temp;
    }
}
