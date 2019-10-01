package task;

public class DoWithinPeriod extends Task {

    private String start;
    private String end;

    /**
     * task.DoWithinPeriod constructor
     * @param description task description
     * @param Period1 earliest date task can be completed by
     * @param Period2 task should be completed by then
     */

    public DoWithinPeriod(String description, String Period1, String Period2) {
        super(description);
        this.start = Period1;
        this.end = Period2;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + " (from: " + start + " til " + end + ")";
    }

    @Override
    public String toWriteFile() {
        int boolToInt = isDone ? 1 : 0;
        return "T | " + boolToInt + " | " + this.description + " | " + this.start + this.end + "\n";
    }

}

//e.g., collect certificate between Jan 15 and 25th.

