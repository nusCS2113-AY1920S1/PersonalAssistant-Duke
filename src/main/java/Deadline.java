public class Deadline extends Tasks {

    private String deadline;

    public Deadline(String description, String type, String deadline) {
        super(description, type);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String toMessage() {
        return description
                + "(by: " + deadline + ")";
    }
}
