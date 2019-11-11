package diyeats.model.meal;

import java.time.LocalDate;
import java.util.HashMap;

//@@author Fractalisk

/**
 * Task is a public class that represents the tasks in DIYeats.
 * A task object encapsulates the description of the task, the type of task it is, and whether
 * the task is done.
 */
public class Meal implements Cloneable {
    protected String description;
    protected String type = "";
    protected MealType mealType;
    protected boolean isDone;
    protected LocalDate date;
    protected HashMap<String, Integer> nutritionValue = new HashMap<String, Integer>();
    protected String costStr;

    /**
     * This is the constructor of Meal object.
     * @param description the description of the meal
     * @param date the date the meal is associated with
     * @param nutritionValue the nutritional data associated with the meal
     */
    public Meal(String description, LocalDate date, HashMap<String, String> nutritionValue, String costStr) {
        this.description = description.trim();
        this.costStr = costStr.trim();
        if (date != null) {
            this.date = date;
        } else {
            this.date = LocalDate.now();
        }
        if (nutritionValue.size() != 0) {
            for (String nutrient : nutritionValue.keySet()) {
                if (!nutrient.equals("date") && !nutrient.equals("cost")) {
                    int value = Integer.parseInt(nutritionValue.get(nutrient));
                    this.nutritionValue.put(nutrient, value);
                }
            }
        }
    }

    /**
     * This is a constructor of Meal object used with suggestMeal.
     * @param description the description of the meal
     * @param nutritionValue the nutritional data associated with the meal
     */
    public Meal(String description, HashMap<String, Integer> nutritionValue) {
        this.date = LocalDate.now();
        this.description = description.trim();
        this.nutritionValue = nutritionValue;
        this.costStr = "0";
    }

    /**
     * This is the no argument constructor for meal object.
     * used to satisfy requirement for default constructor
     */
    public Meal() {
    }

    /**
     * This function checks whether the particular meal object is done and return the string accordingly.
     * @return <code>[YES]</code> if the meal is done
     *          <code>[NO]</code> if the meal is not done
     */
    public String getStatusIcon() {
        return (isDone ? "[YES]" : "[NO]");
    }

    /**
     * This is a getter for description.
     * @return description of the meal
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
     * This is a setter for isDone.
     */
    public void markAsUnDone() {
        this.isDone = false;
    }

    /**
     * This is a getter for isDone.
     * @return isDone status of the meal
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * This is a getter for the type.
     * @return type of the meal
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

    /**
     * This is a getter for the cost of the meal.
     * @return cost of the meal
     */
    public String getCostStr() {
        return this.costStr;
    }

    /**
     * This is a getter for the nutritional value of a meal.
     * @return A HashMap encapsulating the nutritional value of the meal
     */
    public HashMap<String, Integer> getNutritionalValue() {
        return this.nutritionValue;
    }

    /**
     * Adds nutritional info to a meal.
     * @param keyStr name of nutrient
     * @param value amount of nutrient
     */
    public void addNutritionalValue(String keyStr, int value) {
        this.nutritionValue.put(keyStr, value);
    }

    /**
     * This is a getter for the value associated with "calorie".
     * @return nutrtional value of "calorie"
     */
    public int getCalorieValue() {
        return this.nutritionValue.get("calorie");
    }

    /**
     * This is a getter for the meal type.
     * @return the meal type
     */
    public MealType getMealType() {
        return this.mealType;
    }

    /**
     * Setter for the meal type and meal string.
     */
    public void setMealType(MealType mealType) {
        this.mealType = mealType;
        switch (this.mealType) {
            case BREAKFAST:
                this.type = "B";
                break;
            case LUNCH:
                this.type = "L";
                break;
            case DINNER:
                this.type = "D";
                break;
            default:
                this.type = "L";
                break;
        }
    }

    /**
     * This function overrides the toString() function in the object class.
     * @return the status icon and the description of the meal
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

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}