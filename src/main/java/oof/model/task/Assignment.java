package oof.model.task;

public class Assignment extends Deadline {

    private String moduleCode;

    /**
     * Constructor for Assignment.
     *
     * @param moduleCode  Assignment Module
     * @param description Details of the Assignment.
     * @param by          Due date and time of the Assignment.
     */
    public Assignment(String moduleCode, String description, String by) {
        super(description, by);
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Converts a task object to string format for storage.
     * @return Task object in string format for storage.
     */
    @Override
    public String toStorageString() {
        String moduleCode = getModuleCode();
        String dateTime = by;
        String date = dateTime.split(" ")[DATE];
        String time = dateTime.split(" ")[TIME];
        return "A" + DELIMITER + getStatusIcon() + DELIMITER + moduleCode + DELIMITER + description
                + DELIMITER + date + DELIMITER + time + DELIMITER;
    }

    @Override
    public String toString() {
        return "[A][" + getStatusIcon() + "] " + moduleCode + " " + super.getDescription() + " (by: " + by + ")";
    }
}
