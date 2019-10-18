package duke.relation;

public abstract class PatientTask {
    private Integer patientId;
    private Integer taskID;
    private Integer uuid;
    private boolean isDone = false;
    private boolean isRecurrsive = false;
    private String taskType;

    public PatientTask(int pid, int tid, String type) {
        this.patientId = pid;
        this.taskID = tid;
        this.taskType = type;
    }

    public PatientTask(int pid, int tid, boolean isdone, boolean isrecurrsive, String type) {
        this.patientId = pid;
        this.taskID = tid;
        this.taskType = type;
        this.isDone = isdone;
        this.isRecurrsive = isrecurrsive;
    }

    public Integer getPatientId(){
        return patientId;
    }

    public Integer getTaskID(){
        return taskID;
    }

    public String getTaskType(){
        return  taskType;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public boolean isRecurrsive() {
        return isRecurrsive;
    }

    public void markDone() {this.isDone = true;}

    public void markRecurr() {this.isRecurrsive = true;}

    public void undoRecurr(){
        this.isRecurrsive = false;
    }

    public void undoIsDone(){
        this.isDone = false;
    }

    public void setUid(int id) { this.uuid = id;}

    public void updateId(int pid, int tid){
        this.patientId = pid;
        this.taskID = tid;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public String getRecurrsiveIcon(){
        return (isRecurrsive ? "\u0298" : "\u0275");
    }

    public String printStatus() {
        return " Unique ID " + uuid + "[" + this.getStatusIcon() + "] " + "[" + this.getRecurrsiveIcon() + "] ";
    }

    public abstract String toString();
}
