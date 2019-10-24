package project;


public class Budget {
    public double allocatedBudget;
    public double currentSpending;

    public Budget(double allocatedBudget, double currentSpending) {
        this.allocatedBudget = allocatedBudget;
        this.currentSpending = currentSpending;
    }

    public double getAllocatedBudget() {
        return allocatedBudget;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }
}
