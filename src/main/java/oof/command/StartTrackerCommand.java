package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.model.tracker.Tracker;
import oof.Ui;
import oof.exception.OofException;
import oof.model.tracker.TrackerList;

import java.util.Date;

/**
 * Represents a Command to start a task tracker.
 */
public class StartTrackerCommand extends Command {
    private String module;
    private static final long DEFAULT_TIMETAKEN = 0;

    /**
     * Constructor for StartTrackerCommand.
     *
     * @param description of Task to start tracking.
     */
    public StartTrackerCommand(String description) {
        super();
        this.module = description;
    }

    /**
     * Starts Tracker timer.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param trackerList  Instance of TrackerList that stores Tracker objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if invalid Module Code detected or Tracker timer has already started.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, TrackerList trackerList, Ui ui, Storage storage)
            throws OofException {
        Tracker tracker;
        if (module.isEmpty()) {
            throw new OofException("Please enter a Module Code!");
        } else {
            boolean isRegistered = isFound(trackerList, module);
            if (isRegistered) {
                tracker = findTracker(trackerList, module);
                if (tracker.getStartDate() != null) {
                    throw new OofException("Tracker has already started!");
                }
            } else {
                tracker = new Tracker(module, DEFAULT_TIMETAKEN);
                trackerList.addTracker(tracker);
            }
            Date now = new Date();
            String date = convertDateToString(now);
            tracker.setStartDate(date);
            storage.writeTrackerList(trackerList);
            ui.printStartAtCurrent(tracker, date, tracker.getTimeTaken());
        }
    }

    private boolean isFound(TrackerList trackerList, String module) {
        for (int i = 0; i < trackerList.getSize(); i++) {
            Tracker tracker = trackerList.getTracker(i);
            if (module.equals(tracker.getModule())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find Tracker object in TrackerList where descriptions match.
     *
     * @param trackerList TrackerList object.
     * @return Tracker object that matches user given description.
     */
    private Tracker findTracker(TrackerList trackerList, String description) {
        Tracker tracker = null;
        for (int i = 0; i < trackerList.getSize(); i++) {
            String currentDesc = trackerList.getTracker(i).getModule();
            if (description.equals(currentDesc)) {
                tracker = trackerList.getTracker(i);
                break;
            }
        }
        return tracker;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
