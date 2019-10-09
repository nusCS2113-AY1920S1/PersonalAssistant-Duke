package seedu.hustler.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * An abstract class that acts as a template for ToDo, Deadline and Event.
 */
public abstract class Task {
    /**
     * String that contains the description of the task.
     */
    protected String description;

    /**
     * Boolean which states whether the task is done.
     */
    protected boolean isDone;

    /**
     * String that states the difficulty of the task.
     * Default difficulty will be M.
     */
    protected String difficulty;

    /**
     * ArrayList of String that User tags Tasks of.
     */
    protected ArrayList<String> tags;

    /**
     * Initializes description, sets isDone as false, difficulty as M,
     * and sets a new tags arraylist.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.difficulty = "M";
        this.tags = new ArrayList<>();
    }

    /**
     * Initializes description, sets isDone as false, difficulty
     * according to user's input and new tags arraylist.
     */
    public Task(String description, String difficulty) {
        this.description = description;
        this.isDone = false;
        this.difficulty = difficulty;
        this.tags = new ArrayList<>();
    }

    /**
     * Initializes description, sets isDone as false, difficulty
     * and tags according to user's input.
     */
    public Task(String description, String difficulty, ArrayList<String> tags) {
        this.description = description;
        this.isDone = false;
        this.difficulty = difficulty;
        this.tags = tags;
    }

    /**
     * Returns the status of the task.
     *
     * @return a symbol specifying whether a task has been completed or not.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Marks a class as complete.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsDone(TaskList list) {
        this.isDone = true;
    }

    /**
     * Returns the description of the task.
     *
     * @return string description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the difficulty of the task.
     *
     * @return string difficulty.
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Returns a string that displays all information
     * about the task in a user readable format.
     *
     * @return the status and description of the task.
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Returns a disc savable csv format of the task info.
     *
     * @return a pipe separated string of the status, difficulty and description.
     */
    public String toSaveFormat() {
        return (this.isDone ? 1 : 0) + "|" + this.difficulty + "|" + this.description;
    }

    /**
     * Checks whether an input task is equal to current object.
     *
     * @param temp input task
     * @return true or false to comparison.
     */
    public boolean equals(Task temp) {
        return this.description.equals(temp.description);
    }

    public abstract LocalDateTime getDateTime();

    public abstract void setDateTime(LocalDateTime localDateTime);
}
