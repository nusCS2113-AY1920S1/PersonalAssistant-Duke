package diyeats.commons.datatypes;

/**
 * This is a class that will store weight at a specific date.
 */

public class Tuple {
    public String date;
    public double weight;

    /**
     * This is a class that will store weight at a specific date.
     * @param date date of the input
     * @param weight weight during the input
     */

    public Tuple(String date, double weight) {
        this.date = date;
        this.weight = weight;
    }
}
