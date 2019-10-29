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
    private static final int ARGUMENT_FIRST = 0;
    private static final int ARGUMENT_SECOND = 1;
    private static final int MINIMUM_SIZE = 2;

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
            throw new OofException("Please enter the Assignment module code and description!");
        }

        String[] input = description.split(" ", 2);
        if (input.length < MINIMUM_SIZE) {
            throw new OofException("Invalid input!");
        }
        String moduleCode = input[ARGUMENT_FIRST].toLowerCase();
        String moduleDescription = input[ARGUMENT_SECOND];

        TrackerList trackerList = storage.readTrackerList();
        Tracker tracker = trackerList.findTrackerByDesc(moduleDescription, moduleCode);

        Assignment assignment = findAssignment(moduleDescription, moduleCode, tasks);
        if (assignment == null) {
            throw new OofException("Assignment Not Found!");
        }

        boolean isCompleted = isAssignmentCompleted(assignment);
        if (isCompleted) {
            throw new OofException("Assignment has already been completed.");

        }

        if (tracker == null) {
            tracker = addNewTracker(moduleDescription, moduleCode, tasks);
            trackerList.addTracker(tracker);
        } else {
            updateTrackerList(moduleDescription, moduleCode, trackerList);
        }
        storage.writeTrackerList(trackerList);
        ui.printStartAtCurrent(tracker);
    }

    /**
     * Check if Assignment is done.
     *
     * @param assignment    Assignment object.
     * @return              Assignment object status.
     */
    private boolean isAssignmentCompleted(Assignment assignment) {
        return assignment.getStatus();
    }

    /**
     * Find Assignment object in TaskList where descriptions match.
     *
     * @param moduleDescription     description of Assignment.
     * @param moduleCode            module code of Assignment.
     * @param taskList              TaskList of Task objects.
     * @return                      Assignment object that matches in both moduleDescription and moduleCode.
     */
    private Assignment findAssignment(String moduleDescription, String moduleCode, TaskList taskList) {
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            if (task instanceof Assignment) {
                Assignment assignment = (Assignment) task;
                String currentDescription = assignment.getDescription();
                String currentModuleCode = assignment.getModuleCode().toLowerCase();
                if (moduleDescription.equals(currentDescription) && moduleCode.equals(currentModuleCode)) {
                    return assignment;
                }
            }
        }
        return null;
    }

    /**
     * Create a new Tracker object.
     *
     * @param moduleDescription     module description given by User.
     * @param moduleCode            module code given by User.
     * @param tasks                 TaskList object.
     * @return                      new Tracker object.
     */
    private Tracker addNewTracker(String moduleDescription, String moduleCode, TaskList tasks) {
        for (int i = 0; i < tasks.getSize(); i++) {
            Task t = tasks.getTask(i);
            if (t instanceof Assignment) {
                Date current = new Date();
                return new Tracker(moduleCode, moduleDescription, current, current, DEFAULT_TIMETAKEN);
            }
        }
        return null;
    }

    /**
     * Updated Assignment that have been tracked in the past.
     *
     * @param description   module description given by User.
     * @param moduleCode    module code given by user.
     * @param trackerList   TrackerList of Tracker objects.
     */
    private void updateTrackerList(String description, String moduleCode, TrackerList trackerList) {
        for (int i = 0; i < trackerList.getSize(); i++) {
            Tracker tracker = trackerList.getTracker(i);
            String currentDesc = tracker.getDescription();
            String currentModuleCode = tracker.getModuleCode().toLowerCase();

            if (description.equals(currentDesc) && moduleCode.equals(currentModuleCode)) {
                tracker.setLastUpdated(new Date());
                tracker.setStartDate(new Date());
                break;
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
