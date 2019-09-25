package seedu.duke.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static seedu.duke.parser.DateTimeParser.getDateTime;

/**
 * Display reminders list when the app first start
 * and user can type command "remind" to bring up reminders after that.
 */
public class Reminders {
    /*The different list for different types of reminders*/
    private ArrayList<Task> lastDayList = new ArrayList<>();
    private ArrayList<Task> overDueList = new ArrayList<>();
    private String filePath = "data/duke.txt";
    /*Get the current date and time*/
    private Calendar currentDate = Calendar.getInstance();
    private int currentYear = currentDate.get(Calendar.YEAR);
    private int currentMonth = currentDate.get(Calendar.MONTH) + 1;
    private int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

    /**
     * Creates a reminder list when it is the last day for the tasks/task to be done base on real actual time.
     *
     * @return an ArrayList of type Task.
     */
<<<<<<< HEAD
    public ArrayList<Task> oneDay() {
        try {
            Scanner dukeTxt = new Scanner(new File(this.filePath));
            while (dukeTxt.hasNextLine()) {
                // splits line input based on |
                String[] taskString = dukeTxt.nextLine().split("\\|");
                //compare time and date with current time and date
                if (taskString[0].equals("D") && !taskString[1].equals("1")) {
                    String[] dateString = taskString[3].split("/| ");
                    int day = Integer.parseInt(dateString[0]);
                    int month = Integer.parseInt(dateString[1]);
                    int year = Integer.parseInt(dateString[2]);
                    boolean checkDay = day - currentDay == 1;
                    boolean checkMonth = month - currentMonth == 0;
                    boolean checkYear = year - currentYear == 0;
                    LocalDateTime localDateTime = getDateTime(taskString[3]);
                    if (checkYear && checkMonth && checkDay) {
                        lastDayList.add(new Deadline(taskString[2], localDateTime));
                    }
                } else if (taskString[0].equals("E") && !taskString[1].equals("1")) {
                    String[] dateString = taskString[3].split("/| ");
                    int day = Integer.parseInt(dateString[0]);
                    int month = Integer.parseInt(dateString[1]);
                    int year = Integer.parseInt(dateString[2]);
                    int time = Integer.parseInt(dateString[3]);
                    boolean checkDay = day - currentDay == 1;
                    boolean checkMonth = month - currentMonth == 0;
                    boolean checkYear = year - currentYear == 0;
                    LocalDateTime localDateTime = getDateTime(taskString[3]);
                    if (checkYear && checkMonth && checkDay) {
                        lastDayList.add(new Event(taskString[2], localDateTime));
                    }
=======
    public static ArrayList<Task> overdue(TaskList list) {
        overDueList.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime currentTime = LocalDateTime.now();
            boolean done = list.get(i).isDone;
            boolean checkDeadline = list.get(i).toString().contains("[D]");
            boolean checkEvent = list.get(i).toString().contains("[E]");
            boolean checkRange = list.get(i).toString().contains("[R]");
            if (!done && checkDeadline || checkEvent || checkRange) {
                long duration = Duration.between(currentTime, list.get(i).getDateTime()).getSeconds();
                if (duration <= 0) {
                    overDueList.add(list.get(i));
>>>>>>> branch-DetectAnomalies
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("\t_____________________________________");
            System.out.println("\tNo reminders for one day left");
            System.out.println("\t_____________________________________");
        }

<<<<<<< HEAD
=======
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
>>>>>>> branch-DetectAnomalies
        return lastDayList;
    }

    /**
     * Display the list of different reminders
     * and there is no reminders, no list will be displayed.
     */
<<<<<<< HEAD
    public void displayReminder() {
=======
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
>>>>>>> branch-DetectAnomalies

        if (!lastDayList.isEmpty()) {
            System.out.println("\t_____________________________________");
            System.out.println("\tREMINDER!!! One day left to finish task/tasks!");
            for (int i = 0; i < lastDayList.size(); i += 1) {
                System.out.println("\t" + (i + 1) + "." + lastDayList.get(i));
            }
            System.out.println("\t_____________________________________");
        }
    }

    /**
     * Check if reminder list exist.
     * @param list of type Task.
     * @return return ArrayList of type Task.
     */
    public boolean checkList(ArrayList<Task> list) {
        boolean check = false;
        if (!list.isEmpty()) {
            check = true;
        }
        return check;
    }

    /**
     * Creates the different types of reminders.
     *
     */
    public void runAll() {
        oneDay();
    }
}


