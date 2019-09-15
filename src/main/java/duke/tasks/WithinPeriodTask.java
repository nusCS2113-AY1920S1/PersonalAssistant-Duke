package duke.tasks;

public class WithinPeriodTask extends ToDo {

    private String period;

    public WithinPeriodTask(String description, String period) {
        super(description);
        this.period = period;
    }

    @Override
    public String toString() {
        return "[T]" + description + " after " + period;
    }

    @Override
    public String getFullString() {
        return "[T][" + getStatusIcon() + "] " + description + " within " + period;
    }

}
