package duke.task.recipetasks;

public class Feedback {

    private String feedback;

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