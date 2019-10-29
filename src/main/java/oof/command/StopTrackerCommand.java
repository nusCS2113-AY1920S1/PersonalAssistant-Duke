package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.Assignment;
import oof.model.task.Task;
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
    private static final int ARGUMENT_FIRST = 0;
    private static final int ARGUMENT_SECOND = 1;
    private static final int MINIMUM_SIZE = 2;

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

        } else if (!isStarted(tracker)) {
            throw new OofException("Tracker for this Assignment has not started.");

        } else {
            long totalTime = tracker.getTimeTaken();
            Date now = new Date();
            Date startDate = tracker.getStartDate();
            totalTime += Integer.parseInt(tracker.getDateDiff(startDate));

            tracker.updateTracker(totalTime, now);
            assignment.setStatus();
            storage.writeTrackerList(trackerList);
            storage.writeTaskList(tasks);
            ui.printEndAtCurrent(tracker);
        }
    }

    /**
     * Check if tracker has been started.
     *
     * @param tracker   Tracker object.
     * @return          if Tracker object has a start date.
     */
    private boolean isStarted(Tracker tracker) {
        return tracker.getStartDate() != null;
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

    @Override
    public boolean isExit() {
        return false;
    }
}
