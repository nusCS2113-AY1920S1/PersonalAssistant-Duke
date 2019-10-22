package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PauseTrackerCommand extends Command {
    private String description;
    private StartTrackerCommand stc = new StartTrackerCommand(description);

    public PauseTrackerCommand(String description) {
        super();
        this.description = description;
    }

    /**
     * Check that a Start Time for Task object exists.
     * @param task      Task object.
     * @return          boolean True if Start Time for Task object is not null.
     */
    boolean isNotStarted(Task task) {
        return task.getStartDate() == null;
    }

    /**
     * Reset StartDate and EndDate values to NULL in Task object.
     * @param task      Task object.
     */
    void resetStartEnd(Task task) {
        task.setStartDate(null);
        task.setEndDate(null);
    }

    /**
     * Get difference between two Date.
     * @param task          Task object.
     * @param timeUnit      unit of Time to calculate by.
     * @return              difference between two Date.
     * @throws OofException if unable to retrieve Start or End time from Task object.
     */
    long getDateDiff(Task task, TimeUnit timeUnit) throws OofException {
        try {
            Date start = convertStringToDate(task.getStartDate());
            Date end = convertStringToDate(task.getEndDate());
            long diffInMillies = end.getTime() - start.getTime();
            return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            throw new OofException("Unable to retrieve time taken. Please retry!");
        }
    }

    /**
     * Invokes other Command subclasses based on the input given by the user.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter a Task!");
        }
        Task task = stc.findTask(tasks, description);
        if (stc.isDone(task)) {
            throw new OofException("Task has already been completed.");
        } else if (isNotStarted(task)) {
            throw new OofException("Task has not started.");
        } else {
            long totalTime = task.getTimeTaken();
            String date = convertDateToString(new Date());
            task.setEndDate(date);
            totalTime += getDateDiff(task, TimeUnit.MINUTES);
            task.setTimeTaken(totalTime);
            ui.printPauseAtCurrent(task, date, totalTime);
            resetStartEnd(task);
        }
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return true if ExitCommand is called, false otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
