package duke.task.recipetasks;

import java.util.ArrayList;

public class PrepStep {

    private int index;
    private String step;

    public PrepStep(int index, String step) {
        this.index = index;
        this.step = step;
    }

    public int getIndex() {
        return this.index;
    }

    public String getStep() {
        return step;
    }

    public String toSaveString() {
        return index + " | " + step;
    }

    public String toString() {
        return "[" + index + "]" + step;
    }

}