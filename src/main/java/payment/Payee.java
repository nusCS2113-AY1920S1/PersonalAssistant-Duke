package payment;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Payments Class to manage the payment details of an item.
 */
public class Payee {

    public String payee;
    public String email;
    public String matricNum;
    public String phoneNum;
    ArrayList<Payments> payments;

    public Payee(String payee, String email, String matricNum, String phoneNum) {
        this.payee = payee;
        this.email = email;
        this.matricNum = matricNum;
        this.phoneNum = phoneNum;
        this.payments = new ArrayList<Payments>();
    }

}



