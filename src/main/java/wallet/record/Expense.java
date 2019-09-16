package wallet.record;

public class Expense extends Record {
    private double amount;
    private String category;
    private boolean isRecurring;

    public Expense(double amount, String category, boolean isRecurring){
        this.amount = amount;
        this.category = category;
        this.isRecurring = isRecurring;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

}
