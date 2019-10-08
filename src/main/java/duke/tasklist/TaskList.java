package duke.tasklist;

import duke.exception.DukeException;
import duke.task.Task;
import duke.task.Booking;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;
import static duke.common.Messages.MESSAGE_DELETE;
import static duke.common.Messages.MESSAGE_ITEMS1;
import static duke.common.Messages.MESSAGE_ITEMS2;
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
     *
     * @param taskList loaded tasklist from file
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void addBooking(String customerName, String customerContact, String numberOfPax, String bookingDate, String orderName) throws ParseException {
        taskList.add(new Booking(customerName, customerContact, numberOfPax, bookingDate, orderName));
        int index = taskList.size();
        if (index == 1) {
            msg = " booking in the list.";
        } else {
            msg = " bookings in the list.";
        }
        System.out.println("New booking added:\n" + "       " + taskList.get(index - 1) + "\n" + "     Now you have " + index + msg);
    }

    /**
     * Get all the tasks in the current taskList.
     *
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
     * Search for matching tasks in taskList.
     *
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
     * Get number of tasks in taskList.
     *
     * @return Integer corresponding to the number of tasks in taskList
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Delete task in taskList.
     *
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

    /**
     * Get the current taskList in file.
     *
     * @return ArrayList containing tasks
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public Task getTask(int i) {
        return taskList.get(i);
    }
}