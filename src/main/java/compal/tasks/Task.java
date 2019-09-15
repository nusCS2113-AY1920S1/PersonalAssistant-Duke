package compal.tasks;

public abstract class Task {

    public boolean isDone;
    protected String symbol;
    private int id;
    private String dateTime;
    private String taskTypeString;
    private String description;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription(){
        return description;
    }




}
