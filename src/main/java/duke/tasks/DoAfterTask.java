package duke.tasks;

public class DoAfterTask extends ToDo {

    private String afterEvent;

    public DoAfterTask(String description, String afterEvent) {
        super(description);
        this.afterEvent = afterEvent;
    }

    @Override
    public String toString() {
        return "[T]" + description + " after " + afterEvent;
    }

    @Override
    public String getFullString() {
        return "[T][" + getStatusIcon() + "] " + description + " after " + afterEvent;
    }

}
