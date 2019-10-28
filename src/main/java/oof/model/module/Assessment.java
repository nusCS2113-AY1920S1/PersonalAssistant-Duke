package oof.model.module;

/**
 * Represents a Assessment class for module assessments.
 */
public class Assessment {

    private static final String DELIMITER = "||";
    private String moduleCode;
    private String name;
    private String date;
    private String startTime;
    private String endTime;

    /**
     * Constructor for Assessment.
     *
     * @param moduleCode String containing module code.
     * @param name       String containing assessment name.
     * @param startTime  String containing start time of assessment.
     * @param endTime    String containing end time of assessment.
     */
    public Assessment(String moduleCode, String name, String startTime, String endTime) {
        this.moduleCode = moduleCode;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Converts a Lesson object to string format for storage.
     * @return Lesson object in string format for storage.
     */
    public String toStorageString() {
        return "A" + DELIMITER + moduleCode + DELIMITER + name + DELIMITER + startTime + DELIMITER + endTime;
    }

}
