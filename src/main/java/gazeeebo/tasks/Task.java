package gazeeebo.tasks;

public class Task {
    public String description;
    public boolean isDone;
    public int priority;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.priority = 1;
    }
    public String getStatusIcon() {
        return (isDone ? "D" : "ND"); //return tick or X symbols
    }

    public String listFormat() {
        return "";
    }

}
