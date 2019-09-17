package duke.task;

import duke.exception.DukeException;

/**
 * Highest-level abstract class for Task objects.
 */
abstract class Task {
    char type;
    private String name;
    private Boolean isDone;

    Task(String name) {
        this.name = name;
        isDone = false;
    }

    void markDone() throws DukeException {
        if (isDone) {
            throw new DukeException("You already did that task!");
        } else {
            isDone = true;
        }
    }

    public char getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDone() {
        return isDone;
    }

    /**
     * Formats the data about the task for display to the user.
     *
     * @return Display-formatted task description.
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "\u2713" : "\u2718") + "] " + name; //ternary operator returns tick or X
    }

    /**
     * Formats the data about the task to write to the data file.
     *
     * @return Data-formatted (tab-separated) task description.
     */
    public String toData() {
        return type + "\t" + (isDone ? "1" : "0") + "\t" + name;
    }
}
