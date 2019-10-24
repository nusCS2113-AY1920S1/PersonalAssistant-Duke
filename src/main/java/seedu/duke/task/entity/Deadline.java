package seedu.duke.task.entity;

import seedu.duke.Duke;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Deadline is a type of task with a date/time which is the deadline time.
 */
public class Deadline extends Task {
    private LocalDateTime time;

    /**
     * Instantiates the Deadline with the name and the time. Time must be in during the instantiation as it
     * cannot be changed later.
     *
     * @param name name of the Deadline
     * @param time time of the Deadline
     */
    public Deadline(String name, LocalDateTime time) {
        super(name);
        this.time = time;
        this.taskType = TaskType.Deadline;
    }

    /**
     * Instantiates the Deadline with the name and the time. Time must be in during the instantiation as it
     * cannot be changed later. This method accepts another task to be done after the first task.
     *
     * @param name     name of the Deadline
     * @param time     time of the Deadline
     * @param doAfter  task to be done after main task
     * @param tags     tag associated with the task
     * @param priority priority level of the task
     */
    public Deadline(String name, LocalDateTime time, String doAfter, ArrayList<String> tags, String priority) {
        super(name);
        this.taskType = TaskType.Deadline;
        this.time = time;
        setDoAfterDescription(doAfter);
        setTags(tags);
        setPriorityTo(priority);
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Converts the Deadline to a human readable string containing important information about the Deadline,
     * including the type and time of this Deadline.
     *
     * @return a human readable string containing the important information
     */
    @Override
    public String toString() {
        String output = "";
        output = "[D]" + this.getStatus() + " (by: " + formatDate() + ")";
        if (this.doAfterDescription != null && !this.doAfterDescription.equals("")) {
            output += "\n\tAfter which: " + doAfterDescription;
        }
        for (String tagName : tags) {
            output += " #" + tagName;
        }
        if (this.priority != null && !this.priority.equals("")) {
            output += " Priority: " + priority;
        }
        return output;
    }

    /**
     * Outputs a string with all the information of this Deadline to be stored in a file for future usage.
     *
     * @return a string containing all information of this Deadline
     */
    @Override
    public String toFileString() {
        String output = "";
        output = (this.isDone ? "1" : "0") + " deadline " + this.name + " -time "
                + formatDate();
        if (this.doAfterDescription != null && !this.doAfterDescription.equals("")) {
            output += " -doafter " + doAfterDescription;
        }
        for (String tagName : tags) {
            output += " -tag " + tagName;
        }
        if (this.priority != null && !this.priority.equals("")) {
            output += " -priority " + priority;
        }
        return output;
    }

    /**
     * Outputs a formatted string of the time of this Deadline. The format is the same as input format and is
     * shared by all tasks.
     *
     * @return a formatted string of the time of this Deadline
     */
    private String formatDate() {
        return format.format(this.time);
    }

    /**
     * Calculates whether the time set for the deadline is near enough.
     *
     * @param dayLimit maximum number of days from now for the deadline to be considered as near
     * @return the flag whether the deadline is near enough
     */
    @Override
    public boolean isNear(int dayLimit) {
        LocalDateTime now = LocalDateTime.now();
        if (this.time.compareTo(now) >= 0) {
            return now.compareTo(this.time.minusDays(dayLimit)) >= 0;
        }
        return false;
    }

    @Override
    public void snooze(int duration) {
        time = time.plusDays(duration);
    }

    /**
     * Check if this task clashes with the new task being added.
     *
     * @param task the new task being added into the list.
     * @return true if this task clashes with the new task being added, false if not.
     */
    @Override
    public boolean isClash(Task task) {
        try {
            if (task.taskType.equals(TaskType.Deadline)) {
                Deadline deadlineTask = (Deadline) task;  // downcasting task to Deadline in order to use
                // getTime().
                if (this.time.compareTo(deadlineTask.getTime()) == 0) {
                    return true;
                }
            }
            if (task.taskType.equals(TaskType.Event)) {
                Event eventTask = (Event) task;  // downcasting task to Event in order to use getTime().
                if (this.time.compareTo(eventTask.getTime()) == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            Duke.getUI().showError("Error when finding clashes of tasks.");
        }
        return false;
    }
}
