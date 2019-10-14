package owlmoney.model.goals;

import java.util.Date;

public class Goals {
    private String name;
    private double amount;
    private Date date;
    private int days;

    public Goals(String name, double amount, Date date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public String getGoalsName() {
        return this.name;
    }

    public double getGoalsAmount() {
        return this.amount;
    }

    public Date getGoalsDate() {
        return this.date;
    }

    protected String getGoalsDetails() {
        return "To accomplish: " + getGoalsName() + "\nAmount to save: " + getGoalsAmount() + "\nBy: " + getGoalsDate();
    }

    void setGoalsName(String newName) {
        this.name = newName;
    }

    void setGoalsAmount(double newAmount) {
        this.amount = newAmount;
    }

    void setGoalsDate(Date newDate) {
        this.date = newDate;
    }
}
