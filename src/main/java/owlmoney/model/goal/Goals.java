package owlmoney.model.goal;

public class Goals {
    private String name;
    private double amount;
    private String date;
    private int days;

    public Goals(String name, double amount, String date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public void AddNewGoals(String name, double amount, String date) {

    }
    public String getGoalsName() {
        return this.name;
    }

    public double getGoalsAmount() {
        return this.amount;
    }

    public String getGoalsDate() {
        return this.date;
    }

    protected String getGoalsDetails() {
        return "Goal to accomplish: " + getGoalsName() + "\nAmount to save: " + getGoalsAmount() + "\nBy: " + getGoalsDate();
    }

    void setGoalsName(String newName) {
        this.name = newName;
    }

    void setGoalsAmount(double newAmount) {
        this.amount = newAmount;
    }

    void setGoalsDate(String newDate) {
        this.date = newDate;
    }
}
