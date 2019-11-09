package diyeats.model.meal;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Task is a public class that represents the tasks in DIYeats.
 * A task object encapsulates the description of the task, the type of task it is, and whether
 * the task is done.
 */
public class Meal {
    protected String description;
    protected String type = "";
    protected MealType mealType;
    protected boolean isDone;
    protected LocalDate date;
    protected HashMap<String, Integer> nutritionValue = new HashMap<String, Integer>();
    protected String costStr;

    /**
     * This is the constructor of Task object.
     * @param description the description of the task
     * @param date the date the meal is associated with
     * @param details the nutritional data associated with the meal
     */
    public Meal(String description, LocalDate date, HashMap<String, String> details, String costStr) {
        this.description = description.trim();
        this.costStr = costStr.trim();
        //todo: date input can only be accepted at the back of the statement
        if (date != null) {
            this.date = date;
        } else {
            this.date = LocalDate.now();
        }
        if (details.size() != 0) {
            for (String nutrient : details.keySet()) {
                if (!nutrient.equals("date") && !nutrient.equals("cost")) {
                    int value = Integer.parseInt(details.get(nutrient));
                    nutritionValue.put(nutrient, value);
                }
            }
        }
    }

    public Meal(String description, HashMap<String, Integer> nutritionValue) {
        this.date = LocalDate.now();
        this.description = description.trim();
        this.nutritionValue = nutritionValue;
        this.costStr = "0";
    }

    /**
     * This is the no argument constructor for meal task object.
     * used to satisfy requirement for default constructor, not used otherwise
     */
    public Meal() {
    }

    /**
     * This function checks whether the particular task object is done and return the string accordingly.
     * @return <code>[\u2713]</code> if the task is done
     *          <code>[\u2718]</code> if the task is not done
     */
    public String getStatusIcon() {
        return (isDone ? "[YES]" : "[NO]"); //return tick or X symbols
    }

    /**
     * This is a getter for description.
     * @return description of the task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This is a setter for isDone.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * This is a getter for isDone.
     * @return isDone status of the task
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * This is a getter for the type.
     * @return type of the task
     */
    public String getType() {
        return this.type;
    }

    /**
     * This is a getter for the date.
     * @return date of the meal
     */
    public LocalDate getDate() {
        return this.date;
    }

    public String getCostStr() {
        return this.costStr;
    }

    public HashMap<String, Integer> getNutritionalValue() {
        return this.nutritionValue;
    }

    public void addNutritionalValue(String keyStr, int value) {
        this.nutritionValue.put(keyStr, value);
    }

    public int getCalorieValue() {
        return this.nutritionValue.get("calorie");
    }

    public MealType getMealType() {
        return this.mealType;
    }

    /**
     * This function overrides the toString() function in the object class.
     * @return the status icon and the description of the task
     */
    @Override
    public String toString() {
        String temp = "";
        for (String i : nutritionValue.keySet()) {
            temp += i + ":" + nutritionValue.get(i) + " ";
        }
        temp += "cost: " + costStr;
        return "[" + this.type + "]" + this.getStatusIcon() + " " + this.description + " | " + temp;
    }


}