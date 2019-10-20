package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.task.Task;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StopTrackerCommand extends Command {

    private String description;
    private StartTrackerCommand stc = new StartTrackerCommand(description);
    private PauseTrackerCommand ptc = new PauseTrackerCommand(description);

    public StopTrackerCommand(String description) {
        super();
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter a Task!");
        }
        Task task = stc.findTask(tasks, description);
        if (stc.isDone(task)) {
            throw new OofException("Task has already been completed.");
        } else if (ptc.isNotStarted(task)) {
            throw new OofException("Task has not started.");
        } else {
            long totalTime = task.getTimeTaken();
            String date = convertDateToString(new Date());
            task.setEndDate(date);
            totalTime += ptc.getDateDiff(task, TimeUnit.MINUTES);
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
