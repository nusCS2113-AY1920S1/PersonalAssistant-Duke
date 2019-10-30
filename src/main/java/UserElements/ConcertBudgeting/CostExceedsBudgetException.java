package UserElements.ConcertBudgeting;

import Events.EventTypes.EventSubclasses.Concert;

public class CostExceedsBudgetException extends Exception {
    Concert concert;
    int budget;

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
