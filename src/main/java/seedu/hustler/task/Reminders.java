package seedu.hustler.task;

import seedu.hustler.Hustler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Display reminders list when the app first start
 * and user can type command "remind" to bring up reminders after that.
 */
public class Reminders {

    /**
     * Reminder list that contains tasks that are overdue.
     */
    private static ArrayList<Task> overDueList = new ArrayList<>();
    /**
     * Reminder list that contain tasks with less than 30 minutes or less till deadline.
     */
    private static ArrayList<Task> lastDayList = new ArrayList<>();
    /**
     * Reminder list htat contain tasks with less than 24 hours or less till deadline.
     */
    private static ArrayList<Task> lastThirtyMinutesList = new ArrayList<>();
    /**
     * 24 hours converted to seconds.
     */
    private static final int TWENTY_FOUR_HOURS = 86400;
    /**
     * 30 minutes converted to seconds.
     */
    private static final int THIRTY_MINUTES = 1800;

    /**
     * Check if a task is overdue.
     * @param i index of task in task list.
     * @return true if task is overdue.
     */
    public static Boolean checkOverdue(int i) {
        Boolean check = false;
        LocalDateTime currentTime = LocalDateTime.now();
        long duration = Duration.between(currentTime, Hustler.list.get(i).getDateTime()).getSeconds();
        if (duration <= 0) {
            check = true;
        }
        return check;
    }

    /**
     * Check if a task is due in thirty minutes.
     * @param i index of task in task list.
     * @return true if task is due in thirty minutes.
     */
    public static Boolean checkThirty(int i) {
        Boolean check = false;
        LocalDateTime currentTime = LocalDateTime.now();
        long duration = Duration.between(currentTime, Hustler.list.get(i).getDateTime()).getSeconds();
        if (duration <= THIRTY_MINUTES && duration > 0) {
            check = true;
        }
        return check;
    }

    /**
     * Check if a task is due in 24 hours.
     * @param i index of task in task list.
     * @return true if task is due in 24 hours.
     */
    public static Boolean checkLastDay(int i) {
        Boolean check = false;
        LocalDateTime currentTime = LocalDateTime.now();
        long duration = Duration.between(currentTime, Hustler.list.get(i).getDateTime()).getSeconds();
        if (duration <= TWENTY_FOUR_HOURS && duration > THIRTY_MINUTES) {
            check = true;
        }
        return check;
    }

    /**
     * List of overdue tasks.
     * @param list current TaskList.
     * @return a list of overdue tasks.
     */
    public static ArrayList<Task> overdue(TaskList list) {
        overDueList.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean done = list.get(i).isDone;
            boolean checkDeadline = list.get(i).toString().contains("[D]");
            boolean checkEvent = list.get(i).toString().contains("[E]");
            boolean checkRange = list.get(i).toString().contains("[R]");
            if (!done && checkDeadline || checkEvent || checkRange) {
                if (checkOverdue(i)) {
                    overDueList.add(list.get(i));
                }
            }
        }
        return overDueList;
    }

    /**
     * List of tasks that are due in 24 hours or less.
     * @param list current TaskList.
     * @return a list of tasks that are due in 24 hours or less.
     */
    public static ArrayList<Task> lastDay(TaskList list) {
        lastDayList.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean done = list.get(i).isDone;
            boolean checkDeadline = list.get(i).toString().contains("[D]");
            boolean checkEvent = list.get(i).toString().contains("[E]");
            boolean checkRange = list.get(i).toString().contains("[R]");
            if (!done && checkDeadline || checkEvent || checkRange) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= TWENTY_FOUR_HOURS && duration > THIRTY_MINUTES) {
                    lastDayList.add(list.get(i));
                }
            }
        }
        return lastDayList;
    }

    /**
     * List of tasks that are due in 30 minutes or less.
     * @param list current TaskList.
     * @return a list of tasks that are due in 30 minutes or less.
     */
    public static ArrayList<Task> lastThirtyMins(TaskList list) {
        lastThirtyMinutesList.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean done = list.get(i).isDone;
            boolean checkDeadline = list.get(i).toString().contains("[D]");
            boolean checkEvent = list.get(i).toString().contains("[E]");
            boolean checkRange = list.get(i).toString().contains("[R]");
            if (!done && checkDeadline || checkEvent || checkRange) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= THIRTY_MINUTES && duration > 0) {
                    lastThirtyMinutesList.add(list.get(i));
                }
            }
        }
        return lastThirtyMinutesList;
    }

    /**
     * Runs all types of reminders list at once.
     * @param list current TaskList.
     */
    public static void runAll(TaskList list) {
        overdue(list);
        lastThirtyMins(list);
        lastDay(list);
    }

    /**
     * Display reminders list.
     */
    public static void displayReminders() {
        if (!overDueList.isEmpty()) {
            if (overDueList.size() == 1) {
                System.out.println("\t_____________________________________");
                System.out.println("\tThe task below is overdue!!!");
                System.out.print("\t1. ");
                for (Task i : overDueList) {
                    System.out.println(i);
                }
                System.out.println("\t_____________________________________");
            } else {
                System.out.println("\t_____________________________________");
                System.out.println("\tThe tasks below are overdue!!!");
                for (int i = 0; i < overDueList.size(); i++) {
                    System.out.println("\t" + (i + 1) + ". " + overDueList.get(i));
                }
                System.out.println("\t_____________________________________");
            }
        }
        if (!lastThirtyMinutesList.isEmpty()) {
            System.out.println();
            if (lastThirtyMinutesList.size() == 1) {
                System.out.println("\t_____________________________________");
                System.out.println("\tThe task below is due in 30 minutes or less!!!");
                System.out.print("\t1. ");
                for (Task i : lastThirtyMinutesList) {
                    System.out.println(i);
                }
                System.out.println("\t_____________________________________");
            } else {
                System.out.println("\t_____________________________________");
                System.out.println("\tThe tasks below are due in 30 minutes or less!!!");
                for (int i = 0; i < lastThirtyMinutesList.size(); i++) {
                    System.out.println("\t" + (i + 1) + ". " + lastThirtyMinutesList.get(i));
                }
                System.out.println("\t_____________________________________");
            }
        }
        if (!lastDayList.isEmpty()) {
            System.out.println();
            if (lastDayList.size() == 1) {
                System.out.println("\t_____________________________________");
                System.out.println("\tThe task below is due in 24 hours or less!!!");
                System.out.print("\t1. ");
                for (Task i : lastDayList) {
                    System.out.println(i);
                }
                System.out.println("\t_____________________________________");
            } else {
                System.out.println("\t_____________________________________");
                System.out.println("\tThe tasks below are due in 24 hours or less!!!");
                for (int i = 0; i < lastDayList.size(); i++) {
                    System.out.println("\t" + (i + 1) + ". " + lastDayList.get(i));
                }
                System.out.println("\t_____________________________________");
            }
        }
    }

    /**
     * For testing, check if task that are overdue exist in overDueList.
     * @return true if overDueList exist.
     */
    public static boolean exist() {
        boolean check = false;
        if (!overDueList.isEmpty()) {
            check = true;
        }
        return check;
    }
}



