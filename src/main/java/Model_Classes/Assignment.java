package Model_Classes;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



/**
 * An object class representing types of tasks: assignment.
 * Stores the description and when the task should be done by.
 */
public class Assignment extends Task {
    private ArrayList<String> subTasks = new ArrayList<String>();
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
     * @param addList array list containing subtasks
     */
    public void addSubTasks(ArrayList<String> addList) {
        for(String output : addList) {
            subTasks.add(output);
        }
    }

    /**
     * Takes in a String, splits it by "," and sets each new String as a subtask of current Task
     * @param subTasks string containing subtasks
     */
    public void addSubTasks(String subTasks) { this.subTasks = new ArrayList<>(Arrays.asList(subTasks.trim().split(","))); }

    /**
     * Returns the ArrayList containing the Assignment's subtasks
     * @return ArrayList<String> subtasks.
     */
    public ArrayList<String> getSubTasks() { return subTasks; }

    /**
     * Removes completed Subtask
     * @param index index of completed subtask
     * @throws RoomShareException when there is no subtask at that index
     */
    public void doneSubtask(int index) throws RoomShareException {
        try {
            subTasks.remove(index);
        } catch (IndexOutOfBoundsException a) {
            throw new RoomShareException(ExceptionType.noSubtask);
        }
    }

    /**
     * Returns the full description including of the assignment.
     * @return A string indicating the task type, description, and when it should be done by.
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (by: " + super.getDate() + ")";
    }
}
