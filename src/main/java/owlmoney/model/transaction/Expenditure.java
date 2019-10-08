package owlmoney.model.transaction;

public class Expenditure extends Transaction {
    public Expenditure(String description, double amount, String date, String category) {
        super(description,amount,date,category);
    }
}
