package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;

import java.util.Date;

/**
 * Represents a Command to start a task tracker.
 */
public class StartTrackerCommand extends Command {
    private String description;

    /**
     * Constructor for StartTrackerCommand.
     *
     * @param description of Task to start tracking.
     */
    public StartTrackerCommand(String description) {
        super();
        this.description = description;
    }

    /**
     * Parse String to get Task Description.
     *
     * @param task Task object.
     * @return Description of task Task object.
     */
    private String getTaskDescription(Task task) {
        String[] byDate = task.toString().split("\\(");
        String[] byDesc = byDate[0].split(" ", 2);
        return byDesc[1].trim();
    }

    boolean isDone(Task task) {
        return task.getStatus();
    }

    /**
     * Find Task object in TaskList where descriptions match.
     *
     * @param list TaskList object.
     * @return Task object that matches user given description.
     * @throws OofException if no matches are found.
     */
    Task findTask(TaskList list, String desc) throws OofException {
        Task task = null;
        for (int i = 0; i < list.getSize(); i++) {
            String currentDesc = getTaskDescription(list.getTask(i));
            if (desc.equals(currentDesc)) {
                task = list.getTask(i);
                break;
            }
        }
        if (task == null) {
            throw new OofException("Invalid Task Description!");
        }
        return task;
    }

    /**
     * Starts tracker to track time taken for a task.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if description is empty or task if completed.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter a Task!");
        }
        Task task = findTask(tasks, description);
        if (isDone(task)) {
            throw new OofException("Task has already been completed.");
        } else {
            Date now = new Date();
            String date = convertDateToString(now);
            task.setStartDate(date);
            ui.printStartAtCurrent(task, date, task.getTimeTaken());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
