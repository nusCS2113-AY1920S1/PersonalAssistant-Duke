package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.tracker.Tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class TrackerCommand extends Command {
    String description;
    private static final long DEFAULT_TIMETAKEN = 0;
    private static final int INDEX_INSTRUCTION = 0;
    private static final int TASK_INDEX = 1;
    private static final int CORRECT_INDEX = 1;
    private static final int MODULE_CODE_INDEX = 2;
    private static final int SPLIT_INPUT = 3;
    private static final int NOT_FOUND = -1;
    private static final String START_COMMAND = "/start";
    private static final String STOP_COMMAND = "/stop";
    private static final String PAUSE_COMMAND = "/pause";
    private static final String VIEW_COMMAND = "/view";

    public TrackerCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter your instructions!");
        }

        ArrayList<Tracker> trackerList = storage.readTrackerList();
        String[] input = description.split(" ", SPLIT_INPUT);
        String trackerCommand = input[INDEX_INSTRUCTION].toLowerCase();

        if (trackerCommand.equals(VIEW_COMMAND)) {
            ArrayList<Tracker> moduleTrackerList = timeSpentByModule(trackerList);
            if (moduleTrackerList.isEmpty()) {
                throw new OofException("No tracked data available. Please begin by tracking a Task!");
            }
            ArrayList<Tracker> sortedTL = sortAscending(moduleTrackerList);
            long totalTimeTaken = calculateTotalTime(sortedTL);
            ui.printTrackerDiagram(sortedTL, totalTimeTaken);
        } else {
            String moduleCode = input[MODULE_CODE_INDEX].toLowerCase();
            int taskIndex = Integer.parseInt(input[TASK_INDEX]);
            taskIndex -= CORRECT_INDEX;
            if (taskIndex > taskList.getSize()) {
                throw new OofException("Invalid Task Index!");
            }

            Tracker tracker = findTrackerByTaskIndex(trackerList, taskIndex);
            Task task = findTask(taskIndex, taskList);
            boolean isCompleted = task.getStatus();
            if (isCompleted) {
                throw new OofException("Task has already been completed.");
            }

            boolean isValidDescription = isValidDescription(taskIndex, trackerList, taskList);
            switch (trackerCommand) {
            case START_COMMAND:
                if (tracker == null) {
                    tracker = addNewTracker(taskIndex, moduleCode, taskList);
                    trackerList.add(tracker);
                } else {
                    if (!isValidDescription) {
                        throw new OofException("Task descriptions do not match!");
                    }
                    updateTrackerList(taskIndex, moduleCode, trackerList);
                }
                storage.writeTrackerList(trackerList);
                ui.printStartAtCurrent(tracker, taskList);
                break;

            case STOP_COMMAND:
                if (!isStarted(tracker)) {
                    throw new OofException("Tracker for this Assignment has not started.");
                } else {
                    if (!isValidDescription) {
                        throw new OofException("Task descriptions do not match!");
                    }
                    updateTimeTaken(tracker);
                    task.setStatus();
                    storage.writeTrackerList(trackerList);
                    storage.writeTaskList(taskList);
                    ui.printEndAtCurrent(tracker, taskList);
                }
                break;

            case PAUSE_COMMAND:
                if (!isStarted(tracker)) {
                    throw new OofException("Tracker for this Assignment has not started.");
                } else {
                    if (!isValidDescription) {
                        throw new OofException("Task descriptions do not match!");
                    }
                    updateTimeTaken(tracker);
                    storage.writeTrackerList(trackerList);
                    ui.printPauseAtCurrent(tracker, taskList);
                }
                break;

            default:
                throw new OofException("Invalid Command!");
            }
        }
    }

    /**
     * Calculate total amount of time taken over all modules.
     *
     * @param list  ArrayList of Tracker objects.
     * @return total amount of time spent on all modules.
     */
    private long calculateTotalTime(ArrayList<Tracker> list) {
        long totalTimeTaken = 0;
        for (int i = 0; i < list.size(); i++) {
            Tracker tracker = list.get(i);
            totalTimeTaken += tracker.getTimeTaken();
        }
        return totalTimeTaken;
    }

    /**
     * Find Tracker object in ArrayList of Tracker objects where index match.
     *
     * @return Tracker object that matches user given index.
     */
    public Tracker findTrackerByTaskIndex(ArrayList<Tracker> list, int taskIndex) {
        for (int i = 0; i < list.size(); i++) {
            Tracker tracker = list.get(i);
            int currIndex = tracker.getTaskIndex();
            if (taskIndex == currIndex) {
                return tracker;
            }
        }
        return null;
    }

    /**
     * Create a new Tracker object.
     *
     * @param taskIndex     task index given by User.
     * @param moduleCode    module code given by User.
     * @param tasks         TaskList object.
     * @return a newly created Tracker object.
     */
    private Tracker addNewTracker(int taskIndex, String moduleCode, TaskList tasks) {
        Task t = tasks.getTask(taskIndex);
        Date current = new Date();
        String desc = t.getDescription();
        Tracker tracker = new Tracker(moduleCode, taskIndex, desc, current, current, DEFAULT_TIMETAKEN);
        return tracker;
    }

    /**
     * Updated Assignment that have been tracked in the past.
     *
     * @param taskIndex     task index given by User.
     * @param moduleCode    module code given by user.
     * @param trackerList   TrackerList of Tracker objects.
     */
    private void updateTrackerList(int taskIndex, String moduleCode, ArrayList<Tracker> trackerList) {
        for (int i = 0; i < trackerList.size(); i++) {
            Tracker currTracker = trackerList.get(i);
            int currentIndex = currTracker.getTaskIndex();
            String currentModuleCode = currTracker.getModuleCode().toLowerCase();
            if (taskIndex == currentIndex && moduleCode.equals(currentModuleCode)) {
                currTracker.setLastUpdated(new Date());
                currTracker.setStartDate(new Date());
                break;
            }
        }
    }

    /**
     * Check if Task description matches tracked Task description.
     *
     * @param taskIndex     index of Task given by user.
     * @param trackerList   ArrayList of Tracker objects.
     * @param taskList      TaskList object.
     * @return true if Task description matches tracked Task description.
     */
    private boolean isValidDescription(int taskIndex, ArrayList<Tracker> trackerList, TaskList taskList) {
        if (trackerList.isEmpty()) {
            return true;
        }
        Task task = taskList.getTask(taskIndex);
        String taskDescription = task.getDescription();
        for (Tracker tracker : trackerList) {
            String desc = tracker.getDescription();
            if (taskDescription.equals(desc)) {
                return true;
            }
        }
        return false;
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

    private Task findTask(int taskIndex, TaskList taskList) {
        return taskList.getTask(taskIndex);
    }

    /**
     * Calculate total time spent on various modules.
     *
     * @param trackerList     list of Tracker objects.
     * @return TrackerList of Tracker objects.
     */
    private ArrayList<Tracker> timeSpentByModule(ArrayList<Tracker> trackerList) {
        ArrayList<Tracker> moduleTrackerList = new ArrayList<>();
        Tracker tracker;

        for (int i = 0; i < trackerList.size(); i++) {
            tracker = trackerList.get(i);
            String moduleCode = tracker.getModuleCode();
            Tracker moduleTracker = updateModuleTrackerList(moduleTrackerList, tracker, moduleCode);
            moduleTrackerList.add(moduleTracker);
        }
        return moduleTrackerList;
    }

    /**
     * Update Tracker object.
     *
     * @param moduleTrackerList     ArrayList of Tracker objects.
     * @param tracker               Tracker object.
     * @param moduleCode            String containing module code.
     * @return Tracker object.
     */
    private Tracker updateModuleTrackerList(ArrayList<Tracker> moduleTrackerList,
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
            moduleTracker = moduleTrackerList.get(i);
            long totalTime = moduleTracker.getTimeTaken();
            totalTime += timeTaken;
            moduleTracker.setTimeTaken(totalTime);
        }
        return moduleTracker;
    }

    /**
     * Check if Tracker objects have the same ModuleCode.
     *
     * @param moduleTrackerList     ArrayList of Tracker objects.
     * @param moduleCode            Module code in process.
     * @return index of Tracker object found in moduleTrackerList that matches ModuleCode.
     */
    private int matchModuleTracker(ArrayList<Tracker> moduleTrackerList, String moduleCode) {
        for (int i = 0; i < moduleTrackerList.size(); i++) {
            Tracker moduleTracker = moduleTrackerList.get(i);
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
     * @return sorted ArrayList of Tracker objects.
     */
    private ArrayList<Tracker> sortAscending(ArrayList<Tracker> moduleTrackerList) {
        ArrayList<Tracker> sortedTrackerList = new ArrayList<>();
        for (int i = 0; i < moduleTrackerList.size(); i++) {
            Tracker mt = moduleTrackerList.get(i);
            sortedTrackerList.add(mt);
        }
        Collections.sort(sortedTrackerList, timeTakenComparator);
        return sortedTrackerList;
    }

    /**
     * Compare time taken property between two Tracker objects.
     */
    public static Comparator<Tracker> timeTakenComparator = new Comparator<>() {
        /**
         * Compare time taken property between two Tracker objects.
         * @param mt1    Tracker object.
         * @param mt2    Tracker object.
         * @return      difference between time taken property between two Tracker objects.
         */
        public int compare(Tracker mt1, Tracker mt2) {
            int timeTaken1 = (int) mt1.getTimeTaken();
            int timeTaken2 = (int) mt2.getTimeTaken();
            return timeTaken1 - timeTaken2;
        }
    };
}
