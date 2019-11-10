package javacake.storage;

import javacake.JavaCake;
import javacake.exceptions.CakeException;
import javacake.tasks.Deadline;
import javacake.tasks.Task;

import java.util.ArrayList;
import java.util.logging.Level;

public class TaskList {
    private ArrayList<Task> data;
    private static final int maxDeadlineLength = 39;

    public enum TaskState {
        NOT_DONE, DONE
    }

    /**
     * Initialises data from current taskList being passed.
     * @param taskList the existing taskList loaded from save file
     */
    public TaskList(ArrayList<Task> taskList) {
        data = new ArrayList<>(taskList);
    }

    /**
     * Creates a new arrayList for the taskList.
     */
    public TaskList() {
        this.data = new ArrayList<>();
    }

    /**
     * Method to get the Task from index 'position'.
     * @param position Index of task in taskList
     * @return Task at index 'position'
     * @throws CakeException Shows error when bounds are exceeded
     */
    public Task get(int position) throws CakeException {
        if (position >= data.size() || position < 0) {
            throw new CakeException("Out of bounds of data!");
        }
        return data.get(position);
    }

    /**
     * Method to remove the Task from index 'position'.
     * @param position Index of task in taskList
     * @throws CakeException Shows error when bounds are exceeded
     */
    public void remove(int position) throws CakeException {
        if (position >= data.size() || position < 0) {
            throw new CakeException("Task is not within list size!");
        }
        data.remove(position);
    }

    /**
     * Method to get the current size of data.
     * @return Size of current data
     */
    public int size() {
        return data.size();
    }

    /**
     * Method to get the taskList.
     * @return ArrayList of current tasks
     */
    public ArrayList<Task> getData() {
        return data;
    }

    public void add(Task task) {
        this.data.add(task);
    }

    /**
     * Creates a new 'Deadline' task, before adding it to current list,
     * then returning the output by Duke.
     * @param data ArrayList of Tasks that's currently being stored
     * @param inputCommand Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */


    public static String runDeadline(ArrayList<Task> data, String inputCommand, TaskState state) throws CakeException {
        String[] listStr = inputCommand.split("\\s+");
        if (inputCommand.length() <= 9) {
            throw new CakeException("[!] No task description\nPlease input:\n'deadline TASK /by TASK_DATE'");
        }
        if (listStr.length < 4) {
            throw new CakeException("[!] Improper format\nPlease input:\n'deadline TASK /by TASK_DATE'");
        }
        String taskInput;
        String argumentDate;
        int idxSlash = getSlashIndex(listStr);

        if (idxSlash == -1 || idxSlash == (listStr.length - 1)) {
            JavaCake.logger.log(Level.WARNING, "INVALID DEADLINE! slashIdx: " + idxSlash);
            throw new CakeException("[!] Improper format\nPlease input:\n'deadline TASK /by TASK_DATE'");
        } else {
            taskInput = getParamString(listStr, 1, idxSlash);
            argumentDate = getParamString(listStr, idxSlash + 1, listStr.length);
            System.out.println("Task: " + taskInput);
            System.out.println("Date: " + argumentDate);
        }

        if (taskInput.length() > maxDeadlineLength) {
            JavaCake.logger.log(Level.WARNING, "Deadline Task length exceeded!");
            throw new CakeException("[!] Task length too long\nPlease input task with < 40 characters!");
        }
        Task tempTask = new Deadline(taskInput, argumentDate);
        return getString(data, state, tempTask);
    }

    private static String getParamString(String[] listStr, int lowB, int hiB) {
        String output = "";
        for (int i = lowB; i < hiB; ++i) {
            output += listStr[i];
            if (i != hiB - 1) {
                output += " ";
            }
        }
        return output;
    }

    private static int getSlashIndex(String[] listStr) {
        for (int i = 0; i < listStr.length; ++i) {
            if (listStr[i].equals("/by")) {
                return i;
            }
        }
        return -1;
    }

    private static String getString(ArrayList<Task> data, TaskList.TaskState state, Task tempTask) {
        StringBuilder stringBuilder = new StringBuilder();
        if (state == TaskList.TaskState.DONE) {
            tempTask.markAsDone();
        }
        data.add(tempTask);
        stringBuilder.append("Got it. I've added this task:").append("\n");
        stringBuilder.append(tempTask.getFullString()).append("\n");
        stringBuilder.append("Now you have ")
                .append(data.size()).append(" tasks in the list.");
        return stringBuilder.toString();
    }
}
