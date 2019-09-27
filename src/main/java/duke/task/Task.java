package duke.task;

import java.time.LocalDateTime;

/**
 * Represents a task that stores description and boolean that indicates the task as completed.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    public int numberOfDays = 0;
    public LocalDateTime currentDate;
    public LocalDateTime dueDate;

    /**
     * Creates a task with the specified description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of a task.
     *
     * @return String of the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a boolean of the task being done or not.
     *
     * @return Boolean of isDone.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the status icon of a task.
     *
     * @return String of the status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "[/]" : "[X]"); //return [✓] : [✗] symbols; [/][X] for jar
    }

    /**
     * Returns the status icon of a task (GUI).
     *
     * @return String of the status icon.
     */
    public String getStatusIconGui() {
        return (isDone ? "[\u2713]" : "[\u2718]"); //return [✓]" : "[✗] symbols
    }

    /**
     * Returns the status icon of a task (GUI).
     *
     * @return String of the status icon.
     */
    public String getTasks() {
        return (isDone ? "[\u2713]" : "[\u2718]"); //return [✓]" : "[✗] symbols
    }

    /**
     * Sets the status icon of a task to true/false.
     *
     * @param setDone The boolean of the task.
     */
    public void setStatusIcon(boolean setDone) {
        isDone = setDone;
    }

    /**
     * Set the remaining days of reminder.
     *
     * @param remainingDays The number of days left of the reminded task.
     */
    public void setReminder(int remainingDays) {
        this.numberOfDays = remainingDays;
    }

    /**
     * Set the remaining days of reminder.
     *
     * @return boolean that triggers the reminder.
     */
    public boolean isTriggerReminder() {
        /*if (dueDate != null) {
            LocalDateTime reminderDate = dueDate.minusDays(numberOfDays);
            return LocalDateTime.now().isAfter(reminderDate);
        }
        if (currentDate != null) {
            LocalDateTime reminderDate = currentDate.minusDays(numberOfDays);
            return LocalDateTime.now().isAfter(reminderDate);
        }*/
        if (isDone() == false) {
            System.out.println("     High priority: ");
        }
        return false;
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String that contains the status and the description of the task.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Extracting a task content into readable string (GUI).
     *
     * @return String that contains the status and the description of the task.
     */
    public String toStringGui() {
        return getStatusIconGui() + " " + description;
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String that contains the status and the description of the task.
     */
    public String toFile() {
        String numStr = "";
        if (isDone) {
            numStr = "1|";
        } else {
            numStr = "0|";
        }
        return  numStr + description;
    }

    /**
     * Retrieves the date of the task as a String format.
     *
     * @return String of Date (Only in Deadline and Event)
     */
    public String getDateString() {
        return null;
    }

    /**
     * Checks if the input and description matches.
     *
     * @param arr1 Input from user.
     * @return Boolean that states if the input is a duplicate.
     */
    public boolean isContain(String arr1) {
        if(arr1.equalsIgnoreCase(description))
            return true;
        return false;
    }
}