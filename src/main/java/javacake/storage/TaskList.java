package javacake.storage;

import com.joestelmach.natty.DateGroup;
import javacake.exceptions.CakeException;
import javacake.tasks.Deadline;
import javacake.tasks.RecurringTask;
import javacake.tasks.Task;
import javacake.tasks.ToDo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskList {
    private ArrayList<Task> data;

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
     * Creates a new 'toBeDone' task, before adding it to current list,
     * then returning the output by Duke.
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */
    public static String runTodo(ArrayList<Task> data, String input, TaskList.TaskState state) {
        input = input.substring(5);
        Task tempTask = new ToDo(input);
        return getString(data, state, tempTask);
    }

    /**
     * Creates a new 'Deadline' task, before adding it to current list,
     * then returning the output by Duke.
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */


    public static String runDeadline(ArrayList<Task> data, String input, TaskState state) throws CakeException {
        if (input.length() <= 9) {
            throw new CakeException("[!] No task description\nPlease input:\n'deadline TASK /by TASK_DATE'");
        }
        input = input.substring(9);
        String[] listStr = input.split("\\s+");
        if (listStr.length < 3) {
            throw new CakeException("[!] Improper format\nPlease input:\n'deadline TASK /by TASK_DATE'");
        }
        String taskInput = "";
        String argumentDate = "";
        int idxSlash = -1;
        for (int i = 0; i < listStr.length; ++i) {
            if (listStr[i].equals("/by")) {
                idxSlash = i;
                break;
            }
        }
        if (idxSlash == -1 || idxSlash == (listStr.length - 1)) {
            throw new CakeException("[!] Improper format\nPlease input:\n'deadline TASK /by TASK_DATE'");
        } else {
            for (int i = 0; i < idxSlash; ++i) {
                taskInput += listStr[i];
                if (i != idxSlash - 1) {
                    taskInput += " ";
                }
            }
            for (int i = idxSlash + 1; i < listStr.length; ++i) {
                argumentDate += listStr[i];
                if (i != listStr.length - 1) {
                    argumentDate += " ";
                }
            }
        }
        System.out.println("Task: " + taskInput);
        System.out.println("Date: " + argumentDate);
        if (taskInput.length() > 39) {
            throw new CakeException("[!] Task length too long\nPlease input task with < 40 characters!");
        }
        Task tempTask = new Deadline(taskInput, argumentDate);
        return getString(data, state, tempTask);
    }

    /**
     * Method to run recurring tasks.
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @param freq daily, weekly or monthly
     * @return String which highlights what Duke processed
     * @throws CakeException Shows error when cannot parse date
     */
    public static String runRecurring(ArrayList<Task> data, String input,
                                      TaskList.TaskState state, String freq) throws CakeException {
        input = input.substring(5).trim();
        String tt1;
        String tt2;
        int token;
        token = input.indexOf("/");
        tt1 = input.substring(0, token - 1);
        if (freq.equals("daily")) {
            tt2 = input.substring(token + 7);
        } else if (freq.equals("weekly")) {
            tt2 = input.substring(token + 8);
        } else {
            tt2 = input.substring(token + 9);
        }

        // parse date here
        Date startDate = parseDate(tt2);
        Task tempTask = new RecurringTask(tt1, startDate, freq);
        return getString(data, state, tempTask);
    }

    /**
     * Method to return parsed Date.
     * @param tt2 String to be parsed into Date
     * @return Date parsed from the string
     * @throws CakeException If date cannot be parsed
     */
    public static Date parseDate(String tt2) throws CakeException {
        try {
            com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
            List<DateGroup> groups = parser.parse(tt2);
            return groups.get(0).getDates().get(0);
        } catch (Exception e) {
            throw new CakeException("[!] Date cannot be parsed: " + tt2);
        }
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
                .append(Storage.getInternalDataSize()).append(" tasks in the list.");
        return stringBuilder.toString();
    }
}
