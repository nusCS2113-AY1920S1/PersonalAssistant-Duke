package Model_Classes;


import Enums.TimeUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



/**
 * An object class representing types of tasks with deadlines.
 * Stores the description and when the task should be done by.
 */
public class Assignment extends Task {
    private ArrayList<String> subTasks;
    /**
     * Constructor for the Deadline object.
     * Takes in inputs for description and date/time the tasks should be done by.
     * @param description Description of the task
     * @param by The time the tasks should be done by.
     */
    public Assignment (String description, Date by) {
        super(description, by);
    }

    /**
     * Takes in a String, splits it by "," and sets each new String as a subtask of current Task
     * @param input String containing subtasks separated by ","
     */
    public void setSubTasks(String input) {
        subTasks = new ArrayList<>(Arrays.asList(input.split(",")));
    }

    /**
     * Returns the ArrayList containing the Assignment's subtasks
     * @return ArrayList<String> subtasks.
     */
    public ArrayList<String> getSubTasks() { return subTasks; }

    /**
     * Returns the full description including the deadline of the task.
     * @return A string indicating the task type, description, and when it should be done by.
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (by: " + super.getDate() + ")";
    }
}
