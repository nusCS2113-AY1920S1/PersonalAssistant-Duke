package seedu.hustler.schedule;

import java.util.Collections;
import seedu.hustler.Hustler;
import seedu.hustler.ui.Ui;
import java.util.ArrayList;
import seedu.hustler.logic.CommandLineException;
import java.util.PriorityQueue;

/**
 * Extends on the Scheduler in order to provide recommendations
 * for the task completion mode.
 */
public class Recommender {
    
    /**
     * Ui instance that handles outputs to the user.
     */
    private Ui ui = new Ui();

    /**
     * List of schedule entries to recommend from.
     */
    private ArrayList<ScheduleEntry> schedule;

    /**
     * List of recommended schedule entries.
     */
    private ArrayList<ScheduleEntry> recommended;

    public Recommender(ArrayList<ScheduleEntry> schedule) {
        this.schedule = schedule;
    }
    
    /**
     * Initializes recommended based on the supplied time available and priority.
     * If the amount of hours availabe is 1, the highest priority task is given.
     * If the amount of hours is greater than 1 but less than two, two tasks are added.
     * Otherwise, 3 tasks are added based on priority.
     * Time is allocated evenly to all tasks.
     *
     * @param timeInSeconds time available for a task.
     */
    public ArrayList<ScheduleEntry> recommend(int seconds) {
        if (schedule.size() == 0) {
            return new ArrayList<ScheduleEntry>();
        }

        PriorityQueue<ScheduleEntry> topk = new PriorityQueue<ScheduleEntry>(new SortByPriority());
        int hours = (int) seconds / 3600;

        topKAlgorithm(topk, hours);

        recommended = new ArrayList<ScheduleEntry>(topk);
    
        reTime(seconds);
        return recommended;
    }

    private void topKAlgorithm(PriorityQueue<ScheduleEntry> pq, int k) {
        for (int i = 0; i < schedule.size(); i++) {
            if (i < k) {
                pq.add(schedule.get(i)); 
            } else if (pq.peek().getPriorityScore() < schedule.get(i).getPriorityScore()) {
                pq.poll();
                pq.add(schedule.get(i));
            }
        } 
    }
    
    /**
     * Evenly distributes time to all recommended tasks.
     */
    public void reTime(long seconds) {
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
        if (size() == 0) {
            ui.showMessage("There are no tasks to complete. "
                + "Please add more tasks."); 
            return;
        }

        String output = "Hey, this is your recommended schedule for the next "
            + "few hours. Please confirm if you will follow it. Please add or delete" 
            + " entries before updating time:\n\t";
        for (int i = 0; i < recommended.size(); i++) {
            long hours = recommended.get(i).getTimeAlloc() / 3600;
            long minutes = (recommended.get(i).getTimeAlloc() / 60) % 60;
            long seconds = recommended.get(i).getTimeAlloc() % 60;
            output += (i + 1) + ". " + recommended.get(i).getTask().toString() 
                + " time alloted: " + hours + ":" + minutes + ":" + seconds + " " + recommended.get(i).getPriorityScore() + "\n\t";
        }
        ui.showMessage(output);
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
    public static void addFromTaskList(int index) throws CommandLineException{
        for (ScheduleEntry entry : recommended) {
            if (Hustler.list.get(index) == entry.getTask()) {
                throw new CommandLineException("Task already present in schedule.");
            }
        }
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
        recommended.get(index).setTimeAlloc(timeInSeconds);
    }
}
