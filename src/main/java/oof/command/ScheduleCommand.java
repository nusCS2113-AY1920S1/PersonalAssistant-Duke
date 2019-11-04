package oof.command;

import java.util.ArrayList;

import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to query schedule on a specified date.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    private String date;

    /**
     * Constructor for ScheduleCommand.
     *
     * @param input String containing input.
     */
    public ScheduleCommand(String input) {
        super();
        this.date = input;
    }

    /**
     * Queries schedule on specified date.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws OofException if user inputs invalid command or date has no tasks scheduled.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        if (date.isEmpty()) {
            throw new OofException("OOPS! Please enter a date!");
        }
        TaskList scheduledTasks = scheduleByDate(taskList);
        if (scheduledTasks.isEmpty()) {
            throw new OofException("There are no Tasks scheduled on " + date + ".");
        }
        ui.printTasksByDate(scheduledTasks, date);
    }

    /**
     * Checks if input date and date of Task are equal.
     *
     * @param input date from user input.
     * @param date  date from existing Task.
     * @return true if they are equal, false otherwise.
     */
    private boolean compareDate(String input, String date) {
        return input.equals(date);
    }

    /**
     * Checks TaskList for Tasks associated to indicated date.
     *
     * @param taskList ArrayList of Task objects.
     * @return ArrayList of Task objects associated to given date.
     */
    private TaskList scheduleByDate(TaskList taskList) {
        ArrayList<Task> scheduledTasks = new ArrayList<>();
        for (Task task : taskList.getTaskList()) {
            String date = getDate(task);
            if (compareDate(this.date, date)) {
                scheduledTasks.add(task);
            }
        }
        return new TaskList(scheduledTasks);
    }
}