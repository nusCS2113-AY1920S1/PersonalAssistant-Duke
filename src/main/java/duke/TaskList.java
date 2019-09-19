package duke;

import java.util.*;
import duke.exceptions.BadInputException;
import duke.items.*;

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
    public void addTodoItem(int index, String todoitem, String doAfter) {
        taskList.add(new Todo(index, todoitem, doAfter)); //Use the constructor to create a new Task.
        System.out.println("Todo item added: " + todoitem);
        setListIndex(index + 1); //Next open index.
    }

    /**
     * Adds a todo item to the list but with duration.
     * @param todoitem description of the task.
     * @param hours duration of task.
     */
    public void addTodoItem(int index, String todoitem, String doAfter, int hours) {
        taskList.add(new Todo(index, todoitem, doAfter, hours)); //Use the constructor to create a new Task.
        System.out.println("Todo item added: " + todoitem);
        System.out.println("Hours needed: " + hours);
        setListIndex(index + 1); //Next open index.
    }

    /**
     * Adds a deadline item to the list and prints a confirmation.
     *
     * @param deadline the command with the description and deadline of the task.
     */
    public void addDeadlineItem(int index, String description, String deadline, String doAfter) {
        try {
            //Use the constructor to create a new Task.
            taskList.add(new Deadline(index, description, deadline, doAfter));
            System.out.println("Deadline item added: " + description);
            System.out.println("Deadline is: " + deadline);
            setListIndex(index + 1); //Next open index.
        } catch (BadInputException e) {
            System.out.println(e);
        }
    }

    /**
     * Adds an event item to the list and prints a confirmation.
     *
     * @param event the description of the task.
     * @param at the time the event happens.
     */
    public void addEventItem(int index, String event, String at, String doAfter) {
        try {
            taskList.add(new Event(index, event, at, doAfter)); //Use the constructor to create a new Task.
            System.out.println("Event item added: " + event);
            System.out.println("Event happens at: " + at);
            setListIndex(index + 1); //Next open index.

        } catch (BadInputException e) {
            System.out.println(e);
        }
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
    * Snooze a task for a day.
    * @param i the index of the task to be snoozed.
    */
    public void snoozeTask(int i) {
        try {
            Task task = taskList.get(i);
            if (task instanceof Snooze) {
                ((Snooze) taskList.get(i)).snooze(); //Snooze task.
                System.out.print("Nice! I've snoozed this task: ");
                System.out.println(taskList.get(i).getDescription()); //Prints task name
            } else {
                System.out.println("Only Events and Deadlines are able to be snoozed!");
            }
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

        for (Task task: taskList) {
            if (task instanceof Deadline && !task.getIsDone()) {
                Deadline deadline = (Deadline) task;
                long timeDifference = deadline.getDate().getTime() - now.getTime();
                if (timeDifference <= millisInFiveDays && timeDifference > 0) {
                    task.printTaskDetails();
                }
            }
        }
    }

    public void findFreeHours(int reqFreeHours) {
        if (reqFreeHours < 0) {
            System.out.println("Please enter an hour value >= 0.");
            return;
        }

        // Creating temporary ArrayList of events.
        ArrayList<Event> eventSortedList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i) instanceof Event) {
                eventSortedList.add((Event) taskList.get(i));
            }
        }
        eventSortedList.sort(new EventDateTimeComparator());
        if (eventSortedList.size() <= 1) {
            System.out.println("You need at least 2 events to run this command!");
            return;
        }
        // Array is definitely sorted at this point.

        // Printing out for testing purposes.
        for (int i = 0; i < eventSortedList.size(); i++) {
            System.out.println(eventSortedList.get(i).toString());
        }

        DateTime latestEndTime = eventSortedList.get(0).getEventEndTimeObj();
        Event eventBeforeFreeTime = eventSortedList.get(0);
        int curMaxFreeHours = 0;
        boolean freeTimeFound = false;

        for (int i = 1; i < eventSortedList.size(); i++) {
            DateTime nextStartTime = eventSortedList.get(i).getEventStartTimeObj();
            DateTime nextEndTime = eventSortedList.get(i).getEventEndTimeObj();

            int compare = latestEndTime.getAt().compareTo(nextStartTime.getAt());
            // latestEndTime is earlier than nextStartTime
            if (compare < 0) {
                // Getting number of hours between latestEndTime and nextStartTime
                long ms = nextStartTime.getAt().getTime() - latestEndTime.getAt().getTime();
                int potentialMaxFreeHours = (int) ((float)ms / (1000 * 60 * 60));
                System.out.println(potentialMaxFreeHours);

                if (potentialMaxFreeHours >= curMaxFreeHours) {
                    curMaxFreeHours = potentialMaxFreeHours;
                    eventBeforeFreeTime = eventSortedList.get(i - 1);
                }

                // Since curEndTime is earlier than or equal to nextStartTime, it is guaranteed that
                // our latestEndTime will be equiv to nextEndTime - since this definitely
                // takes place after curEndTime.
                latestEndTime = nextEndTime;

                if (curMaxFreeHours >= reqFreeHours) {
                    eventBeforeFreeTime = eventSortedList.get(i - 1);
                    freeTimeFound = true;
                    break;
                }
            }

            // If curEndTime is later than or equal to nextStartTime - this only happens in the
            // event of a clash between events, i.e. events running concurrently.
            else {
                // Assuming the (next) clashing event takes place perfectly within the current event
                // we keep the value of latestEndTime untouched.
                if (nextEndTime.getAt().getTime() <= latestEndTime.getAt().getTime()) {
                    // Leave latestEndTime untouched.
                }

                // Else, if the clashing event happens to end after the current event, we need to update
                // out latestEndTime value accordingly.
                else {
                    latestEndTime = nextEndTime;
                }
            }
        }

        if (!freeTimeFound) {
            eventBeforeFreeTime = eventSortedList.get(eventSortedList.size() - 1);
        }

        System.out.println("The earliest free time I found was after the following event:\n +" +
                eventBeforeFreeTime.toString());
    }
}
