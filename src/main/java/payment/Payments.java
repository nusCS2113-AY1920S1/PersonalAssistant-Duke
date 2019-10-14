package payment;

import java.util.ArrayList;

/**
 * Payments Class to manage the payment details of an item.
 */
public class Payee {

    String payee;
    String email;
    String matricNum;
    String phoneNum;
    ArrayList<Payments> payments;

    public Payee(String payee, String email, String matricNum, String phoneNum) {
        this.payee = payee;
        this.email = email;
        this.matricNum = matricNum;
        this.phoneNum = phoneNum;
        this.payments = new ArrayList<Payments>();
    }

}

public class Payments {
    String item;
    double cost;
    public Payments(String item, double cost) {
        this.item = item;
        this.cost = cost;
    }
}
