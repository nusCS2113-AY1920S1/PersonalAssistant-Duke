package task;

import exception.DukeException;
import parser.TimeParser;

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

    // Optional fields
    protected Date doAfterDate;
    protected List<Task> doAfterTasks; // todo: implement doAfterTasks
    protected String recurringType; // DAILY, WEEKLY, MONTHLY, YEARLY, NONE;

    protected Task(String description) {
        this.description = description;
        this.isDone = false;
        this.recurringType = "NONE";
    }

    protected Task(String[] splitStorageStrings) {
        this.description = splitStorageStrings[2];
        this.isDone = splitStorageStrings[1].equals("1");
        this.doAfterDate = TimeParser.parse(splitStorageStrings[3]);
    }

    /**
     * Marks the status of the task to "done".
     */
    protected void markAsDone() {

        if (doAfterDate != null && doAfterDate.after(new Date())) {
            throw new DukeException("☹ OOPS!!! This task has to be done after " + TimeParser.format(doAfterDate));

        } else if (doAfterTasks != null) {

            List<Task> undoneTasks = doAfterTasks.stream()
                    .filter(task -> (!task.isDone))
                    .collect(Collectors.toList());

            if (!undoneTasks.isEmpty()) {
                StringBuilder exceptionMessageBuilder
                        = new StringBuilder("☹ OOPS!!! This task has to be done after:\n  ");

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
     * Sets the task's type of Recurring.
     *
     * @param recurringType The type of recurring.
     */
    public void setRecurringType(String recurringType) {
        this.recurringType = recurringType;
    }

    /**
     * Returns the type of task recurrence.
     *
     * @return The recurringType in {@code String} format.
     */
    public String getRecurringType() {
        return recurringType;
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
     * Set {@code doAfterDate} to a new value, given by a {@code String}.
     * @param dateString the new value of {@code doAfterDate}.
     * @throws DukeException if the {@code String} provided was not a valid date.
     */
    public void setDoAfterDate(String dateString) throws DukeException {
        doAfterDate = TimeParser.parse(dateString);
    }

    /**
     * Overrides the {@code toString()} method in parent class {@code Object},
     * and returns information of the task to be printed by UI.
     * e.g. "[✓] return the book"
     *
     * @return Information of the task to be printed by UI.
     */
    @Override
    public String toString() {
        if (doAfterDate == null) {
            return "[" + getStatusIcon() + "] "
                    + description;
        } else {
            return "[" + getStatusIcon() + "] "
                    + description
                    + " (after: " + TimeParser.format(doAfterDate) + ")";
        }
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
        storageString += " | " + description + " | " + TimeParser.format(doAfterDate);
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
