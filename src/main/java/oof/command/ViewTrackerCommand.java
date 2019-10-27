package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.Tracker;
import oof.model.TrackerList;
import oof.model.module.SemesterList;
import oof.model.task.Assignment;
import oof.model.task.Task;
import oof.model.task.TaskList;

import java.util.ArrayList;
import java.util.Collections;

public class ViewTrackerCommand extends Command {
    private String period;
    private TrackerList trackerList = new TrackerList();
    private static final long DEFAULT_TIMETAKEN = 0;
    private static final String TASK_DESCRIPTION = "Others";

    /**
     * Constructor of ViewTrackerCommand.
     * @param period    time period as a String.
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
        Tracker tracker = null;
        tracker.add(TASK_DESCRIPTION, DEFAULT_TIMETAKEN);
        trackerList.addTracker(tracker);

        for (int i = 0; i < tasks.getSize(); i++) {
            long totalTime = 0;
            String timeTaken;
            boolean isFound = false;
            Task t = tasks.getTask(i);

            if (t instanceof Assignment) {
                Assignment a = (Assignment) t;

                for (int j = 0; j < trackerList.getSize(); j++) {
                    tracker = trackerList.getTracker(j);
                    String description = tracker.getTaskDescription();
                    if (description.equals(a.getModuleCode())) {
                        totalTime += t.getTimeTaken();
                        trackerList.getTracker(j).setTotalTimeTaken(totalTime);
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    totalTime = t.getTimeTaken();
                    tracker.add(a.getModuleCode(), totalTime);
                    trackerList.addTracker(tracker);
                }
            } else {
                for (int j = 0; j < trackerList.getSize(); j++) {
                    String description = trackerList.getTracker(j).getTaskDescription();
                    if (description.equals("Others")) {
                        totalTime = trackerList.getTracker(j).getTotalTimeTaken();
                        totalTime += t.getTimeTaken();
                        trackerList.getTracker(j).setTotalTimeTaken(totalTime);
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
        ArrayList<Tracker> list = new ArrayList<>();
        for (int i = 0; i < trackerList.getSize(); i++) {
            Tracker t = trackerList.getTracker(i);
            list.add(t);
        }
        Collections.sort(list, trackerList.timeTaken);
        TrackerList sortedTL = new TrackerList();
        for (int i = 0; i < list.size(); i++) {
            sortedTL.addTracker(list.get(i));
        }
        return sortedTL;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
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
