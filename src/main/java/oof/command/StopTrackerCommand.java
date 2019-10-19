package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StopTrackerCommand extends Command {
    private String description;
    private StartTrackerCommand stc = new StartTrackerCommand(description);

    public StopTrackerCommand(String description) {
        super();
        this.description = description;
    }

    /**
     * Check that a Start Time for Task object exists.
     * @param task      Task object.
     * @return          boolean True if Start Time for Task object is not null.
     */
    private boolean isStarted(Task task) {
        return task.getStartDate() != null;
    }

    private long getDateDiff(Task task, TimeUnit timeUnit) throws OofException {
        try {
            Date start = convertStringToDate(task.getStartDate());
            Date end = convertStringToDate(task.getEndDate());
            long diffInMillies = end.getTime() - start.getTime();
            return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            throw new OofException("Unable to retrieve time taken. Please retry!");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter a Task!");
        }
        Task task = stc.findTask(tasks, description);
        if (stc.isDone(task)) {
            throw new OofException("Task has already been completed.");
        } else if (!isStarted(task)) {
            throw new OofException("Task has no Start time.");
        } else {
            Date now = new Date();
            String date = convertDateToString(now);
            task.setEndDate(date);
            long diff = getDateDiff(task, TimeUnit.MINUTES);
            ui.printEndAtCurrent(task, date, diff);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
