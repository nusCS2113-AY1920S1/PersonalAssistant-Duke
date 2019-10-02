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
     * @param index the index of task to be deleted
     */
    public void delete(int index) {
        this.mealTracker.get(currentDate).remove(index - 1);
    }

    public void setMeals(String inputDate) {
        mealTracker.put(inputDate, new ArrayList<Meal>());
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

    public boolean checkDate(String date) {
        return mealTracker.containsKey(date);
    }
}
