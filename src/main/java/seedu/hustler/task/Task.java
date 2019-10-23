package seedu.hustler.task;

import java.time.LocalDateTime;

import seedu.hustler.task.variables.Difficulty;
import seedu.hustler.task.variables.Tag;

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
    protected Difficulty difficulty;

    /**
     * The one-word String tagged onto the task.
     */
    protected Tag tag;

    /**
     * Stores the current date and time at the instance when the user inputs the task.
     */
    protected LocalDateTime inputDateTime;

    /**
     * Initializes description, sets isDone as false and difficulty as M.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.difficulty = new Difficulty("M");
    }

    /**
     * Initializes description, sets isDone as false and difficulty
     * according to user's input.
     */
    public Task(String description, String difficulty, String tagName, LocalDateTime now) {
        this.description = description;
        this.isDone = false;
        this.difficulty = new Difficulty(difficulty);
        this.tag = new Tag(tagName);
        this.inputDateTime = now;
    }

    /**
     * Initializes Task's attributes.
     */
    public Task(String description, Difficulty difficulty, Tag tag, LocalDateTime now) {
        this.description = description;
        this.isDone = false;
        this.difficulty = difficulty;
        this.tag = tag;
        this.inputDateTime = now;
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
     * @return Difficulty of the task.
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Returns the tag of the task.
     *
     * @return the task's tag.
     */
    public String getTag() {
        return this.tag.getTagName();
    }

    public LocalDateTime getInputDateTime() {
        return this.inputDateTime;
    }

    /**
     * Returns a string that displays all information
     * about the task in a user readable format.
     *
     * @return the status and description of the task.
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + this.getDifficulty().toString()
                + this.tag.toString() + " " + this.getDescription();
    }

    /**
     * Returns a disc savable csv format of the task info.
     *
     * @return a pipe separated string of the status, difficulty and description.
     */
    public String toSaveFormat() {
        return (this.isDone ? 1 : 0) + "|" + this.difficulty.toSaveFormat()
                + "|" + this.tag.tagName + "|" + this.description;
    }

    public String toSaveInputDateTime() {
        return "|" + inputDateTime;
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

    /**
     * Checks whether the task has been completed.
     *
     * @return task completed or not
     */
    public boolean isCompleted() {
        return this.isDone;
    }

    public abstract LocalDateTime getDateTime();

    public abstract void setDateTime(LocalDateTime localDateTime);
}
