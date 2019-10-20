package oof.command;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.task.Deadline;
import oof.task.Event;
import oof.task.Task;
import oof.task.Todo;

/**
 * Represents a Command to print calendar.
 */
public class CalendarCommand extends Command {

    private int month;
    private int year;
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 1;
    private static final int INDEX_DAY = 0;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_YEAR = 2;
    private static final int MONTH_JANUARY = 1;
    private static final int MONTH_DECEMBER = 12;
    private static final int SIZE_CALENDAR = 32;
    private ArrayList<ArrayList<String[]>> calendarTasks = new ArrayList<>(SIZE_CALENDAR);

    /**
     * Constructor for CalendarCommand.
     *
     * @param argumentArray Array of command arguments input by user.
     */
    public CalendarCommand(String[] argumentArray) {
        while (calendarTasks.size() != SIZE_CALENDAR) {
            calendarTasks.add(new ArrayList<>());
        }
        Calendar calendar = Calendar.getInstance();
        try {
            this.month = Integer.parseInt(argumentArray[INDEX_MONTH]);
            this.year = Integer.parseInt(argumentArray[INDEX_YEAR]);
            if (month < MONTH_JANUARY || month > MONTH_DECEMBER) {
                // 0-based indexing to 1-based indexing
                this.month = calendar.get(Calendar.MONTH) + 1;
                this.year = calendar.get(Calendar.YEAR);
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            // 0-based indexing to 1-based indexing
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.year = calendar.get(Calendar.YEAR);
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        YearMonth yearMonth = YearMonth.of(year, month);
        for (Task task : tasks.getTasks()) {
            if (task instanceof Todo) {
                Todo todo = (Todo) task;
                String[] dateSplit = todo.getOn().split("-");
                if (verifyTask(dateSplit)) {
                    String description = todo.getLine();
                    String time = "";
                    int day = Integer.parseInt(dateSplit[INDEX_DAY]);
                    addEntry(time, description, day);
                }
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                String[] dateTimeSplit = deadline.getBy().split(" ");
                String[] dateSplit = dateTimeSplit[INDEX_DATE].split("-");
                if (verifyTask(dateSplit)) {
                    String description = deadline.getLine();
                    String time = dateTimeSplit[INDEX_TIME];
                    int day = Integer.parseInt(dateSplit[INDEX_DAY]);
                    addEntry(time, description, day);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                String[] dateTimeSplit = event.getStartTiming().split(" ");
                String[] dateSplit = dateTimeSplit[INDEX_DATE].split("-");
                if (verifyTask(dateSplit)) {
                    String description = event.getLine();
                    String time = dateTimeSplit[INDEX_TIME];
                    int day = Integer.parseInt(dateSplit[INDEX_DAY]);
                    addEntry(time, description, day);
                }
            }
        }
        for (ArrayList<String[]> day : calendarTasks) {
            day.sort(new SortByTime());
        }
        ui.printCalendar(yearMonth, calendarTasks);
    }

    /**
     * Checks if task occurs in the month and year queried by user.
     *
     * @param dateSplit Array containing day, month and year.
     * @return true if task month and year is equal to month and year queried by user.
     */
    public boolean verifyTask(String[] dateSplit) {
        int month = Integer.parseInt(dateSplit[INDEX_MONTH]);
        int year = Integer.parseInt(dateSplit[INDEX_YEAR]);
        return this.month == month && this.year == year;
    }

    /**
     * Adds an entry to the calendarTask ArrayList.
     *
     * @param time        Time of task.
     * @param description Description of task.
     * @param day         Day of task.
     */
    public void addEntry(String time, String description, int day) {
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
