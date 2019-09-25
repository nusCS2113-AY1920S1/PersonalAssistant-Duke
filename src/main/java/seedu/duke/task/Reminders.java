package seedu.duke.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Display reminders list when the app first start
 * and user can type command "remind" to bring up reminders after that.
 */
public class Reminders {

    private static ArrayList<Task> overDueList = new ArrayList<>();
    private static ArrayList<Task> lastDayList = new ArrayList<>();
    private static ArrayList<Task> lastThirtyMinsList = new ArrayList<>();

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
            if (!done && list.get(i).toString().contains("[D]") || list.get(i).toString().contains("[E]")) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= 0) {
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
            if (!done && list.get(i).toString().contains("[D]") || list.get(i).toString().contains("[E]")) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= 86400 && duration > 1800) {
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
        lastThirtyMinsList.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean done = list.get(i).isDone;
            if (!done && list.get(i).toString().contains("[D]") || list.get(i).toString().contains("[E]")) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= 1800 && duration > 0) {
                    lastThirtyMinsList.add(list.get(i));
                }
            }
        }
        return lastThirtyMinsList;
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
                System.out.println("The task below is overdue!!!");
                System.out.print("1.");
                for (Task i : overDueList) {
                    System.out.println(i);
                }
            } else {
                System.out.println("The tasks below are overdue!!!");
                for (int i = 0; i < overDueList.size(); i++) {
                    System.out.println(i + 1 + "." + overDueList.get(i));
                }
            }
        }
        if (!lastThirtyMinsList.isEmpty()) {
            System.out.println();
            if (lastThirtyMinsList.size() == 1) {
                System.out.println("The task below is due in 30 minutes or less!!!");
                System.out.print("1.");
                for (Task i : lastThirtyMinsList) {
                    System.out.println(i);
                }
            } else {
                System.out.println("The tasks below are due in 30 minutes or less!!!");
                for (int i = 0; i < lastThirtyMinsList.size(); i++) {
                    System.out.println(i + 1 + "." + lastThirtyMinsList.get(i));
                }
            }
        }
        if (!lastDayList.isEmpty()) {
            System.out.println();
            if (lastDayList.size() == 1) {
                System.out.println("The task below is due in 24 hours or less!!!");
                System.out.print("1.");
                for (Task i : lastDayList) {
                    System.out.println(i);
                }
            } else {
                System.out.println("The tasks below are due in 24 hours or less!!!");
                for (int i = 0; i < lastDayList.size(); i++) {
                    System.out.println(i + 1 + "." + lastDayList.get(i));
                }
            }
        }

    }
}



