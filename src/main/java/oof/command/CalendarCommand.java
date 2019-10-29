package oof.command;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;

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
    private static final String DELIMITER_DATE = "-";
    private static final String DELIMITER_DATE_TIME = " ";
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
                throw new OofException("Invalid month");
            }
        } catch (IndexOutOfBoundsException | NumberFormatException | OofException e) {
            // 0-based indexing to 1-based indexing
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.year = calendar.get(Calendar.YEAR);
        }
    }

    /**
     * Prints the calendar for the queried month and year.
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) {
        YearMonth yearMonth = YearMonth.of(year, month);
        for (Task task : taskList.getTasks()) {
            String description = task.getDescription();
            String[] dateSplit = {};
            String time = "";
            if (task instanceof Todo) {
                Todo todo = (Todo) task;
                dateSplit = todo.getTodoDate().split(DELIMITER_DATE);
            } else {
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    String[] dateTimeSplit = deadline.getDeadlineDateTime().split(DELIMITER_DATE_TIME);
                    dateSplit = dateTimeSplit[INDEX_DATE].split(DELIMITER_DATE);
                    time = dateTimeSplit[INDEX_TIME];
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    String[] dateTimeSplit = event.getStartDateTime().split(DELIMITER_DATE_TIME);
                    dateSplit = dateTimeSplit[INDEX_DATE].split(DELIMITER_DATE);
                    time = dateTimeSplit[INDEX_TIME];
                }
            }
            if (verifyTask(dateSplit)) {
                int day = Integer.parseInt(dateSplit[INDEX_DAY]);
                addEntry(time, description, day);
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
    private boolean verifyTask(String[] dateSplit) {
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
    private void addEntry(String time, String description, int day) {
        String[] entry = {time, description};
        this.calendarTasks.get(day).add(entry);
    }

}
