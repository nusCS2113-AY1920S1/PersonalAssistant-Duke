package DukeObjects;

public class Expense {
    protected double amount;
    protected String description;
    protected boolean isTentative;

    public Expense(double amount, String description){
        this.amount = amount;
        this.description = description;
        this.isTentative = false;
    }

    @Override
    public String toString() {
        return amount + " " + description;
    }
}
