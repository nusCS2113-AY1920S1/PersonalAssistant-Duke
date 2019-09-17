package task;

import exception.DukeException;

import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Represents a simple task with description and status.
 * Works as a parent class of more specified task classes in the package.
 */
public class Task {
    // Mandatory fields
    protected String description;
    protected boolean isDone;
    protected boolean isDeleted;

    // Optional fields
    protected Date doAfterDate;
    protected List<Task> doAfterTasks;

    protected Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the status of the task to "done".
     */
    protected void markAsDone() {

        if (doAfterDate != null && doAfterDate.after(new Date())) {
            throw new DukeException("☹ OOPS!!! This task has to be done after " + doAfterDate);

        } else if (doAfterTasks != null) {

            List<Task> undoneTasks = doAfterTasks.stream()
                    .filter(task -> (!task.isDone && !task.isDeleted))
                    .collect(Collectors.toList());

            if (!undoneTasks.isEmpty()) {
                StringBuilder exceptionMessageBuilder = new StringBuilder("☹ OOPS!!! This task has to be done after\n  ");
                for (int i = 0; i < undoneTasks.size(); i++) {
                    exceptionMessageBuilder
                            .append(i + 1)
                            .append(". ")
                            .append(undoneTasks.get(i));
                }
                throw new DukeException(exceptionMessageBuilder.toString());
            }

        } else {
            isDone = true;
        }
    }

    /**
     * Returns the icon representing the status of task.
     *
     * @return ✓ if done, and ✘ otherwise.
     */
    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    private String getStatusIcon() {
        if (isDone) {
            return "\u2713";
        } else {
            return "\u2718";
        }
    }

    /**
     * Overrides the <code>toString()</code> method in parent class <code>Object</code>,
     * and returns information of the task to be printed by UI.
     * e.g. "[✓] return the book"
     *
     * @return Information of the task to be printed by UI.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns information of the task in storage format.
     * e.g. "1 | return the book"
     *
     * @return Information of the task to be stored in storage.
     */
    public String toStorageString() {
        String storageString;
        if (isDone) {
            storageString = "1";
        } else {
            storageString = "0";
        }
        storageString += " | " + description;
        return storageString;
    }

    /**
     * Tests whether the given string is a substring of the task's description.
     *
     * @param s The string to be tested.
     * @return True if it is a substring and false otherwise.
     */
    public boolean contains(String s) {
        return description.contains(s);
    }
}
