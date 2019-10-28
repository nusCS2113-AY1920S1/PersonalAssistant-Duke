package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.model.tracker.Tracker;
import oof.model.tracker.TrackerList;

import java.util.Date;

/**
 * Represents a Command to stop an Assignment Task tracker.
 */
public class StopTrackerCommand extends Command {

    private String description;

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
     * Stops Tracker timer.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if invalid Module Code detected.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter an Assignment description!");
        }

        TrackerList trackerList = storage.readTrackerList();
        Tracker tracker = trackerList.findTrackerByDesc(description);
        boolean isCompleted = tracker.getStatus();

        if (isCompleted) {
            throw new OofException("Assignment has already been completed.");
        } else if (tracker == null) {
            throw new OofException("Tracker not found.");
        } else if (tracker.getStartDate() == null) {
            throw new OofException("Tracker has not been started.");
        } else {
            long totalTime = tracker.getTimeTaken();
            Date now = new Date();
            Date startDate = tracker.getStartDate();
            totalTime += Integer.parseInt(tracker.getDateDiff(startDate));

            tracker.updateTracker(totalTime, now);
            tracker.setStatus();
            storage.writeTrackerList(trackerList);

            storage.checkDone(tracker.getStatusIcon());
            storage.writeTaskList(tasks);

            ui.printEndAtCurrent(tracker);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
