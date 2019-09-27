package duke.tasks;

import java.util.ArrayList;

/**
 * TaskList is a public class that represents the list of tasks under duke.
 * A TaskList object encapsulates the ArrayList of tasks.
 */
public class mealList {
    private ArrayList<Meal> meals;

    /**
     * This is the constructor of TaskList object.
     * @param meals the array list of tasks to be assigned
     */
    public mealList(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    /**
     * This is the constructor of TaskList object if there is no argument.
     * The TaskList object will initialise a new empty arraylist of task.
     */
    public mealList() {this.meals = new ArrayList<Meal>();}


    /**
     * This function is used to delete the task of a particular index.
     * @param index the index of task to be deleted
     */
    public void delete(int index) { (this.meals).remove(index - 1);}

    public ArrayList<Meal> getMeals() {
        return meals;
    }

}
