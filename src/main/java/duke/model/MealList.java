package duke.model;

import duke.commons.exceptions.DukeException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * MealList is a public class that represents the list of meals under DIYeats.
 * A MealList object encapsulates the ArrayList of meals.
 */
public class MealList {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar calendarDate = Calendar.getInstance();
    private String currentDate = dateFormat.format(calendarDate.getTime());
    private HashMap<String, ArrayList<Meal>> mealTracker = new HashMap<>();
    private HashMap<String, HashMap<String, Integer>> storedItems = new HashMap<>();
    private Goal goal = null;

    /**
     * This is the constructor of MealList object if there is no argument.
     * The MealList object will initialise a new empty arraylist of meals.
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

    /**
     * This function is used to mark done the task of a particular index.
     * @param date date of the meal to be marked done.
     * @param index the index of task to be marked done.
     * @return Returns the meal that was marked done.
     */
    public Meal markDone(String date, int index) {
        Meal markedDoneMeal = this.mealTracker.get(date).get(index - 1);
        this.mealTracker.get(date).get(index - 1).markAsDone();
        return markedDoneMeal;
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
            ArrayList<Meal> meals = getMealsList(dateStr);
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
            throw new DukeException("No meal matches description of " + newMeal.getDescription() + " on "
                    + newMeal.getDate());
        } else {
            throw new DukeException("No meal found on " + newMeal.getDate());
        }
    }

    public boolean isSameMeal(Meal meal1, Meal meal2) {
        return meal1.getDescription().equals(meal2.getDescription()) && meal1.getDate().equals(meal2.getDate());
    }

    public void setMealTracker(HashMap<String, ArrayList<Meal>> mealTracker) {
        this.mealTracker = mealTracker;
    }

    public void addMeals(Meal data) throws DukeException {
        ArrayList<Meal> mealList;
        if (mealTracker.containsKey(data.getDate())) {
            mealList = mealTracker.get(data.getDate());
        } else {
            mealTracker.put(data.getDate(), new ArrayList<>());
            mealList = mealTracker.get(data.getDate());
        }
        //match meal description to stored meals. If it matches a stored meal, compare nutrition data,
        // and fill in any missing fields
        if (storedItems.get(data.getDescription()) != null) {
            HashMap<String, Integer> storedNutritionValue = storedItems.get(data.getDescription());
            HashMap<String, Integer> nutritionValue = data.getNutritionalValue();
            for (String i: storedNutritionValue.keySet()) {
                if (nutritionValue.get(i) == null) {
                    nutritionValue.put(i, storedNutritionValue.get(i));
                }
            }
        }
        if (data.getNutritionalValue().size() == 0) {
            throw new DukeException("\u2639 OOPS!!! It appears there are no default values associated with this meal\n"
                    + "     Please set a default value for this meal using the \"add\" command, or manually\n"
                    + "     specify nutritional values for this meal");
        }
        mealList.add(data);
    }

    public ArrayList<Meal> getMealsList(String inputDate) {
        if (mealTracker.containsKey(inputDate)) {
            return mealTracker.get(inputDate);
        } else {
            mealTracker.put(inputDate, new ArrayList<>());
            return mealTracker.get(inputDate);
        }
    }

    /**
     * This function is a getter for the mealtracker HashMap.
     * @return mealTracker the data structure storing the list of all meals
     */
    public HashMap<String, ArrayList<Meal>> getMealTracker() {
        return mealTracker;
    }


    public int caloriesAvgToGoal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate startDate = LocalDate.parse(goal.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(goal.getEndDate(), formatter);
        LocalDate currentDate = LocalDate.parse(this.currentDate, formatter);
        int totalConsume = 0;
        for (LocalDate iterator = startDate; iterator.isBefore(currentDate); iterator = iterator.plusDays(1)) {
            if (mealTracker.containsKey(iterator) == false) {
                totalConsume += goal.getNutritionalValue().get("calorie") / (int) DAYS.between(startDate, endDate);
            } else {
                ArrayList<Meal> meals = mealTracker.get(iterator.format(formatter));
                if (meals.size() == 0) {
                    totalConsume += goal.getNutritionalValue().get("calorie") / (int) DAYS.between(startDate, endDate);
                } else {
                    for (int i = 0; i < meals.size(); i += 1) {
                        totalConsume += meals.get(i).getNutritionalValue().get("calorie");
                    }
                }
            }
        }
        long daysLeft = DAYS.between(currentDate,endDate);
        int caloriesRemaining = goal.getNutritionalValue().get("calorie") - totalConsume;
        if (daysLeft >= 1) {
            return caloriesRemaining / (int)daysLeft;
        } else {
            return -1;
        }
    }

    /**
     * This function is used to check if a entry with the corresponding date is stored.
     * @param date the date to be checked
     * @return boolean
     */
    public boolean checkDate(String date) {
        return mealTracker.containsKey(date);
    }

    /**
     * This function is used to add or update default values for a specified meal item.
     * @param item The data to be set as default for a meal item with matching descriptor
     */
    public void addStoredItem(Meal item) {
        String keyword = item.getDescription();
        HashMap<String, Integer> data = item.getNutritionalValue();
        if (storedItems.get(keyword) == null) {
            storedItems.put(keyword, data);
        } else {
            storedItems.remove(keyword);
            storedItems.put(keyword, data);
        }
    }

    public HashMap<String, Integer> getStoredItem(String keyword) {
        return storedItems.get(keyword);
    }

    public void removeStoredItem(String keyword) {
        storedItems.remove(keyword);
    }

    public void addGoal(Goal goal, boolean override) throws DukeException {
        if (this.goal != null && override == false) {
            throw new DukeException("You currently have a previously set goal active\n"
                    + "     Would you like to override the existing goal? (Y/N)");
        } else {
            this.goal = goal;
        }
    }

    /**
     * This function is a getter for Goal.
     * @return goal the data structure storing the goal
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * This function is a getter for the StoredItems HashMap.
     * @return storedItems the data structure storing the list of all meals
     */
    public HashMap<String, HashMap<String, Integer>> getStoredList() {
        return storedItems;
    }
}
