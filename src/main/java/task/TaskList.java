package task;

import exceptions.DukeException;

import java.util.*;

public class TaskList {
    private static List<Tasks> tasks;
    private static TreeMap<Date, Tasks> DE;
    private static TreeMap<Date, Tasks> E;

    public TaskList(List<Tasks> tasks ) {
        DE = new TreeMap<>();
        E = new TreeMap<>();

//        System.out.println("initialising");

        for(Tasks a: tasks){
            if(a.getType().equals("E")){
                DE.put(((Event)a).getDate().getStartDate() , a);
                E.put(((Event)a).getDate().getStartDate() , a);
//                System.out.println("Added a new event");
            }else if(a.getType().equals("D")){
                DE.put(((Deadline)a).getDate().getStartDate() , a);
//                System.out.println("Added a new deadline");
            }
        }


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
     * Removes a particular task from database.
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
     * Extracts the type of a particular task from database.
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
     * Extracts the status icon of a particular task from database.
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
     * Extracts the task description from a particular task from database.
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
