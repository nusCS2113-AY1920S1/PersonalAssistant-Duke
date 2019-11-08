package oof.logic.command.productivity;

import java.util.ArrayList;

import oof.commons.exceptions.command.InvalidArgumentException;
import oof.logic.command.Command;
import oof.logic.command.productivity.exceptions.ScheduleEmptyException;
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
    private String input;

    /**
     * Constructor for ScheduleCommand.
     *
     * @param input String containing input.
     */
    public ScheduleCommand(String input) {
        super();
        this.input = input;
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
        TaskList scheduledTasks = scheduleByDate(taskList, date);
        if (scheduledTasks.isEmpty()) {
            throw new ScheduleEmptyException("There are no Tasks scheduled on " + date + ".");
        }
        ui.printTasksByDate(scheduledTasks, date);
    }

    /**
     * Checks TaskList for Tasks associated to indicated date.
     *
     * @param taskList ArrayList of Task objects.
     * @param date     The user given date.
     * @return ArrayList of Task objects associated to given date.
     */
    private TaskList scheduleByDate(TaskList taskList, String date) {
        ArrayList<Task> scheduledTasks = new ArrayList<>();
        for (Task task : taskList.getTaskList()) {
            String currDate = getDate(task);
            if (currDate.equals(date)) {
                scheduledTasks.add(task);
            }
        }
        return new TaskList(scheduledTasks);
    }
}