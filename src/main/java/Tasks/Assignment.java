package Tasks;

import Commons.DukeConstants;

/**
 * Abstract class Assignment with methods representing all the Command subclasses to be
 * carried out when an input is entered by the user.
 */
public abstract class Assignment {
    private final String description;
    private boolean isDone;
    private boolean isReminder;
    private String remindTime;
    private String modCode;
    private static final String FRONT_ICON_SEPARATOR = "[";
    private static final String BACK_ICON_SEPARATOR = "]";
    private static final String DEADLINE_KEYWORD = "by ";

    /**
     * Creates Task object.
     * @param description The description of the task
     */
    public Assignment(String description) {
        this.description = description;
        this.isDone = false;
        this.isReminder = false;
        this.remindTime = DukeConstants.NO_FIELD;
        this.modCode = DukeConstants.NO_FIELD;
    }

    public abstract String getType();

    public abstract String getDateTime();

    public abstract String getDate();

    public abstract String getTime();

    public abstract String getStartTime();

    public abstract String getEndTime();

    /**
     * Checks whether the task is completed.
     * @return This returns a tick or cross depending on the boolean value of isDone
     */
    public String getStatusIcon() {
        return (isDone ? DukeConstants.DONE_INDICATOR : DukeConstants.NOT_DONE_INDICATOR);
    }

    /**
     * Checks whether the task is completed.
     * @return true if task is completed
     */
    public boolean getStatus() {
        return isDone;
    }

    /**
     * Check whether a reminder is set.
     * @return This returns HR or NR depending on whether reminder is set
     */
    private String getReminderStatus() {
        return (isReminder ? DukeConstants.HAS_REMINDER_INDICATOR : DukeConstants.NO_REMINDER_INDICATOR);
    }

    /**
     * Set a task to be completed.
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * This method gets the description of the assignment.
     */
    public String getDescription() {
        String[] split = description.split(DukeConstants.BLANK_SPACE);
        String taskDescription = DukeConstants.NO_FIELD;
        for (int i = 0; i < split.length; i++) {
            if (!split[i].toUpperCase().equals(getModCode())) {
                taskDescription += split[i] + DukeConstants.BLANK_SPACE;
            }
        }
        return taskDescription;
    }

    /**
     * Convert reminder time to a string.
     */
    public String remindTimeToString() {
        return DukeConstants.REMINDER_TIME_START_KEYWORD + remindTime
                + DukeConstants.REMINDER_TIME_END_KEYWORD
                + DukeConstants.BLANK_SPACE;
    }

    /**
     * Convert reminder task to string for display.
     * @return string of reminder task
     */
    public String toString() {
        return FRONT_ICON_SEPARATOR + getStatusIcon()
                + BACK_ICON_SEPARATOR + getReminderStatus()
                + remindTimeToString() + getDescription();
    }

    /**
     * Display string of task.
     */
    public String displayString() {
        return   getType()  + FRONT_ICON_SEPARATOR
                + getStatusIcon() + BACK_ICON_SEPARATOR
                + DukeConstants.BLANK_SPACE + getModCode()
                + DukeConstants.BLANK_SPACE + getDescription()
                + DEADLINE_KEYWORD + getDateTime();
    }

    /**
     * This method gets the module code of the assignment.
     */
    public String getModCode() {
        String[] split = description.trim().split(DukeConstants.BLANK_SPACE);
        String moduleCode = split[0].toUpperCase();
        return moduleCode;
    }

    /**
     * Sets the time of reminder.
     * @param time The time of reminder input by user
     */
    public void setRemindTime(String time) {
        remindTime = time;
    }

    /**
     * Retrieves the time of reminder.
     */
    public String getRemindTime() {
        return this.remindTime;
    }

    /**
     * Sets true if there is a reminder set, false otherwise.
     */
    public void setReminder(boolean isReminder) {
        this.isReminder = isReminder;
    }

    /**
     * Retrieves the boolean value of whether a reminder was set for this particular assignment.
     */
    public boolean getIsReminder() {
        return this.isReminder;
    }

    /**
     * Gets the string of modCode and description.
     */
    public String toShow() {
        return modCode + "\n" + description;
    }
}