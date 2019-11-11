package oof.logic.command.productivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.commons.exceptions.command.MissingArgumentException;
import oof.logic.command.Command;
import oof.logic.command.productivity.exceptions.ScheduleEmptyException;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import oof.model.university.Lesson;
import oof.model.university.Module;
import oof.model.university.Semester;
import oof.model.university.SemesterList;
import oof.storage.StorageManager;
import oof.ui.Ui;

//@@author debbiextan

/**
 * Represents a Command to query schedule on a specified date.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    private static final String DELIMITER_DATE_TIME = " ";
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 1;
    private String input;
    private ArrayList<String[]> schedule;


    /**
     * Constructor for ScheduleCommand.
     *
     * @param input String containing input.
     */
    public ScheduleCommand(String input) {
        super();
        this.input = input;
        this.schedule = new ArrayList<>();
    }

    /**
     * Queries schedule on specified date.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws MissingArgumentException if user input contains missing arguments.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (input.isEmpty()) {
            throw new MissingArgumentException("OOPS! Please enter a date!");
        }
        String date = parseDate(input);
        if (!isDateValid(date)) {
            throw new InvalidArgumentException("OOPS!!! The date is invalid.");
        }
        scheduleByDate(semesterList, taskList, date);
        if (schedule.isEmpty()) {
            throw new ScheduleEmptyException("There are no Tasks scheduled on " + date + ".");
        }
        schedule.sort(new SortByTime());
        ui.printTasksByDate(schedule, date);
    }

    /**
     * Checks TaskList for Tasks associated to indicated date.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param date         Tomorrow's date.
     */
    private void scheduleByDate(SemesterList semesterList, TaskList taskList, String date) {
        parseTaskList(taskList, date);
        parseSemesterList(semesterList, date);
    }

    /**
     * Iterates through the semesterList to find semester that queried date is within.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tomorrowDate Queried date.
     */
    private void parseSemesterList(SemesterList semesterList, String tomorrowDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate queryDate = LocalDate.parse(tomorrowDate, format);
        for (Semester semester : semesterList.getSemesterList()) {
            LocalDate startDate = LocalDate.parse(semester.getStartDate(), format);
            LocalDate endDate = LocalDate.parse(semester.getEndDate(), format);
            if (isWithinRange(queryDate, startDate, endDate)) {
                getModules(queryDate, semester);
            }
        }

    }

    /**
     * Iterates through the Module list to find Lessons on the queried date.
     *
     * @param queryDate Queried date.
     * @param semester  Instance of Semester that stores Module objects.
     */
    private void getModules(LocalDate queryDate, Semester semester) {
        for (Module module : semester.getModules()) {
            for (Lesson lesson : module.getLessons()) {
                if (queryDate.getDayOfWeek() == lesson.getDay()) {
                    String[] entry = {lesson.getStartTime(), lesson.getDescription()};
                    schedule.add(entry);
                }
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
     * Parses the task list for tasks that occurs within the queried month.
     *
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param tomorrowDate Queried date.
     */
    private void parseTaskList(TaskList taskList, String tomorrowDate) {
        for (Task task : taskList.getTaskList()) {
            String description = task.getDescription();
            String taskDate = "";
            String time = "";
            if (task instanceof Todo) {
                Todo todo = (Todo) task;
                taskDate = todo.getTodoDate();
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                String[] dateTimeSplit = deadline.getDeadlineDateTime().split(DELIMITER_DATE_TIME);
                taskDate = dateTimeSplit[INDEX_DATE];
                time = dateTimeSplit[INDEX_TIME];
            } else if (task instanceof Event) {
                Event event = (Event) task;
                String[] dateTimeSplit = event.getStartDateTime().split(DELIMITER_DATE_TIME);
                taskDate = dateTimeSplit[INDEX_DATE];
                time = dateTimeSplit[INDEX_TIME];
            }
            if (taskDate.equals(tomorrowDate)) {
                String[] entry = {time, description};
                schedule.add(entry);
            }
        }
    }

}