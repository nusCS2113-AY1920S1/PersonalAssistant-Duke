package oof.model.task;

/**
 * Represents a Assessment class for module assessments.
 */
public class Assessment extends Event {

    private static final String DELIMITER = "||";
    private String moduleCode;
    private String date;

    /**
     * Constructor for Assessment.
     *
     * @param moduleCode    String containing module code.
     * @param description   String containing assessment description.
     * @param startDateTime String containing start time of assessment.
     * @param endDateTime   String containing end time of assessment.
     */
    public Assessment(String moduleCode, String description, String startDateTime, String endDateTime) {
        super(description, startDateTime, endDateTime);
        this.moduleCode = moduleCode;
    }

    @Override
    public String toString() {
        return "[A][" + getStatusIcon() + "] " + moduleCode + " " + super.getDescription() + " (from: "
                + startDateTime + " to: " + endDateTime + ")";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getDescription() {
        return moduleCode + " " + super.getDescription();
    }

    /**
     * Converts a Lesson object to string format for storage.
     *
     * @return Lesson object in string format for storage.
     */
    public String toStorageString() {
        String startDate = startDateTime.split(" ")[INDEX_DATE];
        String startTime = startDateTime.split(" ")[INDEX_TIME];
        String endDate = endDateTime.split(" ")[INDEX_DATE];
        String endTime = endDateTime.split(" ")[INDEX_TIME];
        return "ASSESSMENT" + DELIMITER + getStatusIcon() + DELIMITER + moduleCode + DELIMITER + description
                + DELIMITER + startDate + DELIMITER + startTime + DELIMITER + endDate + DELIMITER + endTime;
    }
}
