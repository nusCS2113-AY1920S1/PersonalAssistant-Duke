package duke.tasklist;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.DoAfter;
import duke.task.Duration;
import duke.task.Event;
import duke.task.Period;
import duke.task.Task;
import duke.task.Todo;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;
import static duke.common.Messages.MESSAGE_ADDED;
import static duke.common.Messages.MESSAGE_DELETE;
import static duke.common.Messages.MESSAGE_ITEMS1;
import static duke.common.Messages.MESSAGE_ITEMS2;
import static duke.common.Messages.MESSAGE_MARKED;
import static duke.common.Messages.MESSAGE_SNOOZE;
import static duke.common.Messages.ERROR_MESSAGE_NOTFOUND;

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
        for (int i = 0; i < getSize(); i++) {
            if (taskList.get(i).getDescription().contains(description)) {
                arrFind.add(taskList.get(i).toString());
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
        for (int i = 0; i < getSize(); i++) {
            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayIndex + ". " + taskList.get(i));
        }
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
    public void addDeadlineTask(String description, String by) throws ParseException {
        taskList.add(new Deadline(description, by));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    /**
     * Adds event task to taskList.
     * @param description String containing the description of the task
     * @param at String containing the venue of the event
     */
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

    /**
     * Adds todo task to taskList.
     * @param description String containing the description of the task
     */
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

    /**
     * Adds fixed duration task to taskList.
     * @param description String containing the description of the task
     * @param need String containing time needed for the task
     */
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

    /**
     * Adds fixed duration task to taskList.
     * @param description String containing the description of the task
     * @param startDate String containing the start date of the period to complete the task.
     * @param endDate String containing the end date of the period to complete the task.
     */
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
  
    /**
     * Adds todo task need to be done after a specific time or task.
     * @param description String containing the description of the task
     * @param after String containing the specific time or task
     */
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

    /**
     * Mark the task as completed.
     * @param i index of the task in taskList
     */
    public void doneTask(int i) {
        taskList.get(i).markAsDone();
        System.out.println(MESSAGE_MARKED + "       " + taskList.get(i));
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
     * Get the current taskList in file
     * @return ArrayList containing tasks
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }
}