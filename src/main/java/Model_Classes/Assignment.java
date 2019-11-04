package Model_Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



/**
 * An object class representing types of tasks: assignment.
 * Stores the description and when the task should be done by.
 */
public class Assignment extends Task {
    private ArrayList<String> subTasks;
    /**
     * Constructor for the Assignment object.
     * Takes in inputs for description and date/time the tasks should be done by.
     * @param description Description of the task
     * @param by The time the tasks should be done by.
     */
    public Assignment (String description, Date by) {
        super(description, by);
    }

    /**
     * Takes in arraylist of subtasks and sets it as this assignment's subtasks
     * @param subTasks array list containing subtasks
     */
    public void setSubTasks(ArrayList<String> subTasks) { this.subTasks =  subTasks; }

    /**
     * Takes in a String, splits it by "," and sets each new String as a subtask of current Task
     * @param subTasks string containing subtasks
     */
    public void setSubTasks(String subTasks) { this.subTasks = new ArrayList<>(Arrays.asList(subTasks.trim().split(","))); }

    /**
     * Returns the ArrayList containing the Assignment's subtasks
     * @return ArrayList<String> subtasks.
     */
    public ArrayList<String> getSubTasks() { return subTasks; }

    /**
     * Returns the full description including of the assignment.
     * @return A string indicating the task type, description, and when it should be done by.
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (by: " + super.getDate() + ")";
    }
}
