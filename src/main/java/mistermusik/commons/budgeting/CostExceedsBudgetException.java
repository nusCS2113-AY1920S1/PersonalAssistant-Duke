package mistermusik.commons.budgeting;

import mistermusik.commons.events.eventtypes.eventsubclasses.Concert;

public class CostExceedsBudgetException extends Exception {
    private Concert concert;
    private int budget;

    public CostExceedsBudgetException(Concert concert, int budget) {
        this.concert = concert;
        this.budget = budget;
    }

    public Concert getConcert() {
        return concert;
    }

    public int getBudget() {
        return budget;
    }
}
