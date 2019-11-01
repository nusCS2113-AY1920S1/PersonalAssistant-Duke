package oof.command;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.Assignment;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.tracker.Tracker;
import oof.model.tracker.TrackerList;
import oof.storage.StorageManager;

public class TrackerCommand extends Command {

    public static final String COMMAND_WORD = "tracker";
    private String description;
    private static final long DEFAULT_TIME_TAKEN = 0;
    private static final int INDEX_INSTRUCTION = 0;
    private static final int INDEX_MODULE_CODE = 1;
    private static final int INDEX_DESCRIPTION = 2;
    private static final int SPLIT_INPUT = 3;
    private static final int SPLIT_DESCRIPTION = 2;
    private static final int NOT_FOUND = -1;
    private static final String START_COMMAND = "/start";
    private static final String STOP_COMMAND = "/stop";
    private static final String PAUSE_COMMAND = "/pause";
    private static final String VIEW_COMMAND = "/view";

    public TrackerCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter your instructions!");
        }

        TrackerList trackerList;
        try {
            trackerList = storageManager.readTrackerList();
        } catch (FileNotFoundException e) {
            trackerList = new TrackerList();
        }
        String[] input = description.split(" ", SPLIT_INPUT);
        String trackerCommand = input[INDEX_INSTRUCTION].toLowerCase();

        if (trackerCommand.equals(VIEW_COMMAND)) {
            TrackerList moduleTrackerList = timeSpentByModule(trackerList);
            if (moduleTrackerList.isEmpty()) {
                throw new OofException("There are no completed Assignments!");
            }
            TrackerList sortedTL = sortAscending(moduleTrackerList);
            ui.printTrackerDiagram(sortedTL);
        } else {
            if (input.length < SPLIT_INPUT) {
                throw new OofException("Invalid input!");
            }
            String moduleCode = input[INDEX_MODULE_CODE].toLowerCase();
            String moduleDescription = input[INDEX_DESCRIPTION].toLowerCase();
            Tracker tracker = trackerList.findTrackerByDesc(moduleDescription, moduleCode);
            Assignment assignment = findAssignment(moduleDescription, moduleCode, taskList);
            if (assignment == null) {
                throw new OofException("Assignment does not exist!");
            }

            boolean isCompleted = isAssignmentCompleted(assignment);
            if (isCompleted) {
                throw new OofException("Assignment has already been completed.");

            }

            switch (trackerCommand) {
            case START_COMMAND:
                if (tracker == null) {
                    tracker = addNewTracker(moduleDescription, moduleCode, taskList);
                    trackerList.addTracker(tracker);
                } else {
                    updateTrackerList(moduleDescription, moduleCode, trackerList);
                    storageManager.writeTrackerList(trackerList);
                    ui.printStartAtCurrent(tracker);
                }
                break;

            case STOP_COMMAND:
                if (!isStarted(tracker)) {
                    throw new OofException("Tracker for this Assignment has not started.");
                } else {
                    updateTimeTaken(tracker);
                    assignment.setStatus();
                    storageManager.writeTrackerList(trackerList);
                    storageManager.writeTaskList(taskList);
                    ui.printEndAtCurrent(tracker);
                }
                break;

            case PAUSE_COMMAND:
                if (!isStarted(tracker)) {
                    throw new OofException("Tracker for this Assignment has not started.");
                } else {
                    updateTimeTaken(tracker);
                    storageManager.writeTrackerList(trackerList);
                    ui.printPauseAtCurrent(tracker);
                }
                break;

            default:
                throw new OofException("Invalid Command!");
            }
        }
    }

    /**
     * Create a new Tracker object.
     *
     * @param moduleDescription     module description given by User.
     * @param moduleCode            module code given by User.
     * @param tasks                 TaskList object.
     * @return Tracker object.
     */
    private Tracker addNewTracker(String moduleDescription, String moduleCode, TaskList tasks) {
        for (int i = 0; i < tasks.getSize(); i++) {
            Task t = tasks.getTask(i);
            if (t instanceof Assignment) {
                Date current = new Date();
                return new Tracker(moduleCode, moduleDescription, current, current, DEFAULT_TIME_TAKEN);
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

    /**
     * Update Tracker object TimeTaken property.
     *
     * @param tracker   Tracker object.
     */
    private void updateTimeTaken(Tracker tracker) {
        long totalTime = tracker.getTimeTaken();
        Date now = new Date();
        Date startDate = tracker.getStartDate();
        totalTime += Integer.parseInt(tracker.getDateDiff(startDate));
        tracker.updateTracker(totalTime, now);
    }

    /**
     * Check if tracker has been started.
     *
     * @param tracker   Tracker object.
     * @return if Tracker object has a start date.
     */
    private boolean isStarted(Tracker tracker) {
        return tracker.getStartDate() != null;
    }

    /**
     * Check if Assignment is done.
     *
     * @param assignment    Assignment object.
     * @return Assignment object status.
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
     * @return Assignment object that matches in both moduleDescription and moduleCode.
     */
    private Assignment findAssignment(String moduleDescription, String moduleCode, TaskList taskList) {
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            if (task instanceof Assignment) {
                Assignment assignment = (Assignment) task;
                String[] description = assignment.getDescription().toLowerCase().split(" ", SPLIT_DESCRIPTION);
                String currentDescription = description[INDEX_DESCRIPTION];
                String currentModuleCode = assignment.getModuleCode().toLowerCase();
                if (moduleDescription.equals(currentDescription) && moduleCode.equals(currentModuleCode)) {
                    return assignment;
                }
            }
        }
        return null;
    }

    /**
     * Calculate total time spent on various modules.
     *
     * @param trackerList     list of Tracker objects.
     * @return TrackerList of Tracker objects.
     */
    private TrackerList timeSpentByModule(TrackerList trackerList) {
        TrackerList moduleTrackerList = new TrackerList();
        Tracker tracker;

        for (int i = 0; i < trackerList.getSize(); i++) {
            tracker = trackerList.getTracker(i);
            String moduleCode = tracker.getModuleCode();
            Tracker moduleTracker = updateModuleTrackerList(moduleTrackerList, tracker, moduleCode);
            moduleTrackerList.addTracker(moduleTracker);
        }
        return moduleTrackerList;
    }

    /**
     * Update Tracker object.
     *
     * @param moduleTrackerList     TrackerList object.
     * @param tracker               Tracker object.
     * @param moduleCode            String containing module code.
     * @return Tracker object.
     */
    private Tracker updateModuleTrackerList(TrackerList moduleTrackerList,
                                                  Tracker tracker, String moduleCode) {
        Tracker moduleTracker;
        long timeTaken = tracker.getTimeTaken();
        int i = matchModuleTracker(moduleTrackerList, moduleCode);

        if (tracker.getStartDate() != null) {
            long totalTime = tracker.getTimeTaken();
            Date startDate = tracker.getStartDate();
            totalTime += Integer.parseInt(tracker.getDateDiff(startDate));
            tracker.setTimeTaken(totalTime);
            timeTaken = tracker.getTimeTaken();
        }

        if (moduleTrackerList.isEmpty() || i == NOT_FOUND) {
            moduleTracker = new Tracker(moduleCode,timeTaken);
        } else {
            moduleTracker = moduleTrackerList.getTracker(i);
            long totalTime = moduleTracker.getTimeTaken();
            totalTime += timeTaken;
            moduleTracker.setTimeTaken(totalTime);
        }
        return moduleTracker;
    }

    /**
     * Check if Tracker objects have the same ModuleCode.
     *
     * @param moduleTrackerList     TrackerList of Tracker objects.
     * @param moduleCode            Module code in process.
     * @return index of Tracker object found in TrackerList that matches ModuleCode.
     */
    private int matchModuleTracker(TrackerList moduleTrackerList, String moduleCode) {
        for (int i = 0; i < moduleTrackerList.getSize(); i++) {
            Tracker moduleTracker = moduleTrackerList.getTracker(i);
            String savedModule = moduleTracker.getModuleCode();
            if (moduleCode.equals(savedModule)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Sort trackerList by timeTaken in ascending order.
     *
     * @param moduleTrackerList   ArrayList of Tracker objects.
     * @return TrackerList object.
     */
    private TrackerList sortAscending(TrackerList moduleTrackerList) {
        ArrayList<Tracker> list = new ArrayList<>();
        for (int i = 0; i < moduleTrackerList.getSize(); i++) {
            Tracker mt = moduleTrackerList.getTracker(i);
            list.add(mt);
        }
        Collections.sort(list, moduleTrackerList.timeTakenComparator);
        TrackerList sortedTrackerList = new TrackerList();
        for (Tracker moduleTracker : list) {
            sortedTrackerList.addTracker(moduleTracker);
        }
        return sortedTrackerList;
    }
}
