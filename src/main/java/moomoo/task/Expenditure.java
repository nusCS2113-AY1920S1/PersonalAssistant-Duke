package moomoo.task;

import java.time.LocalDate;

public class Expenditure {

    private double cost;
    private String name;
    public LocalDate date;

    public Expenditure() {
        this.cost = 0.0;
    }

    /**
     * Initializes name, value and date of expenditure if given by user.
     * @param name     Name of the expenditure (e.g chicken rice).
     * @param cost     Cost of the expenditure.
     * @param date     Date of the expenditure.
     */
    public Expenditure(String name, double cost, LocalDate date) {
        this.name = name;
        this.cost = cost;
        this.date = date;
    }

    public String toString() {
        return name;
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
