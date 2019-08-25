public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) { //initialization
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {

        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void markAsDone() { //marks a task as done

        this.isDone = true;
    }

    public String getDescription() {

        return this.description;
    }
}
