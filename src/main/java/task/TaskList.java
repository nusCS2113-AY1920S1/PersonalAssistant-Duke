package task;

import exceptions.DukeException;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private static List<Tasks> tasks;

    public TaskList(List<Tasks> tasks) {

        this.tasks = tasks;
    }

    public TaskList() {

        tasks = new ArrayList<>();
    }

    public static Tasks getTask(int num) {
        return tasks.get(num);
    }

    public static void addTask(Tasks task)  {
        tasks.add(task);
    }

    /**
     * Removes task from tasklist.
     */
    public static void removeTask(int num) throws DukeException {
        try {
            tasks.remove(num);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING_DELETE;
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
    }

    /**
     * Extracts the type of a particular task from tasklist.
     */
    public static String getType(int num) throws DukeException {
        try {
            String type = tasks.get(num).getType();
            return type;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING_DELETE;
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
    }

    /**
     * Extracts the status icon of a particular task from tasklist.
     */
    public static String getStatus(int num) throws DukeException {
        try {
            String status = tasks.get(num).getStatusIcon();
            return status;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING_DELETE;
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
    }

    /**
     * Extracts the task description from a particular task from tasklist.
     */
    public static String getMessage(int num) throws DukeException {
        try {
            String message = tasks.get(num).getDescription();
            return message;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING_DELETE;
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
    }

    public static List<Tasks> getList() {
        return tasks;
    }

    public static int getTotalTasksNumber() {
        return tasks.size();
    }

    public static void markTaskAsDone(int num) {
        tasks.get(num).setDone(true);
    }

    public static void markTaskAsUndone(int num) {
        tasks.get(num).setDone(false);
    }
}
