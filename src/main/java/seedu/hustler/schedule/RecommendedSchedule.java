package seedu.hustler.schedule;

import java.util.Collections;
import seedu.hustler.Hustler;
import seedu.hustler.ui.Ui;
import java.util.ArrayList;

/**
 * Extends on the Scheduler in order to provide recommendations
 * for the task completion mode.
 */
public class RecommendedSchedule extends Scheduler {
    
    /**
     * Recommended list of entries to work.
     */
    public static ArrayList<ScheduleEntry> recommended = new ArrayList<ScheduleEntry>();
    
    /**
     * Current amount of seconds available for work.
     */
    private static long seconds;
    
    /**
     * Initializes recommended based on the supplied time available and priority.
     * If the amount of hours availabe is 1, the highest priority task is given.
     * If the amount of hours is greater than 1 but less than two, two tasks are added.
     * Otherwise, 3 tasks are added based on priority.
     * Time is allocated evenly to all tasks.
     *
     * @param timeInSeconds time available for a task.
     */
    public static void recommend(long timeInSeconds) {
        seconds = timeInSeconds;
        Ui ui = new Ui();
        if (size() == 0) {
            ui.show_message("There are no tasks to complete. "
                + "Please add more tasks."); 
            return;
        }
        sort();
        double hours = timeInSeconds / 3600.0;

        if (hours <= 1) {
            recommended.add(schedule.get(0));
        } else if (hours > 1 && hours <= 2) {
            recommended.add(schedule.get(0)); 
            if (size() > 1) {
                recommended.add(schedule.get(1));
            }
        } else {
            recommended.add(schedule.get(0)); 
            if (size() == 2) {
                recommended.add(schedule.get(1));
            } else if (schedule.size() > 2) {
                recommended.add(schedule.get(1));
                recommended.add(schedule.get(2)); 
            }
        }
        reTime();
    }
    
    /**
     * Evenly distributes time to all recommended tasks.
     */
    public static void reTime() {
        for (ScheduleEntry entry : recommended) {
            entry.setTimeAlloc(seconds / recommended.size());
        }
    }
    
    /**
     * Sorts schedule based on priority for easy retreival.
     */
    public static void sort() {
        Collections.sort(schedule, new SortByPriority());
    }
    
    /**
     * Displays the recommended schedule and amount of time allocated to each entry.
     */
    public static void displayRecommendedSchedule() {
        String output = "Hey, this is your recommended schedule for the next "
            + "few hours. Please confirm if you will follow it. Please add or delete" 
            + " entries before updating time:\n\t";
        for (int i = 0; i < recommended.size(); i++) {
            long hours = recommended.get(i).getTimeAlloc() / 3600;
            long minutes = (recommended.get(i).getTimeAlloc() / 60) % 60;
            long seconds = recommended.get(i).getTimeAlloc() % 60;
            output += (i + 1) + ". " + recommended.get(i).getTask().toString() 
                + " time alloted: " + hours + ":" + minutes + ":" + seconds + "\n\t";
        }
        Ui ui = new Ui();
        ui.show_message(output);
    }

    /**
     * At the end of the timer, stores the amount of time spent in the
     * schedule.
     */
    public static void confirm() {
        seconds = 0;
        for (ScheduleEntry entry : recommended) {
            entry.updateTimeSpent(entry.getTimeAlloc());
        }
        recommended = new ArrayList<ScheduleEntry>();
        displayEntries();
    }

    /**
     * Adds tasks to the recommended based on index in the Hustler tasklist.
     *
     * @param index index of the task in the Hustler tasklist.
     */
    public static void addFromTaskList(int index) {
        for (ScheduleEntry entry : schedule) {
            if (entry.getTask() == Hustler.list.get(index)) {
                recommended.add(entry);
                break;
            }
        }
        reTime();
    }
    
    /**
     * Updates the allocated time to a particular entry in the recommended
     * list.
     *
     * @param index index of entry in recommended
     * @param timeInSeconds time allocated in seconds
     */
    public static void updateAllocTime(int index, long timeInSeconds) {
        System.out.println(index);
        System.out.println(timeInSeconds);
        recommended.get(index).setTimeAlloc(timeInSeconds);
    }
}
