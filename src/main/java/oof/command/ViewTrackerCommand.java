package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.tracker.TrackerList;
import oof.model.module.SemesterList;
import oof.model.task.Assignment;
import oof.model.task.Task;
import oof.model.task.TaskList;

import java.util.ArrayList;
import java.util.Collections;

public class ViewTrackerCommand extends Command {
    private TrackerList trackerList = new TrackerList();
    private static final long DEFAULT_TIMETAKEN = 0;
    private static final String TASK_DESCRIPTION = "Others";
    private String period;

    /**
     * Constructor of ViewTrackerCommand.
     */
    public ViewTrackerCommand(String period) {
        super();
        this.period = period;
    }

    /**
     * Calculate total time spent on various modules.
     * @param tasks     list of Task objects.
     * @return          TrackerList of TaskTracker objects.
     */
    private TrackerList timeSpentByModule(TaskList tasks) {
        TrackerList trackerList = new TrackerList();
        oof.model.tracker.Tracker tracker = new oof.model.tracker.Tracker(TASK_DESCRIPTION, DEFAULT_TIMETAKEN);
        trackerList.addTracker(tracker);

        for (int i = 0; i < tasks.getSize(); i++) {
            long totalTime = 0;
            boolean isFound = false;
            Task t = tasks.getTask(i);

            if (t instanceof Assignment) {
                Assignment a = (Assignment) t;

                for (int j = 0; j < trackerList.getSize(); j++) {
                    tracker = trackerList.getTracker(j);
                    String module = t.getDescription();
                    if (module.equals(a.getModuleCode())) {
                        totalTime += tracker.getTimeTaken();
                        trackerList.getTracker(j).setTimeTaken(totalTime);
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    tracker.setModule(a.getModuleCode());
                    totalTime = tracker.getTimeTaken();
                    tracker.setTimeTaken(totalTime);
                    trackerList.addTracker(tracker);
                }
            } else {
                for (int j = 0; j < trackerList.getSize(); j++) {
                    String module = trackerList.getTracker(j).getModule();
                    if (module.equals("Others")) {
                        totalTime = trackerList.getTracker(j).getTimeTaken();
                        totalTime += tracker.getTimeTaken();
                        trackerList.getTracker(j).setTimeTaken(totalTime);
                        break;
                    }
                }
            }
        }
        return trackerList;
    }

    /**
     * Sort trackerList by timeTaken in ascending order.
     * @param trackerList   ArrayList of Tracker objects.
     * @return              sorted TrackerList object.
     */
    private TrackerList sortAscending(TrackerList trackerList) {
        ArrayList<oof.model.tracker.Tracker> list = new ArrayList<>();
        for (int i = 0; i < trackerList.getSize(); i++) {
            oof.model.tracker.Tracker t = trackerList.getTracker(i);
            list.add(t);
        }
        Collections.sort(list, trackerList.timeTaken);
        TrackerList sortedTL = new TrackerList();
        for (int i = 0; i < list.size(); i++) {
            sortedTL.addTracker(list.get(i));
        }
        return sortedTL;
    }

    /**
     * Queries a visual diagram featuring a histogram sharing the total amount of time spent on each Module by user.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param trackerList  Instance of TrackerList that stores Tracker objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if TrackerList is empty.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, TrackerList trackerList, Ui ui, Storage storage)
            throws OofException {
        trackerList = timeSpentByModule(tasks);
        if (trackerList.getSize() == 0) {
            throw new OofException("There are no completed Tasks!");
        }
        TrackerList sortedTL = sortAscending(trackerList);
        System.out.println("Overall Sorted List: " + sortedTL); // debug
        ui.printTrackerDiagram(sortedTL);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
