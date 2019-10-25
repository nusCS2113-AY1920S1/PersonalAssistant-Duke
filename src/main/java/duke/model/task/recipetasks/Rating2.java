package duke.model.task.recipetasks;

public class Rating2 {

    private String rating;

    public Rating2() {};

    public Rating2(int i, String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return this.rating;
    }

    public String toSaveString() {
        return rating;
    }

    public String toString() {
        return rating;
    }

}