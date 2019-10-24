package moomoo.task;

import java.time.LocalDateTime;

public class Expenditure {

    private double cost;
    private String name;
    public LocalDateTime dateTime;

    public Expenditure() {
        this.cost = 0.0;
    }

    public Expenditure(String name, double cost) {
        this.name = name;
        this.cost += 0.0;
    }

    public Expenditure(double cost) {
        this.cost += cost;
    }

    public String toString() {
        String output = "current spending is ";
        return this.name + output + "$ " + this.cost;
    }

    public double amount() {
        return this.cost;
    }

}
