package mistermusik.commons.budgeting;

import mistermusik.commons.events.eventtypes.eventsubclasses.Concert;

public class CostExceedsBudgetException extends Exception {
    private Concert concert;
    private int budget;

    /**
     * Constructs new CostExceedsBudgetException.
     *
     * @param concert Concert object that caused costs to exceed budget
     * @param budget  Current budget
     */
    public CostExceedsBudgetException(Concert concert, int budget) {
        this.concert = concert;
        this.budget = budget;
    }

    /**
     * Returns corresponding concert object.
     */
    public Concert getConcert() {
        return concert;
    }

    /**
     * Returns current monthly budget.
     */
    public int getBudget() {
        return budget;
    }
}
