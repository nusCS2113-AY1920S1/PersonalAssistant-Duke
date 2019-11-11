package oof.logic.command.productivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

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
 * Shows a summary of tomorrow's tasks.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    private static final String DELIMITER_DATE_TIME = " ";
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 1;
    private static final int ADD_A_DAY = 1;
    private ArrayList<String[]> summary;

    /**
     * Constructor for SummaryCommand.
     */
    public SummaryCommand() {
        super();
        this.summary = new ArrayList<>();
    }

    /**
     * Gets a summary of tomorrow's Tasks.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui,
                        StorageManager storageManager) throws ScheduleEmptyException {
        String tomorrow = getTomorrowDate();
        getSummary(tomorrow, taskList, semesterList);
        if (summary.isEmpty()) {
            throw new ScheduleEmptyException("There are no Tasks scheduled on " + tomorrow + ".");
        }
        summary.sort(new SortByTime());
        ui.printTasksByDate(summary, tomorrow);
    }

    /**
     * Get a summary of tomorrow's tasks.
     *
     * @param date         Tomorrow's date.
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param semesterList Instance of SemesterList that stores Semester objects.
     */
    private void getSummary(String date, TaskList taskList, SemesterList semesterList) {
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
                    summary.add(entry);
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
                summary.add(entry);
            }
        }
    }

    /**
     * Get the date of tomorrow in format DD-MM-YYYY.
     *
     * @return date     String containing formatted date of tomorrow.
     */
    private String getTomorrowDate() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(ADD_A_DAY);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        return format.format(ldt);
    }
}
