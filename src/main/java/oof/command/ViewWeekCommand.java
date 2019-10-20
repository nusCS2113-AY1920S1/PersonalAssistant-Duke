package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.task.Deadline;
import oof.task.Event;
import oof.task.Task;
import oof.task.Todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Represents a Command to view tasks for a particular week.
 */
public class ViewWeekCommand extends Command {

    private int day;
    private int week;
    private int month;
    private int year;
    private int lastDate;
    private static final int TIME = 0;
    private static final int DESCRIPTION = 1;
    private static final int DATE_FIRST = 0;
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 1;
    private static final int INDEX_DAY = 0;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_YEAR = 2;
    private static final int MONTH_JANUARY = 0;
    private static final int MONTH_DECEMBER = 11;
    private static final int OFFSET = 1;
    private static final int DAYS_IN_WEEK = 7;
    private static final int LEAST_POSSIBLE_TASK_SIZE = 0;
    private static final int LEAST_POSSIBLE_COL_SIZE = 19;
    private ArrayList<ArrayList<String[]>> calendarTasks = new ArrayList<>(DAYS_IN_WEEK);

    /**
     * Constructor for ViewWeekCommand.
     *
     * @param argumentArray Array of command arguments input by user.
     */
    public ViewWeekCommand(String[] argumentArray) {
        while (calendarTasks.size() != DAYS_IN_WEEK) {
            calendarTasks.add(new ArrayList<>());
        }
        Calendar calendar = Calendar.getInstance();
        try {
            this.day = Integer.parseInt(argumentArray[INDEX_DAY + OFFSET]);
            this.month = Integer.parseInt(argumentArray[INDEX_MONTH + OFFSET]) - 1;
            this.year = Integer.parseInt(argumentArray[INDEX_YEAR + OFFSET]);
            this.lastDate = calendar.getActualMaximum(Calendar.DATE);
            if ((this.day < DATE_FIRST || this.day > lastDate)
                    || (this.month < MONTH_JANUARY || this.month > MONTH_DECEMBER)) {
                this.day = calendar.get(Calendar.DATE);
                this.month = calendar.get(Calendar.MONTH);
                this.year = calendar.get(Calendar.YEAR);
                this.lastDate = calendar.getActualMaximum(Calendar.DATE);
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            this.day = calendar.get(Calendar.DATE);
            this.month = calendar.get(Calendar.MONTH);
            this.year = calendar.get(Calendar.YEAR);
            this.lastDate = calendar.getActualMaximum(Calendar.DATE);
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Calendar calendar = Calendar.getInstance();
        Date date = getStartDate(this.day, this.month, this.year);
        calendar.setTime(date);
        for (Task task : tasks.getTasks()) {
            if (task instanceof Todo) {
                Todo todo = (Todo) task;
                String[] dateSplit = todo.getOn().split("-");
                if (dateMatches(dateSplit)) {
                    String description = todo.getLine();
                    String time = "";
                    int day = ((Integer.parseInt(dateSplit[INDEX_DAY]) - calendar.get(Calendar.DATE)) + lastDate)
                            % lastDate;
                    addEntry(time, description, day);
                }
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                String[] dateTimeSplit = deadline.getBy().split(" ");
                String[] dateSplit = dateTimeSplit[INDEX_DATE].split("-");
                if (dateMatches(dateSplit)) {
                    String description = deadline.getLine();
                    String time = dateTimeSplit[INDEX_TIME];
                    int day = ((Integer.parseInt(dateSplit[INDEX_DAY]) - calendar.get(Calendar.DATE)) + lastDate)
                            % lastDate;
                    addEntry(time, description, day);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                String[] dateTimeSplit = event.getStartTiming().split(" ");
                String[] dateSplit = dateTimeSplit[INDEX_DATE].split("-");
                if (dateMatches(dateSplit)) {
                    String description = event.getLine();
                    String time = dateTimeSplit[INDEX_TIME];
                    int day = ((Integer.parseInt(dateSplit[INDEX_DAY]) - calendar.get(Calendar.DATE)) + lastDate)
                            % lastDate;
                    addEntry(time, description, day);
                }
            }
        }
        for (ArrayList<String[]> day : calendarTasks) {
            day.sort(new SortByTime());
        }
        int largestTaskSize = getLargestTaskNo(calendarTasks);
        int largestColSize = getLargestColSize(calendarTasks);
        ui.printViewWeek(calendarTasks, date, largestTaskSize, largestColSize);
    }

    /**
     * Finds the largest number of tasks in the week to be printed.
     * @param calendarTasks Tasks to be printed for the week.
     * @return Largest number of tasks in the week to be printed.
     */
    private int getLargestTaskNo(ArrayList<ArrayList<String[]>> calendarTasks) {
        int maximum = LEAST_POSSIBLE_TASK_SIZE;
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            int currentSize = calendarTasks.get(i).size();
            if (currentSize > maximum) {
                maximum = currentSize;
            }
        }
        return maximum;
    }

    /**
     * Finds the task with the longest description and time.
     * @param calendarTasks Tasks to be printed for the week.
     * @return Return the size of the task with the longest description and time.
     */
    private int getLargestColSize(ArrayList<ArrayList<String[]>> calendarTasks) {
        int maximum = LEAST_POSSIBLE_COL_SIZE;
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            for (int j = 0; j < calendarTasks.get(i).size(); j++) {
                String[] entry = calendarTasks.get(i).get(j);
                String task = entry[TIME] + " " + entry[DESCRIPTION];
                if (task.length() > maximum) {
                    maximum = task.length();
                }
            }
        }
        return maximum;
    }

    /**
     * Gets Date object from day, month and year.
     *
     * @param day   Day of the month.
     * @param month Month of the year.
     * @param year  Year.
     * @return Date object based on the day, month and year.
     */
    private Date getStartDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        this.week = calendar.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -dayOfWeek);
        return calendar.getTime();
    }

    /**
     * Checks if task occurs in the month and year queried by user.
     *
     * @param dateSplit Array containing day, month and year.
     * @return true if task month and year is equal to month and year queried by user.
     */
    private boolean dateMatches(String[] dateSplit) {
        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt(dateSplit[INDEX_DATE]);
        int month = Integer.parseInt(dateSplit[INDEX_MONTH]) - 1;
        int year = Integer.parseInt(dateSplit[INDEX_YEAR]);
        calendar.set(year, month, day);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return this.week == week && this.month == month && this.year == year;
    }

    /**
     * Adds an entry to the calendarTask ArrayList.
     *
     * @param time        Time of task.
     * @param description Description of task.
     * @param day         Day of task.
     */
    private void addEntry(String time, String description, int day) {
        String[] entry = {time, description};
        this.calendarTasks.get(day).add(entry);
    }

    /**
     * Comparator to sort tasks by their time in ascending order.
     */
    class SortByTime implements Comparator<String[]> {
        @Override
        public int compare(String[] a, String[] b) {
            if (a[0].equals("")) {
                return -1;
            } else if (b[0].equals("")) {
                return 1;
            }
            int hour1 = Integer.parseInt(a[0].substring(0, 2));
            int hour2 = Integer.parseInt(b[0].substring(0, 2));
            if (hour1 != hour2) {
                return hour1 - hour2;
            } else {
                int minute1 = Integer.parseInt(a[0].substring(3, 5));
                int minute2 = Integer.parseInt(b[0].substring(3, 5));
                return minute1 - minute2;
            }
        }

        @Override
        public boolean equals(Object object) {
            return this == object;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
