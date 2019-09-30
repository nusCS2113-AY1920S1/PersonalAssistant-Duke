package owlmoney.model.expenditure;

public class Expenditure {

    private String description;
    private double amount;
    private String date;
    private String category;

    public Expenditure(String description, double amount, String date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String toString() {
        return "Description: " + this.description + "\nAmount: " + this.amount + "\nDate: " + this.date.toString()
                + "\nCategory: " + category;
    }
}