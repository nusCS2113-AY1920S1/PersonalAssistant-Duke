package oof.model.tracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tracker {

    private static final String DELIMITER = ",";
    private String moduleCode;
    private int taskIndex;
    private String description;
    private Date startDate;
    private Date lastUpdated;
    private long timeTaken;
    private static final int TO_MINUTES = 60000;

    /**
     * Constructor for Tracker.
     *
     * @param moduleCode    module code of Task.
     * @param taskIndex     index of Task.
     * @param description   description of Task.
     * @param startDate     start Date of Tracker.
     * @param lastUpdated   last updated Date of Tracker.
     * @param timeTaken     total time spent on Task.
     */
    public Tracker(String moduleCode, int taskIndex, String description,
                   Date startDate, Date lastUpdated, long timeTaken) {
        this.moduleCode = moduleCode;
        this.taskIndex = taskIndex;
        this.description = description;
        this.startDate = startDate;
        this.lastUpdated = lastUpdated;
        this.timeTaken = timeTaken;
    }

    /**
     * Constructor for Tracker.
     *
     * @param moduleCode    module code.
     * @param timeTaken     total time spent on time taken.
     */
    public Tracker(String moduleCode, long timeTaken) {
        this.moduleCode = moduleCode;
        this.timeTaken = timeTaken;
    }

    /**
     * Get time difference between start date of a tracked Assignment object and current datetime.
     * @param start     start time in Date format.
     * @return number of minutes between start and end time of a tracked Assignment.
     */
    public String getDateDiff(Date start) {
        Date end = new Date();
        long diff = end.getTime() - start.getTime();
        int diffMin = (int) (diff / TO_MINUTES);
        String difference = Integer.toString(diffMin);
        return difference;
    }

    /**
     * Store details of Tracker in desired format.
     * @return formatted details of Tracker
     */
    public String toStorageString() {
        SimpleDateFormat writeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date start = getStartDate();
        Date updated = getLastUpdated();

        String moduleCode = getModuleCode();
        String taskIndex = Integer.toString(getTaskIndex());
        String description = getDescription();

        String startDate;
        if (start != null) {
            startDate = writeFormat.format(start);
        } else {
            startDate = null;
        }
        String lastUpdated = writeFormat.format(updated);
        String timeTaken = Long.toString(getTimeTaken());

        String line = moduleCode + DELIMITER + taskIndex + DELIMITER + description + DELIMITER + startDate + DELIMITER
                + lastUpdated + DELIMITER + timeTaken;

        return line;
    }

    /**
     * Updates startDate and timeTaken properties of Tracker object.
     * @param totalTime     total Time spent on Assignment.
     */
    public void updateTracker(long totalTime, Date lastUpdated) {
        setLastUpdated(lastUpdated);
        setTimeTaken(totalTime);
        setStartDate(null);
    }

    /**
     * Check if tracker has started.
     * @return true if tracker start date equals null.
     */
    public boolean isNotStarted() {
        return getStartDate() == null;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public void setTaskIndex(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

}
