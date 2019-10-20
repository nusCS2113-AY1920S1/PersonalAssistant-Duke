package duke.relation;

public abstract class PatientTask {
    private Integer patientId;
    private Integer taskID;
    private Integer uuid = 0;
    private boolean isDone = false;
    private boolean isRecurrsive = false;
    private String taskType;

    /**
     * .
     *
     * @param pid  .
     * @param tid  .
     * @param type .
     */
    public PatientTask(int pid, int tid, String type) {
        this.patientId = pid;
        this.taskID = tid;
        this.taskType = type;
    }

    /**
     * .
     *
     * @param id .
     */
    public void setUid(int id) {
        this.uuid = id;
    }

    /**
     * .
     *
     * @param pid          .
     * @param tid          .
     * @param isdone       .
     * @param isrecurrsive .
     * @param type         .
     */
    public PatientTask(int pid, int tid, boolean isdone, boolean isrecurrsive, String type) {
        this.patientId = pid;
        this.taskID = tid;
        this.taskType = type;
        this.isDone = isdone;
        this.isRecurrsive = isrecurrsive;
    }

    /**
     * .
     */
    public int getUid() {
        return this.uuid;
    }

    /**
     * .
     *
     * @return .
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * .
     *
     * @return .
     */
    public Integer getTaskID() {
        return taskID;
    }

    /**
     * .
     *
     * @return .
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * .
     *
     * @return .
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * .
     *
     * @return .
     */
    public boolean isRecurrsive() {
        return isRecurrsive;
    }

    /**
     * .
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * .
     */
    public void markRecurr() {
        this.isRecurrsive = true;
    }

    /**
     * .
     */
    public void undoRecurr() {
        this.isRecurrsive = false;
    }

    /**
     * .
     */
    public void undoIsDone() {
        this.isDone = false;
    }

    /**
     * .
     *
     * @param pid .
     * @param tid .
     */
    public void updateId(int pid, int tid) {
        this.patientId = pid;
        this.taskID = tid;
    }

    /**
     * .
     *
     * @return .
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); // unicode icon
    }

    /**
     * .
     *
     * @return .
     */
    public String getRecurrsiveIcon() {
        return (isRecurrsive ? "\u0298" : "\u0275"); // unicode icon
    }

    /**
     * .
     *
     * @return .
     */
    public String printStatus() {
        return " Unique ID " + uuid + " " + "[" + this.getStatusIcon() + "] " + "[" + this.getRecurrsiveIcon() + "] ";
    }


    /**
     * .
     *
     * @return .
     */
    public abstract String toString();
}
