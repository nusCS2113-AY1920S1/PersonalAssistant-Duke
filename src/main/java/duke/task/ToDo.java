package duke.task;

class ToDo extends Task {
    // Initialization
    ToDo(String name) {
        super(name);
        this.taskType = TaskType.TODO;
        this.recordTaskDetails(name);
    }
}
