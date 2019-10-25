package duke.model.task.recipetasks;

public class Feedback {

    private String feedback = "No feedback yet.";

    public Feedback() {};

    public Feedback(int i, String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public String toSaveString() {
        return feedback;
    }

    public String toString() {
        return feedback;
    }

}