package owlmoney.model.transaction;

import java.util.Date;

public class Deposit extends Transaction {
    public Deposit(String description, double amount, Date date, String category) {
        super(description,amount,date,category);
    }
}
