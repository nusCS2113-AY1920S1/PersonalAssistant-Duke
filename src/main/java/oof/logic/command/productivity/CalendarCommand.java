package oof.logic.command.productivity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import oof.logic.command.Command;
import oof.ui.Ui;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.model.semester.Lesson;
import oof.model.semester.Module;
import oof.model.semester.Semester;
import oof.model.semester.SemesterList;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import oof.storage.StorageManager;

//@@author KahLokKee

/**
 * Represents a Command to print calendar.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    private int month;
    private int year;
    private YearMonth yearMonth;
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 1;
    private static final int INDEX_DAY = 0;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_YEAR = 2;
    private static final int INDEX_ARGUMENT_MONTH = 0;
    private static final int INDEX_ARGUMENT_YEAR = 1;
    private static final int MONTH_JANUARY = 1;
    private static final int MONTH_DECEMBER = 12;
    private static final int SIZE_CALENDAR = 32;
    private static final String DELIMITER_DATE = "-";
    private static final String DELIMITER_DATE_TIME = " ";
    private ArrayList<ArrayList<String[]>> calendarTasks = new ArrayList<>(SIZE_CALENDAR);

    /**
     * Constructor for CalendarCommand.
     *
     * @param input Array of command arguments input by user.
     */
    public CalendarCommand(String input) {
        while (calendarTasks.size() != SIZE_CALENDAR) {
            calendarTasks.add(new ArrayList<>());
        }
        String[] arguments = input.split(" ");
        Calendar calendar = Calendar.getInstance();
        try {
            this.month = Integer.parseInt(arguments[INDEX_ARGUMENT_MONTH]);
            this.year = Integer.parseInt(arguments[INDEX_ARGUMENT_YEAR]);
            if (month < MONTH_JANUARY || month > MONTH_DECEMBER) {
                throw new InvalidArgumentException("Invalid month");
            }
        } catch (IndexOutOfBoundsException | NumberFormatException | InvalidArgumentException e) {
            // The current month and year will be used if there is an invalid input.
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.year = calendar.get(Calendar.YEAR);
        }
        this.yearMonth = YearMonth.of(year, month);
    }

    /**
     * Prints the calendar for the queried month and year.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task.
     * @throws InvalidArgumentException if date of Semester is invalid.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws InvalidArgumentException {
        parseTaskList(taskList);
        parseSemesterList(semesterList);
        for (ArrayList<String[]> day : calendarTasks) {
            day.sort(new SortByTime());
        }
        ui.printCalendar(yearMonth, calendarTasks);
    }

    /**
     * Parses the task list for tasks that occurs within the queried month.
     *
     * @param taskList Instance of TaskList that stores Task objects.
     */
    private void parseTaskList(TaskList taskList) {
        for (Task task : taskList.getTaskList()) {
            String description = task.getDescription();
            String[] dateSplit = {};
            String time = "";
            if (task instanceof Todo) {
                Todo todo = (Todo) task;
                dateSplit = todo.getTodoDate().split(DELIMITER_DATE);
            } else if (task instanceof Deadline) {
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
            if (verifyTask(dateSplit)) {
                int day = Integer.parseInt(dateSplit[INDEX_DAY]);
                addEntry(time, description, day);
            }

        }
    }

    /**
     * Parses the semester list for lessons that occurs within the queried month.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     */
    private void parseSemesterList(SemesterList semesterList) {
        for (Semester semester : semesterList.getSemesterList()) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate startDate = LocalDate.parse(semester.getStartDate(), format);
            LocalDate endDate = LocalDate.parse(semester.getEndDate(), format);
            for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
                queryModules(semester, startDate, endDate, day);
            }
        }
    }

    /**
     * Adds lessons of each module to the calendar if they fall within the queried month.
     *
     * @param semester  Instance of Semester containing Module data.
     * @param startDate Start date of Semester.
     * @param endDate   End date of Semester.
     * @param day       Day of Month.
     */
    private void queryModules(Semester semester, LocalDate startDate, LocalDate endDate, int day) {
        LocalDate queryDate = yearMonth.atDay(day);
        if (isWithinRange(queryDate, startDate, endDate)) {
            for (Module module : semester.getModules()) {
                addLessons(queryDate, module);
            }
        }
    }

    /**
     * Adds lesson to the calendar.
     *
     * @param queryDate Day of the month.
     * @param module    Instance of Module containing Lesson data
     */
    private void addLessons(LocalDate queryDate, Module module) {
        for (Lesson lesson : module.getLessons()) {
            DayOfWeek dayOfWeek = lesson.getDay();
            if (queryDate.getDayOfWeek() == dayOfWeek) {
                addEntry(lesson.getStartTime(), lesson.getDescription(), queryDate.getDayOfMonth());
            }
        }
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

    /**
     * Checks if a date is within two dates.
     *
     * @param queryDate Date to be checked.
     * @param startDate Starting Date used for checking.
     * @param endDate   Ending Date used for checking.
     * @return true if queried date is within start and end date.
     */
    private boolean isWithinRange(LocalDate queryDate, LocalDate startDate, LocalDate endDate) {
        return queryDate.isAfter(startDate) && queryDate.isBefore(endDate) || queryDate.isEqual(startDate)
                || queryDate.isEqual(endDate);
    }
}
