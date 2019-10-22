package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Represents a Command to stop a task tracker.
 */
public class StopTrackerCommand extends Command {

    private String description;
    private StartTrackerCommand stc = new StartTrackerCommand(description);
    private PauseTrackerCommand ptc = new PauseTrackerCommand(description);

    /**
     * Default Constructor for StopTrackerCommand.
     *
     * @param description arguments entered by user.
     */
    public StopTrackerCommand(String description) {
        super();
        this.description = description;
    }

    /**
     * Checks if a Start Time for Task object exists.
     *
     * @param task Task object.
     * @return boolean True if Start Time for Task object is not null.
     */
    private boolean isStarted(Task task) {
        return task.getStartDate() != null;
    }

    /**
     * Gets the time difference between the start and end of a tracked task.
     * @param task Instance of Task object being tracked.
     * @return number of minutes between start and end of a task.
     * @throws OofException if start and end date is invalid.
     */
    private long getDateDiff(Task task) throws OofException {
        try {
            Date start = convertStringToDate(task.getStartDate());
            Date end = convertStringToDate(task.getEndDate());
            long diffInMilliSeconds = end.getTime() - start.getTime();
            return TimeUnit.MINUTES.convert(diffInMilliSeconds, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            throw new OofException("Unable to retrieve time taken. Please retry!");
        }
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter a Task!");
        }
        Task task = stc.findTask(tasks, description);
        if (task.getStatus()) {
            throw new OofException("Task has already been completed.");
        } else if (ptc.isNotStarted(task)) {
            throw new OofException("Task has not started.");
        } else {
            long totalTime = task.getTimeTaken();
            String date = convertDateToString(new Date());
            task.setEndDate(date);
            totalTime += ptc.getDateDiff(task, TimeUnit.MINUTES);
            task.setTimeTaken(totalTime);
            ui.printEndAtCurrent(task, date, totalTime);
            ptc.resetStartEnd(task);
            task.setStatus();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
