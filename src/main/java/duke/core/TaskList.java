package duke.core;

import duke.command.*;
import duke.exception.*;
import duke.task.*;

import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Stores an array of the Tasks created thus far, as well as functions to
 * search, find for, create and delete Tasks.
 */

public class TaskList {
    protected ArrayList<Task> items;

    /**
     * Constructor for the TaskList. Takes in an array of Tasks from the Storage instance
     * and saves it.
     * @param items the array of Tasks, as converted from text in the save-file by the Storage instance
     */
    public TaskList(ArrayList<Task> items, Ui ui) {
        this.items = items;
        int daysDue = 100;
        if ((searchItemsDue(daysDue).isEmpty())) {
            ui.print("No tasks due today!");
        } else {
            ui.printTaskArray("REMINDER - The following tasks are due to occur soon:", searchItemsDue(daysDue));
        }
    }

    /**
     * Returns a Task in the Task array by index number.
     * @param itemNo the index number of the desired Task.
     * @return the Task itself.
     */
    public Task getItem(int itemNo) {
        Task thisItem = items.get(itemNo);
        return thisItem;
    }

    /**
     * Returns a printable String format of a Task, as obtained by its index number.
     * @param itemNo the index number of the Task.
     * @return a String form of the Task.
     */
    public String getItemToPrint(int itemNo) {
        Task thisItem = items.get(itemNo);
        return ("  " + thisItem.toString());
    }

    /**
     * Returns the TaskList itself.
     * @return the array of Tasks.
     */
    public ArrayList<Task> getAllItems() {
        return items;
    }

    /**
     * Returns the number of items in the TaskList.
     * @return the number of items in the TaskList.
     */
    public int getNumberOfItems() {
        return items.size();
    }

    /**
     * Removes a Task from the TaskList, as specified by its index number.
     * @param itemNo the index number of the Task.
     */
    public void deleteItem(int itemNo) {
        items.remove(itemNo);
    }

    /**
     * Adds a new Task to the TaskList.
     * @param item the new created Task.
     */
    public void addItem(Task item) {
        items.add(item);
    }

    /**
     * Marks a Task in the TaskList as done, as specified by its index number.
     * @param itemNo the index number of the Task.
     */
    public void markItemAsDone(int itemNo) {
        Task thisItem = items.get(itemNo);
        thisItem.markAsDone();
    }

    /**
     * Searches for items matching the keyword inputted by the user, and returns
     * an array of Tasks (in String format, to be printed) that match the keyword.
     * @param keyword the keyword typed by the user.
     * @return an ArrayList of Tasks in String format that match the keyword.
     */
    public ArrayList<String> searchItems(String keyword) {
        ArrayList<String> results = new ArrayList<String>();
        for (int j = 0; j < items.size(); j++) {
            Task thisTask = items.get(j);
            if (thisTask.getDescription().toLowerCase().contains(keyword)) {
                results.add((j+1) + ". " + thisTask.toString());
            }
        }
        return results;
    }

    /**
     * Searches items, specifically deadlines, that are due today.
     * @return an ArrayList of String-ified Tasks that are due today.
     */
    public ArrayList<String> searchItemsDue() {
        ArrayList<String> results = new ArrayList<String>();
        int index = 1;
        for (Task thisTask : items) {
            if ((thisTask instanceof Deadline) && (thisTask.isDueToday())) {
                results.add((index) + ". " + thisTask.toString());
                index++;
            }
        }
        return results;
    }

    /**
     * Searches items, specifically deadlines, that are due in a specified number of days.
     * @param daysDue Maximum number of days left for a Task to be valid for reminder.
     * @return an ArrayList of String-ified Tasks that are due in specified number of days.
     */
    public ArrayList<String> searchItemsDue(int daysDue) {
        ArrayList<String> results = new ArrayList<String>();
        for (int j = 0; j < items.size(); j++) {
            Task thisTask = items.get(j);
            if (thisTask instanceof Deadline || thisTask instanceof Event || thisTask instanceof Recurring) {
                int actualDaysLeft = thisTask.isDueInDays(daysDue);
                if (actualDaysLeft != -1) {
                    String dayWord = actualDaysLeft == 1 ? " day " : " days ";
                    results.add((j+1) + ". " + thisTask.toString() + " -- " + actualDaysLeft + dayWord + "left");
                }
            }
        }
        return results;
    }

    /**
     * Converts the TaskList to an array of String versions of the Tasks, to be printed.
     * @return an ArrayList of String-ified Tasks.
     */
    public ArrayList<String> generateList() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            Task thisTask = items.get(i);
            list.add((i+1) + ". " + thisTask.toString());
        }
        return list;
    }

    public ArrayList<String> generateListByDate(String dateEntered) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            Task thisTask = items.get(i);
            if (thisTask.getDate() != null) {
                if (thisTask.getDateAsString().contains(dateEntered)){
                    list.add((i+1) + ". " + thisTask.toString());
                }
            }
        }
        return list;
    }

}