package duke.model.task.recipetasks;

public class PrepTime {

    private String prepTime = "Unknown preparation time";

    public PrepTime() {};

    public PrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public void edit(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getPrepTime() {
        return this.prepTime;
    }

    public String toSaveString() {
        return prepTime;
    }

    public String toString() {
        return prepTime;
    }

}