package gazeeebo.Tasks;

public class Task {
    public String description;
    public boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    public String getStatusIcon() {
        return (isDone ? "D" : "ND"); //return tick or X symbols
    }

    public String listFormat() {
        return "";
    }

}
