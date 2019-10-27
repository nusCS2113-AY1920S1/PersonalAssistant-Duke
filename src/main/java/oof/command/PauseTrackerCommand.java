package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.model.tracker.Tracker;
import oof.model.tracker.TrackerList;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PauseTrackerCommand extends Command {
    private String module;

    public PauseTrackerCommand(String module) {
        super();
        this.module = module;
    }

    /**
     * Pauses Tracker timer.
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
            ui.printPauseAtCurrent(tracker, date, totalTime);
            resetStartEnd(tracker);
        }
    }

    /**
     * Check that a Start Time for Task object exists.
     * @param tracker   Tracker object.
     * @return          boolean True if Start Time for Task object is not null.
     */
    boolean isStarted(Tracker tracker) {
        return tracker.getStartDate() != null;
    }

    /**
     * Reset StartDate and EndDate values to NULL in Task object.
     * @param tracker      Task object.
     */
    void resetStartEnd(Tracker tracker) {
        tracker.setStartDate(null);
        tracker.setEndDate(null);
    }

    /**
     * Get difference between two Date.
     * @param tracker          Task object.
     * @param timeUnit      unit of Time to calculate by.
     * @return              difference between two Date.
     * @throws OofException if unable to retrieve Start or End time from Task object.
     */
    long getDateDiff(Tracker tracker, TimeUnit timeUnit) throws OofException {
        try {
            Date start = convertStringToDate(tracker.getStartDate());
            Date end = convertStringToDate(tracker.getEndDate());
            long diffInMillies = end.getTime() - start.getTime();
            return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            throw new OofException("Unable to retrieve time taken.");
        }
    }

    /**
     * Parse String to get Tracker Module.
     *
     * @param tracker Tracker object.
     * @return Module of Tracker object.
     */
    private String getModule(Tracker tracker) {
        String[] byDate = tracker.toString().split("\\(");
        String[] byDesc = byDate[0].split(" ", 2);
        return byDesc[1].trim();
    }

    /**
     * Find Tracker object in TrackerList where descriptions match.
     *
     * @param trackerList TrackerList object.
     * @return Tracker object that matches user given description.
     * @throws OofException if no matches are found.
     */
    Tracker findTracker(TrackerList trackerList, String description) throws OofException {
        Tracker tracker = null;
        for (int i = 0; i < trackerList.getSize(); i++) {
            String currentDesc = getModule(trackerList.getTracker(i));
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
