package oof.model.tracker;

public class Tracker {
    private String module;
    private String startDate;
    private String endDate;
    private String lastUpdated;
    private long timeTaken = 0;
    private static final String DELIMITER = "\t";

    public Tracker(String module, long timeTaken) {
        this.module = module;
        this.timeTaken = timeTaken;
    }

    /**
     * Constructor for Tracker.
     * @param module        String of Module Code.
     * @param startDate     String of starting date.
     * @param lastUpdated   String of last updated date.
     * @param timeTaken     long of Time Taken.
     */
    public Tracker(String module, String startDate, String lastUpdated, long timeTaken) {
        this.module = module;
        this.startDate = startDate;
        this.lastUpdated = lastUpdated;
        this.timeTaken = timeTaken;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String toStorageString() {
        return getModule() + DELIMITER + getStartDate() + DELIMITER + getLastUpdated() + DELIMITER + getTimeTaken();
    }
}
