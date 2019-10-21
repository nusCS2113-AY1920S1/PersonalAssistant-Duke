package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.Todo;

/**
 * Represents a Command to query schedule on a specified date.
 */
public class ScheduleCommand extends Command {
    private String date;

    /**
     * Constructor for ScheduleCommand.
     *
     * @param date String containing date.
     */
    public ScheduleCommand(String date) {
        super();
        this.date = date;
    }

    /**
     * Queries schedule on specified date.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user inputs invalid command or date has no tasks scheduled.
     */
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (this.date.isEmpty()) {
            throw new OofException("OOPS! Please enter a date!");
        }
        TaskList scheduledTasks = scheduleByDate(tasks);
        if (scheduledTasks.getSize() == 0) {
            throw new OofException("There are no Tasks scheduled on " + this.date + ".");
        }
        ui.printTasksByDate(scheduledTasks, this.date);
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
     * @param arr ArrayList of Task objects.
     * @return ArrayList of Task objects associated to given date.
     */
    private TaskList scheduleByDate(TaskList arr) {
        TaskList scheduledTasks = new TaskList();
        for (int i = 0; i < arr.getSize(); i++) {
            Task t = arr.getTask(i);
            if (isValid(t)) {
                String date = getDate(t);
                if (compareDate(this.date, date)) {
                    scheduledTasks.addTask(t);
                }
            }
        }
        return scheduledTasks;
    }

    /**
     * Check Task type.
     *
     * @param task Task object.
     * @return boolean if Task object is of a valid Task type or not.
     */
    private boolean isValid(Task task) {
        return task instanceof Todo || task instanceof Deadline || task instanceof Event;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}