package owlmoney.model.transaction;

public class Deposit extends Transaction {
    public Deposit(String description, double amount, String date, String category) {
        super(description,amount,date,category);
    }
}
