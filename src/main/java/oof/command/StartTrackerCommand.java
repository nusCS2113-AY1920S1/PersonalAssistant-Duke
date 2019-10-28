package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.Assignment;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.tracker.Tracker;
import oof.Ui;
import oof.exception.OofException;
import oof.model.tracker.TrackerList;

import java.util.Date;

/**
 * Represents a Command to start an Assignment Task tracker.
 */
public class StartTrackerCommand extends Command {
    private String description;
    private static final long DEFAULT_TIMETAKEN = 0;

    /**
     * Constructor for StartTrackerCommand.
     *
     * @param description of Assignment Task to start tracking.
     */
    public StartTrackerCommand(String description) {
        super();
        this.description = description;
    }

    /**
     * Starts Tracker timer.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if invalid Module Code detected or Tracker timer has already started.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter an Assignment description!");
        } else {
            TrackerList trackerList = storage.readTrackerList();
            Tracker tracker = trackerList.findTrackerByDesc(description);
            if (tracker == null) {
                boolean isFound = false;
                for (int i = 0; i < tasks.getSize(); i++) {
                    Task t = tasks.getTask(i);
                    if (t instanceof Assignment) {
                        Assignment assignment = findAssignment(description, tasks);
                        Date current = new Date();
                        String moduleCode = assignment.getModuleCode();
                        String deadline = assignment.getDeadlineDateTime();

                        tracker = new Tracker(moduleCode, description, deadline, current, current, DEFAULT_TIMETAKEN);
                        trackerList.addTracker(tracker);
                        storage.writeTrackerList(trackerList);
                        ui.printStartAtCurrent(tracker);
                        isFound = true;
                        break;

                    }
                }
                if (!isFound) {
                    throw new OofException("Invalid Assignment description!");
                }
            } else {
                updateTrackerList(trackerList);
                storage.writeTrackerList(trackerList);
                ui.printStartAtCurrent(tracker);
            }
        }
    }

    /**
     * Updated Assignment that have been tracked in the past.
     *
     * @param trackerList   TrackerList of Tracker objects.
     */
    private void updateTrackerList(TrackerList trackerList) {
        for (int i = 0; i < trackerList.getSize(); i++) {
            Tracker tracker = trackerList.getTracker(i);
            if (description.equals(tracker.getDescription())) {
                trackerList.getTracker(i).setLastUpdated(new Date());
                trackerList.getTracker(i).setStartDate(new Date());
                break;
            }
        }
    }

    /**
     * Find Assignment object in TaskList where descriptions match.
     * @param description   description of Task.
     * @param taskList      TaskList of Task objects.
     * @return              Assignment object with description that matches given description.
     */
    private Assignment findAssignment(String description, TaskList taskList) {
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            if (task instanceof Assignment) {
                Assignment assignment = (Assignment) task;
                if (description.equals(assignment.getDescription())) {
                    return assignment;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
