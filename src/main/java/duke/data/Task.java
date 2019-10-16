package duke.data;

import duke.exception.DukeException;

import java.time.LocalDateTime;

/**
 * Highest-level abstract class for Task objects.
 */
public abstract class Task {
    protected char type;
    private String name;
    private Boolean isDone;
    private Reminder reminder;

    Task(String name) {
        this.name = name;
        isDone = false;
        reminder = null;
    }

    /**
     * Creates a Task with Reminder.
     *
     * @param name     Description of the Task.
     * @param reminder Reminder to be added to the Task.
     */
    public Task(String name, Reminder reminder) {
        this.name = name;
        this.reminder = reminder;
        isDone = false;
    }

    /**
     * Marks a Task as completed.
     *
     * @throws DukeException If Task has already been completed previously.
     */
    public void markDone() throws DukeException {
        if (isDone) {
            throw new DukeException("You already did that task!");
        } else {
            isDone = true;
            reminder = null;
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
        String remind = " ";
        if (reminder != null) {
            remind = reminder.toString() + remind;
        }
        return "[" + (isDone ? "Y" : "N") + "]" + remind + name;
    }

    /**
     * Formats the data about the task to write to the data file.
     *
     * @return Data-formatted (tab-separated) task description.
     */
    public String toData() {
        String remind = "-";
        if (reminder != null) {
            remind = reminder.toData();
        }

        return type + "\t" + (isDone ? "1" : "0") + "\t" + remind + "\t" + name;
    }

    public Reminder getReminder() {
        return reminder;
    }

    /**
     * Set Reminder for Task.
     *
     * @param reminder Reminder to be added to the Task.
     * @throws DukeException If Task has already been completed.
     */
    public void setReminder(Reminder reminder) throws DukeException {
        if (isDone) {
            throw new DukeException("This task has already been completed! I can't set a reminder for it.");
        }

        this.reminder = reminder;
    }

    /**
     * Sets the time that the task should be snoozed until.
     * @param newDateTime The new LocalDateTime to be assigned to the task.
     * @throws DukeException If the user tries to snooze a task that is not timed.
     */
    public void changeTime(LocalDateTime newDateTime) throws DukeException {
        throw new DukeException("Only timed tasks can be snoozed");
    }    // might be better to just define changeTime in TimedTask instead but I could not get it to work?
}
