package moomoo.feature.category;

import java.time.LocalDate;
import java.text.DecimalFormat;

public class Expenditure {

    private String name;
    private double cost;
    private LocalDate date;
    private String category;
    private static DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Initializes name, value and date of expenditure if given by user.
     * @param name     Name of the expenditure (e.g chicken rice).
     * @param cost     Cost of the expenditure.
     * @param date     Date of the expenditure.
     * @param category Name of the category it belongs to.
     */
    public Expenditure(String name, double cost, LocalDate date, String category) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.category = category;
    }

    public String toString() {
        return category + " | " + name + " | " + cost + " | " + date;
    }

    public String getName() {
        return name;
    }

    public String dateToString() {
        return date.toString();
    }

    public double getCost() {
        return this.cost;
    }

    public int getLength() {
        return (df.format(this.cost)).length();
    }

    public LocalDate getDate() {
        return this.date;
    }
}
