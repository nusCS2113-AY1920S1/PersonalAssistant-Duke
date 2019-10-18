package duke.task.recipetasks;

public class PrepStep {

    private int index;
    private String steps = "No preparation steps provided yet.";

    public PrepStep() {}

    public PrepStep(String prepStep) {
        this.steps = prepStep;
    }

    public PrepStep(int index, String steps) {
        this.index = index;
        this.steps = steps;
    }

    public int getIndex() {
        return this.index;
    }

    public String getStep() {
        return steps;
    }

    public String toSaveString() {
        return steps;
    }

    public String toString() {
        return "[" + index + "]" + steps;
    }

}