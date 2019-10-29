package oof.model.tracker;

public class ModuleTracker {

    private String moduleCode;
    private long totalTimeTaken;

    /**
     * Constructor for ModuleTrackerList.
     * @param moduleCode    String containing module code that identifies module.
     * @param totalTimeTaken     long of time spent on a certain module.
     */
    public ModuleTracker(String moduleCode, long totalTimeTaken) {
        this.moduleCode = moduleCode;
        this.totalTimeTaken = totalTimeTaken;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public long getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(long totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }
}
