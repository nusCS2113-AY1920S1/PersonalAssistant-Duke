package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Enums.Priority;
import Enums.TimeUnit;
import Model_Classes.Assignment;
import Model_Classes.Meeting;
import Model_Classes.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        sortPriority();
    }

    /**
     * Deletes a task from the list. Task to be deleted is specified by the index that is input into this method
     * Will not perform any operations if the index does not exist in the list.
     * @param index Index of task in the list to be deleted
     * @throws RoomShareException If the index cannot be found in the list of tasks.
     */
    public void delete(int[] index, TempDeleteList deletedList) throws RoomShareException {
        int[] idx = index.clone();
        if (idx.length == 1) {
            if (idx[0] < 0 || idx[0] >= tasks.size()) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            deletedList.add(tasks.get(idx[0]));
            tasks.remove(idx[0]);
        }
        else {
            for (int i = idx[0]; idx[1] >= idx[0]; idx[1]--) {
                if (i < 0 || i >= tasks.size()) {
                    throw new RoomShareException(ExceptionType.outOfBounds);
                }
                deletedList.add(tasks.get(i));
                tasks.remove(i);
            }
        }
    }
    /**
     * Lists out all tasks in the current list in the order they were added into the list.
     */
    public void list() throws RoomShareException {
        sortPriority();
        if( tasks.size() != 0 ){
            int listCount = 1;
            for (Task output : tasks) {
                System.out.println("\t" + listCount + ". " + output.toString());
                if( output instanceof Assignment && !(((Assignment) output).getSubTasks() == null) ) {
                    ArrayList<String> subTasks = ((Assignment) output).getSubTasks();
                    for(String subtask : subTasks) {
                        System.out.println("\t" + "\t" + "-" + subtask);
                    }
                }
                listCount += 1;
            }
        } else {
            throw new RoomShareException(ExceptionType.emptyList);
        }
    }


    /**
     * Sets a task in the list as 'done' to mark that the user has completed the task.
     * Will not perform any operations if the index does not exist in the list.
     * @param index Index of the task to be marked as done.
     * @throws RoomShareException If the index cannot be found in the list of tasks.
     */
    public void done(int[] index) throws RoomShareException {
        if (index.length == 1) {
            if (index[0] < 0 || index[0] >= tasks.size()) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            tasks.get(index[0]).setDone(true);
        }
        else {
            for (int i = index[0]; i <= index[1]; i++){
                if (i < 0 || i >= tasks.size()) {
                    throw new RoomShareException(ExceptionType.outOfBounds);
                }
                tasks.get(i - 1).setDone(true);
            }
        }
    }

    /**
     * Searches for tasks that has the specified keyword and prints them to the console.
     * Will prompt that the search has no results if keyword does not exist in the list.
     * @param key Keyword of the search.
     */
    public void find (String key) {
        int queryCount = 1;
        for (Task query : tasks) {
            if (query.toString().toLowerCase().contains(key.trim())) {
                System.out.println("\t" + queryCount + ". " + query.toString());
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
     * Sets priority of task
     */
    public void setPriority(String[] info) throws RoomShareException {
        try {
            int index = Integer.parseInt(info[0]) - 1;
            Priority priority = Priority.valueOf(info[1]);
            tasks.get(index).setPriority(priority);
        } catch (IllegalArgumentException a) {
            throw new RoomShareException(ExceptionType.wrongPriority);
        } catch (IndexOutOfBoundsException i) {
            throw new RoomShareException(ExceptionType.outOfBounds);
        }

    }

    /**
     * Returns priority of the task in the form of an integer
     * high = 0, medium = 1, low = 2
     * @param t task in which we are checking the value of
     * @return integer value of the task's priority
     */
    public int getValue(Task t) {
        if (t.getPriority().equals(Priority.high)) {
            return 0;
        } else if (t.getPriority().equals(Priority.medium)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Sorts the list based on priority
     */
    public void sortPriority() {
        tasks.sort(Comparator.comparingInt(this::getValue));
    }

    /**
     * Reorder the positions of two tasks inside the task list
     * @param first the first task
     * @param second the second task
     */
    public void reorder(int first, int second) {
        Collections.swap(tasks, first, second);
    }

    /**
     * Snooze a specific task indicated by user
     * @param index the index of the task to be snoozed
     * @param amount the amount of time to snooze
     * @param timeUnit unit for snooze time: month, day, hour, minute
     */
    public void snooze (int index, int amount, TimeUnit timeUnit){
        switch (timeUnit) {
        case month:
            tasks.get(index).snoozeMonth(amount);
            break;
        case day:
            tasks.get(index).snoozeDay(amount);
            break;
        case hours:
            tasks.get(index).snoozeHour(amount);
            break;
        case minutes:
            tasks.get(index).snoozeMinute(amount);
            break;
        }
    }

    public int getSize() {
        return tasks.size();
    }

    public int getDoneSize(){
        int count = 0;
        for (Task t: tasks){
            if (t.getDone()) count++;
        }
        return count;
    }
}
