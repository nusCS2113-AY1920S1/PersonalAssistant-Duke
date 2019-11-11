package storage.task;

public class Recurring extends Task {
    private final TaskType taskType;

    // Initialization
    Recurring(String name) {
        super(name);
        this.taskType = TaskType.RECUR;
        this.detailDesc = "for";
        this.recordTaskDetails(name);
    }
}
