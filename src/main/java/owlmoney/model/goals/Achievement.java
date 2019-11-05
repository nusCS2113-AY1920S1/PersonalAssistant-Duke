package owlmoney.model.goals;

public class Achievement {
    private String name;
    private double amount;
    private String category;
    private String date;

    Achievement(String name, double amount, String category, String date) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getDate() {
        return this.date;
    }
}
