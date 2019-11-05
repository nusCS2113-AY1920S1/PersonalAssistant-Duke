package Tasks;

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

    /**
     * Creates Task object.
     * @param description The description of the task
     */
    public Assignment(String description) {
        this.description = description;
        this.isDone = false;
        this.isReminder = false;
        this.remindTime = "";
        this.modCode ="";
    }

    public abstract String getType();

    public abstract String getDateTime();

    public abstract String getDate();

    public abstract String getTime();

    /**
     * Checks whether the task is completed.
     * @return This returns a tick or cross depending on the boolean value of isDone
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public boolean getStatus() {
        return isDone;
    }

    private String getReminderStatus() {
        return (isReminder ? "[HR]" : "[NR]");
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDescription() {
        String[] split = description.split(" ");
        String taskDescription = "";
        for (int i = 0; i < split.length; i++) {
            if (!split[i].toUpperCase().equals(getModCode())) {
                taskDescription += split[i] + " ";
            }
        }
        return taskDescription;
    }

    public String remindTimeToString() {
        return "[<R" + remindTime + "/R>] ";
    }

    public String toString() {
        return "[" + getStatusIcon() + "]" + getReminderStatus()  + remindTimeToString() + getDescription();
    }

    public String displayString(){
        return "[" + getType() + "]" +"[" + getStatusIcon() + "]" + this.description + " by " + getDateTime();
    }
    public String getModCode() {
        String[] split = description.trim().split(" ");
        String modcode = split[0].toUpperCase();
        return modcode;
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

    public String toShow() {
        return modCode + "\n" + description;
    }
}