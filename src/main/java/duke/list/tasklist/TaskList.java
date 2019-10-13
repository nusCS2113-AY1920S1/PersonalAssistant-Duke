package duke.list.tasklist;

import duke.exception.DukeException;

import duke.task.Task;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static duke.common.Messages.*;

/**
 * Handles all the operations for the task in the list.
 */
public class TaskList {

    private ArrayList<Task> taskList;
    private static String msg = "";

    /**
     * Constructor for the class TaskList.
     */
    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    /**
     * Constructor to initialize taskList.
     * @param taskList loaded tasklist from file
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Search for matching tasks in taskList.
     * @param description String containing the targeted search
     * @return list of matching tasks
     * @throws DukeException if not able to find any matching task
     */
    public ArrayList<String> findTask(String description) throws DukeException {
        ArrayList<String> arrFind = new ArrayList<>();
        for (int i = 0; i < getSize() / 3; i++) {
            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
            if (taskList.get(i).getDescription().contains(description)) {
                arrFind.add("     " + displayIndex + ". " + taskList.get(i).toString());
            }
        }
        if (arrFind.isEmpty()) {
            throw new DukeException(ERROR_MESSAGE_NOTFOUND);
        } else {
            return arrFind;
        }
    }

    /**
     * Get all the tasks in the current taskList.
     * @return list of tasks in the taskList
     */
    public ArrayList<String> listTask() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize() / 3; i++) {
            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayIndex + ". " + taskList.get(i));
        }
        System.out.println("this is the number of items in the list: " + getSize());
        return arrList;
    }

    /**
     * Searches for deadline type task in taskList.
     * @return list of deadlines
     */
    public ArrayList<String> remindDeadlineTask() {
        ArrayList<String> arrRemind = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            if (taskList.get(i).getTaskType() == Task.TaskType.DEADLINE) {
                arrRemind.add(taskList.get(i).toString());
            }
        }
        return arrRemind;
    }

    /**
     * Get number of tasks in taskList.
     * @return Integer corresponding to the number of tasks in taskList
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Adds deadline task to taskList.
     * @param description String containing the description of the task
     * @param by String containing the date and time of the deadline for the task
     */
/*    public void addDeadlineTask(String description, String by) throws ParseException {
        taskList.add(new Deadline(description, by));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    *//**
     * Adds event task to taskList.
     * @param description String containing the description of the task
     * @param at String containing the venue of the event
     *//*
    public void addEventTask(String description, String at) {
        taskList.add(new Event(description, at));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    *//**
     * Adds todo task to taskList.
     * @param description String containing the description of the task
     *//*
    public void addTodoTask(String description) {
        taskList.add(new Todo(description));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    *//**
     * Adds fixed duration task to taskList.
     * @param description String containing the description of the task
     * @param need String containing time needed for the task
     *//*
    public void addDurationTask(String description, String need) {
        taskList.add(new Duration(description, need));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    *//**
     * Adds task that has to be done within a certain period of time.
     * @param description String containing the description of the task
     * @param startDate String containing the start date of the period to complete the task.
     * @param endDate String containing the end date of the period to complete the task.
     *//*
    public void addPeriodTask(String description, String startDate, String endDate) {
        taskList.add(new Period(description, startDate, endDate));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    *//**
     * Adds tasks that will recur daily, weekly, monthly or yearly.
     * @param description String containing the description of the task
     * @param frequency String containing the frequency of recurrence of the task.
     * @param dayOrDate String containing either the day of the date of the recurring task.
     *//*
    public void addRecurringTask(String description, String frequency, String dayOrDate) throws ParseException {
        taskList.add(new Recurring(description, frequency, dayOrDate));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }
  
    *//**
     * Adds todo task need to be done after a specific time or task.
     * @param description String containing the description of the task
     * @param after String containing the specific time or task
     *//*
    public void addDoAfterTask(String description, String after) {
        taskList.add(new DoAfter(description, after));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    *//**
     * Adds tentative schedule task to taskList.
     * @param description String containing the description of the task
     * @param on String containing the multiple dates and time
     *//*
    public void addTentativeSchedulingTask(String description, String on) throws ParseException {
        String[] items = on.split(",");
        ArrayList<Task> tentativeTasks = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            tentativeTasks.add(new TentativeScheduling(description, items[i].trim()));
            taskList.add(new TentativeScheduling(description, items[i].trim()));
        }
        int i = 1;
        System.out.println(MESSAGE_TENTATIVE);
        for (Task task : tentativeTasks) {
            System.out.println("      " + i++ + ". " + task.toString());
        }
        System.out.println("      Pls confirm the scheduling anytime with the command: confirmschedule");
    }*/

    /**
     * Marks the task as completed.
     * @param i index of the task in taskList
     */
    public void doneTask(int i) {
        taskList.get(i).markAsDone();
        System.out.println(MESSAGE_MARKED + "       " + taskList.get(i));
    }

    /**
     * Schedules task in one of the slots.
     * @param i index of the task in taskList
     */
    public void scheduledTask(int i) {
        String description = taskList.get(i).getDescription();
        // to avoid concurrent modification exception.
        List<Task> myList = new CopyOnWriteArrayList<Task>();
        myList.addAll(taskList);
        myList.get(i).markAsDone();
        for (Task task : myList) {
            if (task.getTaskType() == Task.TaskType.TENTATIVESCHEDULING) {
                if (task.getDescription().equals(description)) {
                    if (task.getStatusIcon().equals("-")) {
                        myList.remove(task);
                    }
                }
            }
        }
        System.out.println(MESSAGE_SCHEDULED + "       " + taskList.get(i));
        taskList.clear();
        taskList.addAll(myList);
    }

    /**
     * Delete task in taskList.
     * @param i index of the task in taskList
     */
    public void deleteTask(int i) {
        if (taskList.size() - 1 <= 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_DELETE + "       " + taskList.get(i)
                + "\n" + MESSAGE_ITEMS1 + (taskList.size() - 1) + msg);
        taskList.remove(taskList.get(i));
    }

    public void snoozeTask(int i) {
        System.out.println(MESSAGE_SNOOZE + "       " + taskList.get(i));
        taskList.remove(taskList.get(i));
    }

    /**
     * Get the current taskList in file.
     * @return ArrayList containing tasks
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }
}