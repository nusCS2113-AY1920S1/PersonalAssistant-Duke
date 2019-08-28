public class Task {
    protected String description;
    protected boolean isDone;
    private final String TICKS = "\u2713";
    private final String CROSS = "\u2718";
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    public String getStatusIcon() {
        return (isDone? TICKS: CROSS);
    }
    public void markAsDone() {
        this.isDone = true;
    }
}