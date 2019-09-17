package wallet.record;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense extends Record {
    private double amount;
    private String category;
    private boolean isRecurring;
    private String recFrequency;

    public Expense(String description, LocalDate createdDate, double amount, String category, boolean isRecurring, String recFrequency){
        super(description, createdDate);
        this.amount = amount;
        this.category = category;
        this.isRecurring = isRecurring;
        this.recFrequency = recFrequency;
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

    public String getRecFrequency() {
        return recFrequency;
    }

    public void setRecFrequency(String recFrequency) {
        this.recFrequency = recFrequency;
    }

    @Override
    public String toString() {
        if (isRecurring){
            return "[" + recFrequency + "] " + getDescription() + " Amount:$" + amount + " Date:" + DateTimeFormatter.ofPattern("dd MMM yyyy").format(getCreatedDate()) + " Category:" + category;
        } else {
            return getDescription() + " Amount:$" + amount + " Date:" + DateTimeFormatter.ofPattern("dd MMM yyyy").format(getCreatedDate()) + " Category:" + category;
        }
    }
}
