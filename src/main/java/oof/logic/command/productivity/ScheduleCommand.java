package oof.logic.command.productivity;

import java.util.ArrayList;

import oof.logic.command.Command;
import oof.ui.Ui;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.MissingArgumentException;
import oof.model.semester.SemesterList;
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
     * @throws MissingArgumentException if user input contains missing arguments.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (date.isEmpty()) {
            throw new MissingArgumentException("OOPS! Please enter a date!");
        }
        TaskList scheduledTasks = scheduleByDate(taskList);
        if (scheduledTasks.isEmpty()) {
            ui.printNoTaskScheduled(date);
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