package moomoo.feature.category;

import java.time.LocalDate;

public class Expenditure {

    private double cost;
    private String name;
    private LocalDate date;
    private String category;

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

    String dateToString() {
        return "0";
    }

    String costToString() {
        return String.valueOf(cost);
    }

    public double getCost() {
        return this.cost;
    }

    public String getCostString() {
        return Double.toString(this.cost);
    }

    public int getLength() {
        return (Double.toString(this.cost)).length();
    }

    public LocalDate getDate() {
        return this.date;
    }
}
