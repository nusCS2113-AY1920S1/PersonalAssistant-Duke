package moomoo.task;

import java.text.DecimalFormat;

/**
 * Represents an event that allows for a timing to be set.
 */
public class Budget {

    private double budget;

    public Budget() {
        this.budget = 0.0;
    }

    /**
     * Takes in budget set by user and set budget variable.
     * @param amount Budget given by user every month
     */
    public Budget(double amount) {
        this.budget = amount;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        return "Your budget is: $" + df.format(this.budget);
    }

    public double getBudget() {
        return this.budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}