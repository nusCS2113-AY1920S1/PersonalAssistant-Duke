package seedu.hustler.schedule;

import java.util.Collections;
import seedu.hustler.Hustler;
import seedu.hustler.ui.Ui;
import java.util.ArrayList;
import seedu.hustler.logic.CommandLineException;
import java.util.PriorityQueue;
import java.util.Iterator;

/**
 * Recommends schedule entries/tasks to work on based on priority.
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
    private ArrayList<ScheduleEntry> recommended
        = new ArrayList<ScheduleEntry>();
    
    /**
     * Initializes list of schedule entries/tasks.
     *
     * @param schedule list of schedule entries.
     */
    public Recommender(ArrayList<ScheduleEntry> schedule) {
        this.schedule = schedule;
    }
    
    /**
     * Initializes recommended based using topKAlgorithm algorithm on schedule, where
     * k is set the number of hours available.
     *
     * @param seconds time available to work on tasks.
     */
    public ArrayList<ScheduleEntry> recommend(int seconds) {
        if (schedule.size() == 0) {
            return new ArrayList<ScheduleEntry>();
        }

        PriorityQueue<ScheduleEntry> topk = new PriorityQueue<ScheduleEntry>(new SortByPriority());
        int hours = (int) seconds / 3600;

        hours = hours == 0 ? 1 : hours;

        topKAlgorithm(topk, hours);
        
        while (topk.peek() != null) {
            recommended.add(topk.poll());
        }
        
        reTime(seconds);
        return recommended;
    }
    
    /**
     * Top K topKAlgorithm that uses min heap and can find top k in O(nlogk).
     *
     * @param pq the priority queue that acts as a min heap
     * @param k the number of recommendations
     */
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
