package duke.task.recipetasks;

public class Rating {

    private String rating;

    public Rating(int i, String rating) {
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