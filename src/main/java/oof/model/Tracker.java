package oof.model;

import java.util.Comparator;

public class Tracker {
    private String taskDescription;
    private long totalTimeTaken;

    public Tracker(String taskDescription, long totalTimeTaken) {
        this.taskDescription = taskDescription;
        this.totalTimeTaken = totalTimeTaken;
    }

    /**
     * Add description and total time taken to Tracker object.
     * @param taskDescription   description of Task object.
     * @param totalTimeTaken    total time spent on Task object.
     */
    public void add(String taskDescription, long totalTimeTaken) {
        setTaskDescription(taskDescription);
        setTotalTimeTaken(totalTimeTaken);
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public long getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(long totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

}
