package oof.model.tracker;

import oof.model.task.Assignment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tracker {

    private static final String DELIMITER = ",";
    private String moduleCode;
    private String description;
    private Date startDate;
    private Date lastUpdated;
    private long timeTaken;
    private static final int TO_MINUTES = 60000;

    /**
     * Constructor for Tracker.
     *
     * @param moduleCode    module code of Assignment.
     * @param description   description of Assignment.
     * @param startDate     start Date of Tracker.
     * @param lastUpdated   last updated Date of Tracker.
     * @param timeTaken     total time spent on Assignment.
     */
    public Tracker(String moduleCode, String description, Date startDate, Date lastUpdated, long timeTaken) {
        this.moduleCode = moduleCode;
        this.description = description;
        this.startDate = startDate;
        this.lastUpdated = lastUpdated;
        this.timeTaken = timeTaken;
    }

    /**
     * Get time difference between start date of a tracked Assignment object and current datetime.
     * @param start     start time in Date format.
     * @return          number of minutes between start and end time of a tracked Assignment.
     */
    public String getDateDiff(Date start) {
        Date end = new Date();
        long diff = end.getTime() - start.getTime();
        int diffMin = (int) (diff / TO_MINUTES);
        return Integer.toString(diffMin);
    }

    /**
     * Store details of Tracker in desired format.
     * @return  formatted details of Tracker
     */
    public String toStorageString() {
        SimpleDateFormat writeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date start = getStartDate();
        Date updated = getLastUpdated();
        String startDate;

        String moduleCode = getModuleCode();
        String description = getDescription();
        if (start != null) {
            startDate = writeFormat.format(start);
        } else {
            startDate = null;
        }
        String lastUpdated = writeFormat.format(updated);
        String timeTaken = Long.toString(getTimeTaken());

        return moduleCode + DELIMITER + description + DELIMITER + startDate + DELIMITER
                + lastUpdated + DELIMITER + timeTaken;
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

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
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
