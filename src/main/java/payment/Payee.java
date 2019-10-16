package payment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Payments Class to manage the payment details of an item.
 */
public class Payee {

    public String payee;
    public String email;
    public String matricNum;
    public String phoneNum;
    public ArrayList<Payments> payments;

    /**
     * Creates a Payee object with the payee's information and list of payments.
     * @param payee Payee's name.
     * @param email Payee's email.
     * @param matricNum Payee's matriculation number.
     * @param phoneNum Payee's phone number.
     */
    public Payee(String payee, String email, String matricNum, String phoneNum) {
        this.payee = payee;
        this.email = email;
        this.matricNum = matricNum;
        this.phoneNum = phoneNum;
        this.payments = new ArrayList<Payments>();
    }

}
