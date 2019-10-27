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
     * @throws OofException if invalid Module Code detected.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, TrackerList trackerList, Ui ui, Storage storage)
            throws OofException {
        Tracker tracker = findTracker(trackerList, module);
        if (module.isEmpty()) {
            throw new OofException("Please enter a Module Code!");
        } else {
            Date now = new Date();
            String date = convertDateToString(now);
            tracker.setStartDate(date);
            storage.writeTrackerList(trackerList);
            ui.printStartAtCurrent(tracker, date, tracker.getTimeTaken());
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
