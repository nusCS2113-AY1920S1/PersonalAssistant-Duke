package task;

import java.io.Serializable;
import java.util.ArrayList;

public class PaymentForm extends Task implements Serializable{
    ArrayList<PaymentDetails> paymentslist;
    double totalexpense;

    public PaymentForm(String description, ArrayList<PaymentDetails> paymentslist, double totalexpense){
        super(description);
        this.paymentslist = paymentslist;
        this.totalexpense = totalexpense;
    }
}
