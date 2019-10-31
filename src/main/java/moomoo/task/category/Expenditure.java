package moomoo.task.category;

import java.time.LocalDate;

public class Expenditure {
    
    private double cost;
    private String name;
    public LocalDate date;

    /**
     * Initializes value of expenditure if given by user.
     * @param cost  Cost of expenditure.
     */
    public Expenditure(double cost) {
        this.cost = cost;
    }

    /**
     * Initializes value and date of expenditure if given by user.
     * @param cost  Cost of expenditure.
     * @param date  Date of the expenditure.
     */
    public Expenditure(double cost, LocalDate date) {
        this.cost = cost;
        this.date = date;
    }

    /**
     * Initializes name and value of expenditure if given by user.
     * @param name  Name of the expenditure (e.g. chicken rice).
     * @param cost  Cost of the expenditure.
     */
    public Expenditure(String name, double cost) {
        this.name = name;
        this.cost = cost;
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

    String dateToString() {
        return "0";
    }

    String costToString() {
        return String.valueOf(cost);
    }
    
    public double getCost() {
        return this.cost;
    }
    
    public LocalDate getDate() {
        return this.date;
    }
}
