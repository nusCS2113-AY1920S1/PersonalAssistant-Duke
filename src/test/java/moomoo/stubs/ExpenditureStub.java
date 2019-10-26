package moomoo.stubs;

import moomoo.task.Expenditure;

import java.time.LocalDateTime;

public class ExpenditureStub extends Expenditure {
    private String name;
    private double cost;
    private LocalDateTime dateTime;

    /**
     * Initializes the expenditure stub with name,cost and date time.
     * @param name Given input name of expenditure.
     * @param cost Given cost of expenditure.
     * @param dateTime Date and time of expenditure.
     */
    public ExpenditureStub(String name, double cost, LocalDateTime dateTime) {
        this.name = name;
        this.cost = cost;
        this.dateTime = dateTime;
    }

    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public double getCost() {
        return cost;
    }
}
