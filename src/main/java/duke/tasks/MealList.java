package duke.tasks;

import duke.exceptions.DukeException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * TaskList is a public class that represents the list of meals under DIYeats.
 * A MealList object encapsulates the ArrayList of meals.
 */
public class MealList {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar calendarDate = Calendar.getInstance();
    private String currentDate = dateFormat.format(calendarDate.getTime());
    private HashMap<String, ArrayList<Meal>> mealTracker = new HashMap<>();

    /**
     * This is the constructor of TaskList object.
     */
    public MealList(HashMap<String, ArrayList<Meal>> mealTracker) {
        this.mealTracker = mealTracker;
    }

    /**
     * This is the constructor of TaskList object if there is no argument.
     * The TaskList object will initialise a new empty arraylist of task.
     */
    public MealList() {
    }


    /**
     * This function is used to delete the task of a particular index.
     * @param date date of the meal to be deleted.
     * @param index the index of task to be deleted.
     * @return Returns the meal that was deleted.
     */
    public Meal delete(String date, int index) {
        Meal deletedMeal = this.mealTracker.get(date).get(index - 1);
        this.mealTracker.get(date).remove(index - 1);
        return deletedMeal;
    }

    public void deleteAllMealsOnDate(String dateStr) {
        if (mealTracker.containsKey(dateStr)) {
            this.mealTracker.get(dateStr).clear();
        }
    }

    public void addMeal(Meal meal) {
        String dateStr = meal.getDate();
        if (!mealTracker.containsKey(dateStr)) {
            mealTracker.put(dateStr, new ArrayList<Meal>());
        }
        mealTracker.get(dateStr).add(meal);
    }

    /**
     * Update existing meal from mealList based on date and description given.
     * @param newMeal Meal to be updated.
     * @return Returns updated meal information.
     * @throws DukeException Exception thrown if meal description or date not found in current list.
     */
    public Meal updateMeal(Meal newMeal) throws DukeException {
        String dateStr = newMeal.getDate();
        if (mealTracker.containsKey(dateStr)) {
            ArrayList<Meal> meals = getMeals(dateStr);
            for (int idx = 0; idx < meals.size(); idx++) {
                Meal currMeal = meals.get(idx);
                if (isSameMeal(currMeal, newMeal)) {
                    HashMap<String, Integer> newNutrition = newMeal.getNutritionalValue();
                    for (String keyStr : newNutrition.keySet()) {
                        currMeal.addNutritionalValue(keyStr, newNutrition.get(keyStr));
                    }
                    meals.set(idx, currMeal);
                    newMeal = currMeal;
                    mealTracker.replace(dateStr, meals);
                    return newMeal;
                }
            }
            throw new DukeException("No meal matches description of " + newMeal.getDescription() + " on " + newMeal.getDate());
        }
        else {
            throw new DukeException("No meal found on " + newMeal.getDate());
        }
    }

    public boolean isSameMeal(Meal meal1, Meal meal2) {
        return meal1.getDescription().equals(meal2.getDescription()) && meal1.getDate().equals(meal2.getDate());
    }

    public ArrayList<Meal> getMeals(String inputDate) {
        if (mealTracker.containsKey(inputDate)) {
            return mealTracker.get(inputDate);
        } else {
            mealTracker.put(inputDate, new ArrayList<>());
            return mealTracker.get(inputDate);
        }
    }

    public HashMap<String, ArrayList<Meal>> getMealTracker() {
        return mealTracker;
    }

    public boolean hasMealsOnDate(String date) {
        ArrayList<Meal> temp = getMeals(date);
        return temp.size() > 0;
    }
}
