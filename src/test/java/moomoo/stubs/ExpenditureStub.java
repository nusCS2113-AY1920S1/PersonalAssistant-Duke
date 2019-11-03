package moomoo.stubs;

import moomoo.task.category.Expenditure;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class ExpenditureStub extends Expenditure {
    private String name;
    private double cost;
    private LocalDate date;

    /**
     * Initializes the expenditure stub with name,cost and date time.
     * @param name Given input name of expenditure.
     * @param cost Given cost of expenditure.
     * @param date Date and time of expenditure.
     */
    public ExpenditureStub(String name, double cost, LocalDate date) {
        super(name, cost, date);
        this.name = name;
        this.cost = cost;
        this.date = date;
    }

    //@Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}
