package Operations;

import CustomExceptions.DukeException;
import Enums.ExceptionType;
import Model_Classes.Task;
import Model_Classes.ToDo;

import java.util.ArrayList;
import Enums.TimeUnit;

/**
 * A class to perform operations on the task list in Duke
 */
public class TaskList {
    private static ArrayList<Task> tasks;

    /**
     * Constructor for the TaskList class.
     * takes in an ArrayList as the list of tasks to be operated on.
     * @param tasks ArrayList of Task objects to be operated on.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task into the task list
     * @param newTask Task object to be added into the list of tasks
     */
    public void add(Task newTask) {
        tasks.add(newTask);
    }

    /**
     * Deletes a task from the list. Task to be deleted is specified by the index that is input into this method
     * Will not perform any operations if the index does not exist in the list.
     * @param index Index of task in the list to be deleted
     * @throws DukeException If the index cannot be found in the list of tasks.
     */
    public void delete(int index) throws DukeException {
        tasks.remove(index - 1);
    }

    /**
     * Lists out all tasks in the current list in the order they were added into the list.
     */
    public void list() throws DukeException {
        if( tasks.size() != 0 ){
            int listCount = 1;
            for (Task output : tasks) {
                System.out.println("    " + listCount + ". " + output.toString());
                listCount += 1;
            }
        } else {
            throw new DukeException(ExceptionType.emptylist);
        }

    }

    /**
     * Sets a task in the list as 'done' to mark that the user has completed the task.
     * Will not perform any operations if the index does not exist in the list.
     * @param index Index of the task to be marked as done.
     * @throws DukeException If the index cannot be found in the list of tasks.
     */
    public void done(int index) throws DukeException{
        tasks.get(index - 1).setDone();
    }

    /**
     * Searches for tasks that has the specified keyword and prints them to the console.
     * Will prompt that the search has no results if keyword does not exist in the list.
     * @param key Keyword of the search.
     */
    public void find (String key) {
        int queryCount = 1;
        for (Task query : tasks) {
            if (query.toString().toLowerCase().contains(key)) {
                System.out.println("    " + queryCount + ". " + query.toString());
            }
            queryCount += 1;
        }
        if (queryCount == 1) {
            System.out.println("    Your search returned no results.... Try searching with another keyword!");
        }
    }

    /**
     * Returns the entire ArrayList of tasks
     * @return tasks The ArrayList of Task objects that is being operated on.
     */
    public static ArrayList<Task> currentList() {
        return tasks;
    }

    public void replace(int index, Task replacement) {
        tasks.set(index, replacement);
    }

    /**
     * Snooze a specific task indicated by user
     * @param index the index of the task to be snoozed
     * @param amount the amount of time to snooze
     * @param timeUnit unit for snooze time: year, month, day, hour, minute
     */
    public void snooze (int index, int amount, TimeUnit timeUnit){
        if (tasks.get(index - 1) instanceof ToDo){
            System.out.println("Todo cannot be snoozed");
            return;
        }
        switch (timeUnit) {
            case year:
                tasks.get(index - 1).snoozeYear(amount);
                break;
            case month:
                tasks.get(index - 1).snoozeMonth(amount);
                break;
            case day:
                tasks.get(index - 1).snoozeDay(amount);
                break;
            case hours:
                tasks.get(index - 1).snoozeHour(amount);
                break;
            case minutes:
                tasks.get(index - 1).snoozeMinute(amount);
                break;
        }
    }
}
