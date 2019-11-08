package oof.logic.command.productivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Comparator;

import oof.logic.command.Command;
import oof.ui.Ui;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.commons.exceptions.command.MissingArgumentException;
import oof.logic.command.organization.exceptions.TaskAlreadyCompletedException;
import oof.logic.command.productivity.exceptions.TrackerNotFoundException;
import oof.commons.exceptions.StorageFileCorruptedException;
import oof.model.university.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.tracker.Tracker;
import oof.storage.StorageManager;

import javax.sound.midi.Track;

public class TrackerCommand extends Command {

    public static final String COMMAND_WORD = "tracker";
    private String description;
    private static final long DEFAULT_TIMETAKEN = 0;
    private static final int INDEX_INSTRUCTION = 0;
    private static final int PERIOD_INDEX = 1;
    private static final int DELETE_INDEX = 1;
    private static final int TASK_INDEX = 1;
    private static final int CORRECT_INDEX = 1;
    private static final int MODULE_CODE_INDEX = 2;
    private static final int SPLIT_INPUT = 3;
    private static final int NOT_FOUND = -1;
    private static final int VIEW_COMMAND_LENGTH = 2;
    private static final int DELETE_COMMAND_LENGTH = 2;
    private static final int TIMER_COMMAND_LENGTH = 3;
    private static final String PERIOD_DAY = "day";
    private static final String PERIOD_WEEK = "week";
    private static final String PERIOD_ALL = "all";
    private static final String START_COMMAND = "/start";
    private static final String STOP_COMMAND = "/stop";
    private static final String PAUSE_COMMAND = "/pause";
    private static final String VIEW_COMMAND = "/view";
    private static final String DELETE_COMMAND = "/delete";
    private static final String LIST_COMMAND = "/list";

    public TrackerCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (description.isEmpty()) {
            throw new MissingArgumentException("Please enter your instructions!");
        }

        ArrayList<Tracker> trackerList;
        try {
            trackerList = storageManager.readTrackerList();
        } catch (NullPointerException | StorageFileCorruptedException e) {
            trackerList = new ArrayList<>();
        }

        String[] input = description.split(" ", SPLIT_INPUT);
        String trackerCommand = input[INDEX_INSTRUCTION].toLowerCase();
        Tracker tracker;

        switch (trackerCommand) {
        case VIEW_COMMAND:
            if (input.length != VIEW_COMMAND_LENGTH) {
                throw new InvalidArgumentException("Invalid Commmand!");
            }
            String period = input[PERIOD_INDEX].toLowerCase();
            ArrayList<Tracker> sortedTL = processModuleTrackerList(period, trackerList);
            long totalTimeTaken = calculateTotalTime(sortedTL);
            ui.printTrackerDiagram(sortedTL, totalTimeTaken);
            break;

        case LIST_COMMAND:
            ui.printTrackerList(trackerList);
            break;

        case DELETE_COMMAND:
            if (input.length != DELETE_COMMAND_LENGTH) {
                throw new InvalidArgumentException("Invalid Commmand!");
            }
            int deleteIndex = Integer.parseInt(input[DELETE_INDEX]) - CORRECT_INDEX;
            tracker = trackerList.get(deleteIndex);
            String description = tracker.getDescription();
            long timeTaken = tracker.getTimeTaken();
            trackerList.remove(deleteIndex);
            int size = trackerList.size();
            ui.printTrackerDelete(size, description, timeTaken);
            storageManager.writeTrackerList(trackerList);
            break;

        default:
            if (input.length != TIMER_COMMAND_LENGTH) {
                throw new InvalidArgumentException("Invalid Command!");
            }
            String moduleCode = input[MODULE_CODE_INDEX].toLowerCase();
            int taskIndex = Integer.parseInt(input[TASK_INDEX]);
            taskIndex -= CORRECT_INDEX;
            if (taskIndex > taskList.getSize()) {
                throw new InvalidArgumentException("Invalid Task Index!");
            }

            tracker = findTrackerByTaskIndex(trackerList, taskIndex);
            Task task = findTask(taskIndex, taskList);
            boolean isCompleted = task.getStatus();
            if (isCompleted) {
                throw new TaskAlreadyCompletedException("Task has already been completed.");
            }

            boolean isValidDescription = isValidDescription(taskIndex, trackerList, taskList);
            switch (trackerCommand) {
            case START_COMMAND:
                if (tracker == null) {
                    tracker = addNewTracker(taskIndex, moduleCode, taskList);
                    trackerList.add(tracker);
                } else {
                    if (!isValidDescription) {
                        throw new InvalidArgumentException("Task descriptions do not match!");
                    }
                    updateTrackerList(taskIndex, moduleCode, trackerList);
                }
                storageManager.writeTrackerList(trackerList);
                ui.printStartAtCurrent(tracker, taskList);
                break;

            case STOP_COMMAND:
                if (tracker == null || tracker.isNotStarted()) {
                    throw new TrackerNotFoundException("Tracker for this Assignment has not started.");
                } else {
                    if (!isValidDescription) {
                        throw new InvalidArgumentException("Task descriptions do not match!");
                    }
                    updateTimeTaken(tracker);
                    task.setStatus();
                    storageManager.writeTrackerList(trackerList);
                    storageManager.writeTaskList(taskList);
                    ui.printEndAtCurrent(tracker, taskList);
                }
                break;

            case PAUSE_COMMAND:
                if (tracker == null || tracker.isNotStarted()) {
                    throw new TrackerNotFoundException("Tracker for this Assignment has not started.");
                } else {
                    if (!isValidDescription) {
                        throw new InvalidArgumentException("Task descriptions do not match!");
                    }
                    updateTimeTaken(tracker);
                    storageManager.writeTrackerList(trackerList);
                    ui.printPauseAtCurrent(tracker, taskList);
                }
                break;

            default:
                throw new InvalidArgumentException("Invalid Command!");
            }
        }
    }

    /**
     * Process Tracker data by Module Code.
     *
     * @param period        limiting period of time given by user.
     * @param trackerList   ArrayList of Tracker objects.
     * @return ArrayList of Tracker objects grouped by Module Code and limited by given period.
     * @throws CommandException if period given by user is invalid or if no data is available within given period.
     */
    private ArrayList<Tracker> processModuleTrackerList(
            String period, ArrayList<Tracker> trackerList) throws CommandException {
        ArrayList<Tracker> moduleTrackerList;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (PERIOD_DAY.equals(period)) {
            Date date = new Date();
            Date today;
            try {
                today = dateFormat.parse(dateFormat.format(date));
            } catch (ParseException e) {
                throw new InvalidArgumentException("Unable to parse Date.");
            }
            moduleTrackerList = timeSpentByModule(trackerList, today);
        } else if (PERIOD_WEEK.equals(period)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            Date date = cal.getTime();
            Date startPeriod;
            try {
                startPeriod = dateFormat.parse(dateFormat.format(date));
            } catch (ParseException e) {
                throw new InvalidArgumentException("Unable to parse Date.");
            }
            moduleTrackerList = timeSpentByModule(trackerList, startPeriod);
        } else if (PERIOD_ALL.equals(period)) {
            moduleTrackerList = timeSpentByModule(trackerList);
        } else {
            throw new InvalidArgumentException("Invalid Period!");
        }
        if (moduleTrackerList.isEmpty()) {
            throw new TrackerNotFoundException("No tracked data available. Please begin by tracking a Task!");
        }
        sortAscending(moduleTrackerList);
        return moduleTrackerList;
    }

    /**
     * Calculate total amount of time taken over all modules.
     *
     * @param trackerList  ArrayList of Tracker objects.
     * @return total amount of time spent on all modules.
     */
    private long calculateTotalTime(ArrayList<Tracker> trackerList) {
        long totalTimeTaken = 0;
        for (Tracker tracker : trackerList) {
            totalTimeTaken += tracker.getTimeTaken();
        }
        return totalTimeTaken;
    }

    /**
     * Find Tracker object in ArrayList of Tracker objects where index match.
     *
     * @param trackerList   ArrayList of Tracker objects.
     * @param taskIndex     Index of Task object given by user.
     * @return Tracker object that matches user given index.
     */
    private Tracker findTrackerByTaskIndex(ArrayList<Tracker> trackerList, int taskIndex) {
        for (Tracker tracker : trackerList) {
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
        return new Tracker(moduleCode, taskIndex, desc, current, current, DEFAULT_TIMETAKEN);
    }

    /**
     * Updated Assignment that have been tracked in the past.
     *
     * @param taskIndex     task index given by User.
     * @param moduleCode    module code given by user.
     * @param trackerList   TrackerList of Tracker objects.
     */
    private void updateTrackerList(int taskIndex, String moduleCode, ArrayList<Tracker> trackerList) {
        for (Tracker currTracker : trackerList) {
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
        Task currTask = taskList.getTask(taskIndex);
        String taskDescription = currTask.getDescription();
        for (Tracker tracker : trackerList) {
            int index = tracker.getTaskIndex();
            String desc = tracker.getDescription();
            if (taskDescription.equals(desc) && index == taskIndex) {
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
        for (Tracker tracker : trackerList) {
            String moduleCode = tracker.getModuleCode();
            Tracker moduleTracker = updateModuleTrackerList(moduleTrackerList, tracker, moduleCode);
            moduleTrackerList.add(moduleTracker);
        }
        return moduleTrackerList;
    }

    /**
     * Calculate total time spent on various modules based on a time period.
     *
     * @param trackerList   list of Tracker objects.
     * @param period        limiting Date given by user.
     * @return TrackerList of Tracker objects.
     */
    private ArrayList<Tracker> timeSpentByModule(ArrayList<Tracker> trackerList, Date period) {
        ArrayList<Tracker> moduleTrackerList = new ArrayList<>();
        for (Tracker tracker : trackerList) {
            Date currDate = tracker.getLastUpdated();
            if (period.compareTo(currDate) <= 0) {
                String moduleCode = tracker.getModuleCode();
                Tracker moduleTracker = updateModuleTrackerList(moduleTrackerList, tracker, moduleCode);
                moduleTrackerList.add(moduleTracker);
            }
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
     */
    private void sortAscending(ArrayList<Tracker> moduleTrackerList) {
        moduleTrackerList.sort(timeTakenComparator);
    }

    /**
     * Compare time taken property between two Tracker objects.
     */
    private static Comparator<Tracker> timeTakenComparator = new Comparator<>() {
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
