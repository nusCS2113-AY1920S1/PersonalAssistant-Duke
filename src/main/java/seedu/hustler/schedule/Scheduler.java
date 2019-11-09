package seedu.hustler.schedule;

import seedu.hustler.Hustler;
import java.util.ArrayList;
import seedu.hustler.task.Task;
import seedu.hustler.ui.Ui;

/**
 * A class that holds all the
 * tasks to complete, time spent on each task,
 * and priority of each task, which is together called
 * an entry. Scheduler handles all these entries.
 */
public class Scheduler {
    /**
     * An ArrayList that stores entries
     * that are tasks to complete with the amount
     * of time spent on each task and their computed
     * priority.
     */
    public static ArrayList<ScheduleEntry> schedule 
        = new ArrayList<>();

    /**
     * An object that uses top-k algorithm
     * based on priority to initialize recommended list.
     */
    public static Recommender recommender;

    /**
     * An ArrayList of entries extracted based on priorty
     * from schedule.
     */
    public static ArrayList<ScheduleEntry> recommended 
        = new ArrayList<>();

    /**
     * Add an entry to the schedule based on the task
     * supplied as input, only if the task is not done.
     *
     * @param task task to be added as entry
     */
    public static void add(Task task) {
        if (task.isCompleted()) {
            return;
        }
        schedule.add(new ScheduleEntry(task, 0));
    }
    
    /**
     * Adds task as an entry and also configuring a preset
     * amount of time spent on the task.
     *
     * @param task task to be added as entry
     * @param timeSpent amount of time spent on each task
     */
    public static void add(Task task, long timeSpent) {
        if (task.isCompleted()) {
            return;
        }
        schedule.add(new ScheduleEntry(task, timeSpent));
    }
    
    /**
     * Returns an entry based on supplied index.
     *
     * @param index index of the wanted entry
     * @return entry that contains the task, time spent and priority of the task
     */
    public static ScheduleEntry getEntry(int index) {
        return schedule.get(index);
    }
    
    /**
     * Adds seconds to the time spent on a task in the schedule.
     *
     * @param index index of the entry to update
     * @param seconds seconds to be added to the time spent on the task
     */
    public static void updateEntry(int index, long seconds) {
        schedule.get(index).updateTimeSpent(seconds);
    }
    
    /**
     * Returns the list of entries.
     *
     * @return list of entries
     */
    public static ArrayList<ScheduleEntry> getList() {
        return schedule;
    }
    
    /**
     * Updates the schedule with a latest task from
     * the original TaskList.
     */
    public static void update() {
        add(Hustler.list.getLastTask());
    }
    
    /**
     * Removes an entry based on the task supplied.
     *
     * @param task task whose matching entry needs to be removed
     */
    public static void remove(Task task) {
        schedule.removeIf(n -> (n.getTask() == task));
    }
    
    /**
     * Returns the size of the current list of entries.
     *
     * @return size of the list of entries
     */
    public static int size() {
        return schedule.size();
    }
    
    /**
     * Displays the whole schedule which includes incomplete tasks,
     * and time spent on each of the tasks.
     */
    public static void displayEntries() {
        String output = "";
        for (ScheduleEntry entry : schedule) {
            output += entry.getTask().toString() 
                + " hours spent: " + (entry.getTimeSpent() / 3600.0) + "\n\t";
        }
        if (size() == 0) {
            output = "Tasks completed. Please add more.";
        }
        Ui ui = new Ui();
        ui.showMessage(output);
    }
    
    /**
     * Returns the time spent on each task.
     *
     * @param task the task for which time spent is required
     * @return amount of time spent on the task
     */
    public static long getTimeSpent(Task task) {
        if (task.isCompleted()) {
            return -1; 
        }
        for (ScheduleEntry entry : schedule) {
            if (entry.getTask() == task) {
                return entry.getTimeSpent();
            }
        }
        return -1;
    }

    public static void recommend(int seconds) {
        recommender = new Recommender(schedule);
        recommended = recommender.recommend(seconds);
    }
}
