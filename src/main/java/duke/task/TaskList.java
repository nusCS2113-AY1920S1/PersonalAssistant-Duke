package duke.task;

import exceptions.DukeException;
import parser.TimeParser;
import duke.ui.Ui;
import wrapper.Pair;
import wrapper.TimeInterval;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeMap;


public class TaskList {
    private static List<Tasks> tasks;
    private static TreeMap<Date, Tasks> DE;
    private static TreeMap<Date, Tasks> E;

    /**
     * Constructor for class.
     * @param tasks which is the tasks in the database.
     */
    public TaskList(List<Tasks> tasks) {
        DE = new TreeMap<>();
        E = new TreeMap<>();

        for (Tasks a : tasks) {
            if (a.getType().equals("E")) {
                DE.put(((Event) a).getDate().getStartDate(), a);
                E.put(((Event) a).getDate().getStartDate(), a);
            } else if (a.getType().equals("D")) {
                DE.put(((Deadline) a).getDate().getStartDate(), a);
            }
        }

        this.tasks = tasks;
    }

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public static int getSize() {
        return tasks.size();
    }

    /**
     * This function returns time interval of free time.
     */
    public static TimeInterval getFreeSlot(int hours) {

        Date now = new Date();

        for (Map.Entry<Date, Tasks> entry : DE.entrySet()) {
            Date next = null;
            if (entry.getValue().getType().equals("D")) {
                next = ((Deadline) entry.getValue()).getDate().getStartDate();
            } else if (entry.getValue().getType().equals("E")) {
                next = ((Event) entry.getValue()).getDate().getStartDate();
            }

            if (next.after(now)) {
                long diffHour = TimeParser.getDiffHours(now, next);
                if (diffHour >= hours) {
                    return new TimeInterval(now, next);
                }
                if (entry.getValue().getType().equals("D")) {
                    now = ((Deadline) entry.getValue()).getDate().getEndDate();
                } else if (entry.getValue().getType().equals("E")) {
                    now = ((Event) entry.getValue()).getDate().getEndDate();
                }
            } else {

                if (entry.getValue().getType().equals("D")) {
                    next = ((Deadline) entry.getValue()).getDate().getEndDate();
                } else if (entry.getValue().getType().equals("E")) {
                    next = ((Event) entry.getValue()).getDate().getEndDate();
                }
                if (next.after(now)) {
                    now = next;
                }

            }

        }

        return new TimeInterval(now, now);

    }

    /**
     * This function checks whether is there any conflicts for tasks.
     */
    public static void getConflicts() {

        ArrayList<Pair> conflicts = new ArrayList<>();

        for (Map.Entry<Date, Tasks> entry1 : E.entrySet()) {
            for (Map.Entry<Date, Tasks> entry2 : E.entrySet()) {
                if (TimeParser.isConflicted(entry1.getValue(), entry2.getValue())) {
                    conflicts.add(new Pair(entry1.getValue(), entry2.getValue()));

                }
            }
        }

        Ui.showConflicts(conflicts);


    }

    /**
     * This function returns tasks from the list.
     */
    public static Tasks getTask(int num) {
        return tasks.get(num);
    }

    public static TreeMap<Date, Tasks> getTreeMap() {
        return DE;
    }

    /**
     * This function adds tasks into tree map.
     */
    public static void addTask(Tasks task) {
        tasks.add(task);
        if (task.getType().equals("E")) {
            DE.put(((Event) task).getDate().getStartDate(), task);
            E.put(((Event) task).getDate().getStartDate(), task);
        } else if (task.getType().equals("D")) {
            DE.put(((Deadline) task).getDate().getStartDate(), task);
        }
    }

    /**
     * This function returns true if two tasks are the same, else it will return false.
     */
    private static boolean isTheSameTask(Tasks a, Tasks b) {
        if (a.getType().equals(b.getType()) && a.getDescription().equals(b.getDescription())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a particular duke.task from database.
     */
    public static void removeTask(int num) throws DukeException {


        try {
            Tasks toBeRemoved = tasks.get(num);

            Date tobeRemovedKeyE = null;

            for (Map.Entry<Date, Tasks> entry : E.entrySet()) {
                if (isTheSameTask(entry.getValue(), toBeRemoved)) {
                    tobeRemovedKeyE = (entry.getKey());
                }
            }

            Date tobeRemovedKeyDE = null;

            for (Map.Entry<Date, Tasks> entry : DE.entrySet()) {
                if (isTheSameTask(entry.getValue(), toBeRemoved)) {
                    tobeRemovedKeyDE = (entry.getKey());
                }
            }
            if (tobeRemovedKeyE != null) {
                E.remove(tobeRemovedKeyE);
            }
            if (tobeRemovedKeyDE != null) {
                DE.remove(tobeRemovedKeyDE);
            }

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
     * Extracts the type of a particular duke.task from database.
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
     * Extracts the status icon of a particular duke.task from database.
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
     * Extracts the duke.task description from a particular duke.task from database.
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

    /**
     * This function counts the total number of tentative scheduled tasks in database.
     *
     * @return num which contains total number of tentative scheduled tasks in database.
     */
    public static int getTentativeNumber() {
        int num = 0;
        for (int i = 0; i < tasks.size(); i += 1) {
            if (tasks.get(i).getType().equals("?][E")) {
                num += 1;
            }
        }
        return num;
    }
}
