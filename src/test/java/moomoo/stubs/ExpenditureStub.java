package moomoo.stubs;

import moomoo.task.Expenditure;

import java.time.LocalDateTime;

public class ExpenditureStub extends Expenditure {
    private String name;
    private double cost;
    private LocalDateTime dateTime;

    public ExpenditureStub(double cost, LocalDateTime dateTime) {
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
