package duke.model.user;

/**
 * This is a class that will store weight at a specific date.
 */

public class Tuple {
    public String date;
    public int weight;

    /**
     * This is a class that will store weight at a specific date.
     * @param date date of the input
     * @param weight weight during the input
     */

    public Tuple(String date, int weight) {
        this.date = date;
        this.weight = weight;
    }
}
