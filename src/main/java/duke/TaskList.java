package duke;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import duke.exceptions.BadInputException;
import duke.items.DateTime;
import duke.items.Deadline;
import duke.items.Event;
import duke.items.Task;
import duke.items.Todo;
import duke.enums.TaskType;

/**
 * Manages the list of (different types of classes),
 * including all the methods to modify the list:
 * Adding each of the 3 types, print, delete, mark as done, search.
 */

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> savedFile) {
        taskList = savedFile;
    }

    public TaskList() {
        taskList = new ArrayList<Task>();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getSize() {
        return taskList.size();
    }

    /**
     * Adds a task to the tasklist.
     *
     * @return True if item was added successfully.
     */
    public boolean addItem(TaskType type, String description) {
        taskList.add(new Todo(description));

        return true;
    }

    /**
     * Adds a ToDo item to the list.
     *
     * @param type        TaskType enum MUST BE TODO.
     * @param description User input description of task
     * @param hrs         Duration of task.
     * @return true if item was added successfully.
     */
    public boolean addItem(TaskType type, String description, int hrs) {
        if (type != TaskType.TODO) {
            return false;
        }
        taskList.add(new Todo(description, hrs));

        return true;
    }

    /**
     * Adds a TODO, DEADLINE or EVENT item to the list.
     *
     * @param type        TaskType enum of task to be added.
     * @param description User input description of task.
     * @param dateTimes   Vararg of DateTimes that are to be added.
     * @return true if item was added successfully.
     */
    public boolean addItem(TaskType type, String description, DateTime... dateTimes) {
        try {
            switch (type) {
                case TODO:
                    taskList.add(new Todo(description));
                    break;

                case DEADLINE:
                    taskList.add(new Deadline(description, dateTimes[0]));
                    break;

                case EVENT:
                    taskList.add(new Event(description, dateTimes[0], dateTimes[1]));
                    break;

                default:
                    return false;
            }

        } catch (BadInputException e) {
            System.out.println(e);
        }

        return true;
    }


    /**
     * Prints the whole list of items with index numbers.
     */
    public void printList() {
        int max = taskList.size();
        if (max == 0) {
            System.out.println("The list is currently empty.");
            return;
        }

        for (int i = 0; i < max; i++) { //Index starts from 0.
            System.out.print(i + 1 + ". "); //Add 1 to follow natural numbers.
            taskList.get(i).printTaskDetails();
        }
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }

    /**
     * Deletes a task of the user's choice.
     *
     * @param i the index of the task to be deleted.
     */
    public void deleteTask(int i) {
        try {
            Task item = taskList.get(i);
            taskList.remove(i); //The original copy is gone.

            System.out.print("Okay! I've deleted this task: ");
            System.out.println(item.getDescription());

            if (item.getIsDone()) {
                System.out.println("The task was completed.");
            } else {
                System.out.println("The task was not completed.");
            }

        } catch (IndexOutOfBoundsException e) {
            printTaskNonexistent();
        }
    }

    /**
     * Marks a task as done.
     *
     * @param i the index of the task to be marked as done.
     */
    public void markTaskAsDone(int i) {

        try {
            taskList.get(i).markAsDone(); //Mark task as done.
            System.out.print("Nice! I've marked this task as done: ");
            System.out.println(taskList.get(i).getDescription()); //Prints task name
        } catch (IndexOutOfBoundsException e) {
            printTaskNonexistent();
        }
    }


    /**
     * Prints error message if a nonexistent task index is accessed.
     * Prints the task list for user to choose again.
     */
    private void printTaskNonexistent() {
        System.out.println("That task doesn't exist! Please check the available tasks again: ");
        printList();
    }

    /**
     * Allows the user to search for task descriptions that match a given string.
     * Prints the list of tasks that match. Alternatively prints a message if none are found.
     */
    public void searchForTask(String search) {
        int max = taskList.size();
        boolean found = false;

        for (int i = 0; i < max; i++) {
            if (taskList.get(i).getDescription().contains(search)) {
                System.out.print(i + 1 + ". "); //Print the index of the task.
                taskList.get(i).printTaskDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Sorry, I could not find any tasks containing the description \"" + search + "\".");
            System.out.println("Please try a different search string.");
        }
    }

    /**
     * Looks for undone deadlines within the next 5 Days and prints the task.
     */
    public void printReminders() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        long millisInFiveDays = 5 * 24 * 60 * 60 * 1000;

        for (Task task : taskList) {
            if (task instanceof Deadline && !task.getIsDone()) {
                Deadline deadline = (Deadline) task;
                long timeDifference = deadline.getDate().getTime().getTime() - now.getTime();
                if (timeDifference <= millisInFiveDays && timeDifference > 0) {
                    task.printTaskDetails();
                }
            }
        }
    }
}
