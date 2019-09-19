package duke;

import java.util.ArrayList;
import duke.items.Task;
import duke.items.Todo;
import duke.items.Deadline;
import duke.items.Event;

/**
 * Manages the list of (different types of classes),
 * including all the methods to modify the list:
 * Adding each of the 3 types, print, delete, mark as done, search.
 */

public class TaskList {
    private ArrayList<Task> taskList;
    private int listIndex;

    public TaskList(ArrayList<Task> savedFile, int lastIndex) {
        taskList = savedFile;
        listIndex = lastIndex;
    }

    public TaskList() {
        listIndex = 0;
        taskList = new ArrayList<Task>();
    }

    /**
     * Returns the last index of the taskList.
     */
    public int getLastIndex() {
        return taskList.get(taskList.size() - 1).getTaskIndex();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getSize() {
        return taskList.size();
    }

    public void setListIndex(int index) {
        listIndex = index;
    }

    public int getListIndex() {
        return listIndex;
    }

    /**
     * Adds a todo item to the list and prints a confirmation.
     *
     * @param todoitem the description of the task.
     */
    public void addTodoItem(int index, String todoitem) {
        taskList.add(new Todo(index, todoitem)); //Use the constructor to create a new Task.
        System.out.println("Todo item added: " + todoitem);
        setListIndex(index + 1); //Next open index.
    }

    /**
     * Adds a deadline item to the list and prints a confirmation.
     *
     * @param deadline the command with the description and deadline of the task.
     */
    public void addDeadlineItem(int index, String description, String deadline) {
        taskList.add(new Deadline(index, description, deadline)); //Use the constructor to create a new Task.
        System.out.println("Deadline item added: " + description);
        System.out.println("Deadline is: " + deadline);
        setListIndex(index + 1); //Next open index.
    }

    /**
     * Adds an event item to the list and prints a confirmation.
     *
     * @param event the description of the task.
     * @param at the time the event happens.
     */
    public void addEventItem(int index, String event, String at) {

        taskList.add(new Event(index, event, at)); //Use the constructor to create a new Task.
        System.out.println("Event item added: " + event);
        System.out.println("Event happens at: " + at);
        setListIndex(index + 1); //Next open index.
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

}
