package oof.logic.command.productivity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import oof.logic.command.Command;
import oof.ui.Ui;
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

//@@author jasperosy

/**
 * Represents a Command to view tasks for a particular week.
 */
public class ViewWeekCommand extends Command {

    public static final String COMMAND_WORD = "viewweek";
    private static final String TODO = "TODO";
    private static final String EVENT = "EVENT";
    private static final String DEADLINE = "DEADLINE";
    private static final String LESSON = "LESSON";
    private int day;
    private int week;
    private int month;
    private int year;
    private int lastDate;
    private YearMonth yearMonth;
    private static final int TIME = 0;
    private static final int DESCRIPTION = 1;
    private static final int DATE_FIRST = 0;
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 1;
    private static final int INDEX_DAY = 0;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_YEAR = 2;
    private static final int OFFSET = 1;
    private static final int MONTH_JANUARY = 0;
    private static final int MONTH_DECEMBER = 11;
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
            this.day = Integer.parseInt(argumentArray[INDEX_DAY]);
            this.month = Integer.parseInt(argumentArray[INDEX_MONTH]) - OFFSET;
            this.year = Integer.parseInt(argumentArray[INDEX_YEAR]);
            calendar.set(year, month, day);
            this.week = calendar.get(Calendar.WEEK_OF_YEAR);
            this.yearMonth = YearMonth.of(year, month + OFFSET);
            this.lastDate = yearMonth.lengthOfMonth();
            if ((this.day < DATE_FIRST || this.day > lastDate)
                    || (this.month < MONTH_JANUARY || this.month > MONTH_DECEMBER)) {
                this.day = calendar.get(Calendar.DATE);
                this.month = calendar.get(Calendar.MONTH);
                this.year = calendar.get(Calendar.YEAR);
                calendar.set(year, month, day);
                this.week = calendar.get(Calendar.WEEK_OF_YEAR);
                this.yearMonth = YearMonth.of(year, month + OFFSET);
                this.lastDate = yearMonth.lengthOfMonth();
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            this.day = calendar.get(Calendar.DATE);
            this.month = calendar.get(Calendar.MONTH);
            this.year = calendar.get(Calendar.YEAR);
            calendar.set(year, month, day);
            this.week = calendar.get(Calendar.WEEK_OF_YEAR);
            this.yearMonth = YearMonth.of(year, month + OFFSET);
            this.lastDate = yearMonth.lengthOfMonth();
        }
    }

    /**
     * Prints calendar for the current week.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager) {
        Calendar calendar = Calendar.getInstance();
        Date date = getStartDate(this.day, this.month, this.year);
        calendar.setTime(date);
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        for (Task task : taskList.getTaskList()) {
            if (task instanceof Todo) {
                addToDoTask(task, calendar);
            } else if (task instanceof Deadline) {
                addDeadlineTask(task, calendar);
            } else if (task instanceof Event) {
                addEventTask(task, calendar);
            }
        }
        for (Semester semester : semesterList.getSemesterList()) {
            parseLessons(semester, calendar);
        }
        for (ArrayList<String[]> day : calendarTasks) {
            day.sort(new SortByTime());
        }
        int largestTaskSize = getLargestTaskNo(calendarTasks);
        int largestColSize = getLargestColSize(calendarTasks);
        ui.printViewWeek(calendarTasks, date, largestTaskSize, largestColSize);
    }

    /**
     * Adds todo task to the list of task for the week if the task falls into the week of interest.
     *
     * @param task     Task to be added to the list of task.
     * @param calendar Instance of calendar index generation.
     */
    private void addToDoTask(Task task, Calendar calendar) {
        Todo todo = (Todo) task;
        String[] dateSplit = todo.getTodoDate().split("-");
        if (dateMatches(dateSplit)) {
            String description = todo.getDescription();
            String time = "";
            int day = ((Integer.parseInt(dateSplit[INDEX_DAY]) - calendar.get(Calendar.DATE)) + lastDate)
                    % lastDate;
            addEntry(TODO, time, description, day);
        }
    }

    /**
     * Adds deadline task to the list of task for the week if the task falls into the week of interest.
     *
     * @param task     Task to be added to the list of task.
     * @param calendar Instance of calendar index generation.
     */
    private void addDeadlineTask(Task task, Calendar calendar) {
        Deadline deadline = (Deadline) task;
        String[] dateTimeSplit = deadline.getDeadlineDateTime().split(" ");
        String[] dateSplit = dateTimeSplit[INDEX_DATE].split("-");
        if (dateMatches(dateSplit)) {
            String description = deadline.getDescription();
            String time = dateTimeSplit[INDEX_TIME];
            int day = ((Integer.parseInt(dateSplit[INDEX_DAY]) - calendar.get(Calendar.DATE)) + lastDate)
                    % lastDate;
            addEntry(DEADLINE, time, description, day);
        }
    }

    /**
     * Adds event task to the list of task for the week if the task falls into the week of interest.
     *
     * @param task     Task to be added to the list of task.
     * @param calendar Instance of calendar index generation.
     */
    private void addEventTask(Task task, Calendar calendar) {
        Event event = (Event) task;
        String[] dateTimeSplit = event.getStartDateTime().split(" ");
        String[] dateSplit = dateTimeSplit[INDEX_DATE].split("-");
        if (dateMatches(dateSplit)) {
            String description = event.getDescription();
            String time = dateTimeSplit[INDEX_TIME];
            int day = ((Integer.parseInt(dateSplit[INDEX_DAY]) - calendar.get(Calendar.DATE)) + lastDate)
                    % lastDate;
            addEntry(EVENT, time, description, day);
        }
    }

    /**
     * Parses the semester for lessons that occurs within the queried month.
     *
     * @param semester Instance of Semester that stores Module objects.
     * @param calendar Instance of Calendar.
     */
    private void parseLessons(Semester semester, Calendar calendar) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startingDate = LocalDate.parse(semester.getStartDate(), format);
        LocalDate endingDate = LocalDate.parse(semester.getEndDate(), format);
        int startDay = calendar.get(Calendar.DATE);
        for (int day = startDay; day < startDay + DAYS_IN_WEEK; day++) {
            queryModules(semester, startingDate, endingDate, day);
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
        if (day == lastDate + OFFSET) {
            day -= lastDate;
            yearMonth = yearMonth.plusMonths(OFFSET);
            if (yearMonth.getYear() > MONTH_DECEMBER + OFFSET) {
                yearMonth = yearMonth.plusYears(OFFSET);
            }
        } else if (day > lastDate) {
            day -= lastDate;
        }
        LocalDate queryDate = yearMonth.atDay(day);
        if (isWithinRange(queryDate, startDate, endDate)) {
            for (Module module : semester.getModules()) {
                addLesson(queryDate, module);
            }
        }
    }

    /**
     * Adds lesson to the calendar.
     *
     * @param queryDate Day of the month.
     * @param module    Instance of Module containing Lesson data
     */
    private void addLesson(LocalDate queryDate, Module module) {
        for (Lesson lesson : module.getLessons()) {
            DayOfWeek dayOfWeek = lesson.getDay();
            if (queryDate.getDayOfWeek() == dayOfWeek) {
                addEntry(LESSON, lesson.getStartTime(), lesson.getDescription(), dayOfWeek.getValue());
            }
        }
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

    /**
     * Finds the largest number of tasks in the week to be printed.
     *
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
     *
     * @param calendarTasks Tasks to be printed for the week.
     * @return Size of the task with the longest description and time.
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
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - OFFSET;
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
        int month = Integer.parseInt(dateSplit[INDEX_MONTH]) - OFFSET;
        int year = Integer.parseInt(dateSplit[INDEX_YEAR]);
        Date date = getStartDate(day, month, year);
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return this.week == week && this.year == year;
    }

    /**
     * Adds an entry to the calendarTask ArrayList.
     *
     * @param time        Time of task.
     * @param description Description of task.
     * @param day         Day of task.
     */
    private void addEntry(String type, String time, String description, int day) {
        String[] entry = {time, description, type};
        this.calendarTasks.get(day).add(entry);
    }
}
