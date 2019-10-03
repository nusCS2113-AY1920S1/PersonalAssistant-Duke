package dolla.task;

public class FixDuration extends Task {

    private String duration;

    /**
     * This method will set the type of a fix duration task to w and set the description and duration in this
     * class as the input parameters.
     * @param description is the description of the task.
     * @param duration is the period of time that the user want to fix.
     */
    public FixDuration(String description, String duration) {
        super(description);
        type = 'W';
        this.duration = duration;
    }

    /**
     * This method will return the duration of the task.
     * @return a string containing "within" and the duration of the task.
     */
    @Override
    public String getDateStr() {
        return "(within " + duration + ")";
    }

    public String formatDateSave() {
        return " | " + duration;
    }
}
