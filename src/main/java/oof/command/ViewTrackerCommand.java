package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.tracker.ModuleTracker;
import oof.model.tracker.ModuleTrackerList;
import oof.model.tracker.Tracker;
import oof.model.tracker.TrackerList;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ViewTrackerCommand extends Command {
    private String period;
    private static final int EMPTY = 0;
    private static final int NOT_FOUND = -1;

    /**
     * Constructor of ViewTrackerCommand.
     */
    public ViewTrackerCommand(String period) {
        super();
        this.period = period;
    }

    /**
     * Queries a visual diagram featuring a histogram sharing the total amount of time
     * spent on each Module by user in blocks of 10 minutes.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if TrackerList is empty.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        TrackerList trackerList = storage.readTrackerList();
        ModuleTrackerList moduleTrackerList = timeSpentByModule(trackerList);
        if (moduleTrackerList.getSize() == EMPTY) {
            throw new OofException("There are no completed Assignments!");
        }
        ModuleTrackerList sortedTL = sortAscending(moduleTrackerList);
        ui.printTrackerDiagram(sortedTL);
    }

    /**
     * Calculate total time spent on various modules.
     * @param trackerList     list of Tracker objects.
     * @return          TrackerList of Tracker objects.
     */
    private ModuleTrackerList timeSpentByModule(TrackerList trackerList) {
        ModuleTrackerList moduleTrackerList = new ModuleTrackerList();
        Tracker tracker;

        for (int i = 0; i < trackerList.getSize(); i++) {
            tracker = trackerList.getTracker(i);
            String moduleCode = tracker.getModuleCode();
            ModuleTracker moduleTracker = updateModuleTrackerList(moduleTrackerList, tracker, moduleCode);
            moduleTrackerList.addModuleTracker(moduleTracker);
        }
        return moduleTrackerList;
    }

    /**
     * Update ModuleTracker object.
     * @param moduleTrackerList     ModuleTrackerList object.
     * @param tracker               Tracker object.
     * @param moduleCode            String containing module code.
     * @return                      created or updated ModuleTracker object.
     */
    private ModuleTracker updateModuleTrackerList(ModuleTrackerList moduleTrackerList,
                                                  Tracker tracker, String moduleCode) {
        ModuleTracker moduleTracker;
        long timeTaken = tracker.getTimeTaken();
        int i = matchModuleTracker(moduleTrackerList, moduleCode);

        if (tracker.getStartDate() != null) {
            long totalTime = tracker.getTimeTaken();
            Date startDate = tracker.getStartDate();
            totalTime += Integer.parseInt(tracker.getDateDiff(startDate));
            tracker.setTimeTaken(totalTime);
            timeTaken = tracker.getTimeTaken();
        }

        if (moduleTrackerList.getSize() == EMPTY || i == NOT_FOUND) {
            moduleTracker = new ModuleTracker(moduleCode,timeTaken);
        } else {

            moduleTracker = moduleTrackerList.getModuleTracker(i);
            long totalTime = moduleTracker.getTotalTimeTaken();
            totalTime += timeTaken;
            moduleTracker.setTotalTimeTaken(totalTime);
        }
        return moduleTracker;
    }

    /**
     * Check if ModuleTracker objects have the same ModuleCode.
     * @param moduleTrackerList     ModuleTrackerList of ModuleTracker objects.
     * @param moduleCode            Module code in process.
     * @return                      index of ModuleTracker object found in ModuleTrackerList that matches ModuleCode.
     */
    private int matchModuleTracker(ModuleTrackerList moduleTrackerList, String moduleCode) {
        for (int i = 0; i < moduleTrackerList.getSize(); i++) {
            ModuleTracker moduleTracker = moduleTrackerList.getModuleTracker(i);
            String savedModule = moduleTracker.getModuleCode();
            if (moduleCode.equals(savedModule)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Sort trackerList by timeTaken in ascending order.
     * @param moduleTrackerList   ArrayList of Tracker objects.
     * @return              sorted TrackerList object.
     */
    private ModuleTrackerList sortAscending(ModuleTrackerList moduleTrackerList) {
        ArrayList<ModuleTracker> list = new ArrayList<>();
        for (int i = 0; i < moduleTrackerList.getSize(); i++) {
            ModuleTracker mt = moduleTrackerList.getModuleTracker(i);
            list.add(mt);
        }
        Collections.sort(list, moduleTrackerList.timeTimeComparator);
        ModuleTrackerList sortedTL = new ModuleTrackerList();
        for (ModuleTracker moduleTracker : list) {
            sortedTL.addModuleTracker(moduleTracker);
        }
        return sortedTL;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
