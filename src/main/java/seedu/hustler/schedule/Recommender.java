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

        hours = hours == 0 ? 1 : hours;

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
}
