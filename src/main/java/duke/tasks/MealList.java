package duke.tasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * TaskList is a public class that represents the list of tasks under duke.
 * A TaskList object encapsulates the ArrayList of tasks.
 */
public class MealList {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar calendarDate = Calendar.getInstance();
    private String currentDate = dateFormat.format(calendarDate.getTime());
    private HashMap<String, ArrayList<Meal>> mealTracker = new HashMap<>();
    private HashMap<String, HashMap<String, Integer>> storedItems = new HashMap<>();

    /**
     * This is the constructor of TaskList object if there is no argument.
     * The TaskList object will initialise a new empty arraylist of task.
     */
    public MealList() {
    }


    /**
     * This function is used to delete the task of a particular index.
     * @param index the index of task to be deleted
     */
    public void delete(int index) {
        this.mealTracker.get(currentDate).remove(index - 1);
    }

    public void setMeals(String inputDate) {
        mealTracker.put(inputDate, new ArrayList<Meal>());
    }

    public void setMealTracker(HashMap<String, ArrayList<Meal>> mealTracker) {
        this.mealTracker = mealTracker;
    }

    public void addMeals(Meal data) {
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
     * @return HashMap<String, ArrayList<Meal>> mealTracker
     */
    public HashMap<String, ArrayList<Meal>> getMealTracker() {
        return mealTracker;
    }

    /**
     * This function is used to check if a entry with the corresponding date is stored
     * @param date
     * @return boolean
     */
    public boolean checkDate(String date) {
        return mealTracker.containsKey(date);
    }

    public void addStoredItem(Meal item) {
        String keyword = item.getDescription();
        HashMap <String, Integer> data = item.getNutritionalValue();
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

    public HashMap<String, HashMap<String, Integer>> getStoredList() {
        return storedItems;
    }
}
