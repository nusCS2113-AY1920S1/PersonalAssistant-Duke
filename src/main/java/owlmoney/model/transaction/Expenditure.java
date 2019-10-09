package owlmoney.model.transaction;

import java.util.Date;

public class Expenditure extends Transaction {
    public Expenditure(String description, double amount, Date date, String category) {
        super(description,amount,date,category);
    }
}
