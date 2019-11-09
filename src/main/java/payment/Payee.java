package payment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

//@@author karansarat
/**
 * Payments Class to manage the payment details of an item.
 */
public class Payee {
    public String project;
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
    public Payee(String project, String payee, String email, String matricNum, String phoneNum) {
        this.project = project; 
        this.payee = payee;
        this.email = email;
        this.matricNum = matricNum;
        this.phoneNum = phoneNum;
        this.payments = new ArrayList<Payments>();
    }

    public void printPayee() {
        System.out.println("\t" + "Project: " + this.project);
        System.out.println("\t" + "Payee: " + this.payee);
        System.out.println("\t" + "Email: " + this.email);
        System.out.println("\t" + "Matric No: " + this.matricNum);
        System.out.println("\t" + "Phone No: " + this.phoneNum);
    }

}
