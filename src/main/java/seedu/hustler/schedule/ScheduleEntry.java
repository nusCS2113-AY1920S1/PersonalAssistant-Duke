package seedu.hustler.schedule;

import seedu.hustler.task.Task;

/**
 * Entry of the schedule which symbolizes an
 * incomplete task, amount of time spent on the task,
 * amount of time allocated by the completion engine and
 * priority score of the task.
 */
public class ScheduleEntry {

    /**
     * Incomplete task that has to be completed.
     */
    private Task task;

    /**
     * Amount of time spent on the task.
     */
    private long secondsSpent;

    /**
     * Amount of time allocated to the task.
     */
    private long secondsAlloc;

    /**
     * Priority of the task.
     */
    private double priorityScore;
    
    /**
     * Initializes the task and seconds spent on it.
     *
     * @param task incomplete task
     * @param secondsSpent amount of time spent on it
     */
    public ScheduleEntry(Task task, long secondsSpent) {
        this.task = task;
        this.secondsSpent = secondsSpent;
    }
    
    /**
     * Adds time to the time spent on the task.
     *
     * @param seconds seconds spent recently
     */
    public void updateTimeSpent(long seconds) {
        this.secondsSpent += seconds;
        this.priorityUpdate();
    }
    
    /**
     * Returns the time spent on the task as seconds.
     *
     * @return seconds spent on the task
     */
    public long getTimeSpent() {
        return this.secondsSpent;
    }
    
    /**
     * Returns the task of the entry.
     */
    public Task getTask() {
        return this.task;
    }
    
    /**
     * Updates the priority of the task based on time spent, time
     * available and difficulty of the task.
     */
    public void priorityUpdate() {
        this.priorityScore = Priority.getPriorityScore(this);
    }

    /**
     * Returns the priority score of the task at the moment.
     *
     * @return priority of the task for comparison sake
     */
    public double getPriorityScore() {
        this.priorityUpdate();
        return this.priorityScore;
    }
    
    /**
     * Sets the time spent on the task.
     *
     * @param seconds seconds spent on the task overall
     */
    public void setTimeSpent(long seconds) {
        this.secondsSpent = seconds;
    }
    
    /**
     * Sets the time allocated to the task.
     *
     * @param seconds seconds allocated to the task
     */
    public void setTimeAlloc(long seconds) {
        this.secondsAlloc = seconds;
    }
    
    /**
     * Returns the amount of time allocated to the task.
     *
     * @return time allocated
     */
    public long getTimeAlloc() {
        return this.secondsAlloc;
    }
}
