package executor.task;

class Event extends Task {
    // Initialization
    Event(String name) {
        super(name);
        this.taskType = TaskType.EVENT;
        this.recordTaskDetails(name);
    }
}
