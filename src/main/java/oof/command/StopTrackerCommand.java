package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.model.tracker.Tracker;
import oof.model.tracker.TrackerList;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Represents a Command to stop a task tracker.
 */
public class StopTrackerCommand extends Command {

    private String module;

    /**
     * Default Constructor for StopTrackerCommand.
     *
     * @param description arguments entered by user.
     */
    public StopTrackerCommand(String description) {
        super();
        this.module = description;
    }

    /**
     * Stops Tracker timer.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param trackerList  Instance of TrackerList that stores Tracker objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if invalid Module Code detected.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, TrackerList trackerList, Ui ui, Storage storage)
            throws OofException {
        if (module.isEmpty()) {
            throw new OofException("Please enter a Module Code!");
        }
        Tracker tracker = findTracker(trackerList, module);
        if (!isStarted(tracker)) {
            throw new OofException("Tracker has not been started.");
        } else {
            long totalTime = tracker.getTimeTaken();
            String date = convertDateToString(new Date());
            tracker.setEndDate(date);
            totalTime += getDateDiff(tracker, TimeUnit.MINUTES);
            tracker.setTimeTaken(totalTime);
            storage.writeTrackerList(trackerList);
            ui.printEndAtCurrent(tracker, date, totalTime);
            resetStartEnd(tracker);
        }
    }

    /**
     * Checks if a Start Time for Task object exists.
     *
     * @param tracker Task object.
     * @return boolean True if Start Time for Task object is not null.
     */
    private boolean isStarted(Tracker tracker) {
        return tracker.getStartDate() != null;
    }

    /**
     * Reset StartDate and EndDate values to NULL in Task object.
     * @param tracker      Task object.
     */
    private void resetStartEnd(Tracker tracker) {
        tracker.setStartDate(null);
        tracker.setEndDate(null);
    }

    /**
     * Gets the time difference between the start and end of a tracked task.
     * @param tracker Instance of Task object being tracked.
     * @return number of minutes between start and end of a task.
     * @throws OofException if start or end date is invalid.
     */
    private long getDateDiff(Tracker tracker, TimeUnit timeUnit) throws OofException {
        try {
            Date start = convertStringToDate(tracker.getStartDate());
            Date end = convertStringToDate(tracker.getEndDate());
            long diffInMilliSeconds = end.getTime() - start.getTime();
            return TimeUnit.MINUTES.convert(diffInMilliSeconds, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            throw new OofException("Unable to retrieve time taken.");
        }
    }

    /**
     * Find Tracker object in TrackerList where descriptions match.
     *
     * @param trackerList TrackerList object.
     * @return Tracker object that matches user given description.
     * @throws OofException if no matches are found.
     */
    private Tracker findTracker(TrackerList trackerList, String description) throws OofException {
        Tracker tracker = null;
        for (int i = 0; i < trackerList.getSize(); i++) {
            String currentDesc = trackerList.getTracker(i).getModule();
            if (description.equals(currentDesc)) {
                tracker = trackerList.getTracker(i);
                break;
            }
        }
        if (tracker == null) {
            throw new OofException("Invalid Module Code!");
        }
        return tracker;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
