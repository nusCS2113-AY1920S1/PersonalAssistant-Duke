package oof.model.tracker;

public class ModuleTracker {

    private String moduleCode;
    private long timeTaken;

    /**
     * Constructor for ModuleTrackerList.
     * @param moduleCode    String containing module code that identifies module.
     * @param timeTaken     long of time spent on a certain module.
     */
    public ModuleTracker(String moduleCode, long timeTaken) {
        this.moduleCode = moduleCode;
        this.timeTaken = timeTaken;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
