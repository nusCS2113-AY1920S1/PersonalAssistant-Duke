package payment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@@author karansarat
/**
 * Payments Class to record details of an item payment.
 */

public class Payments implements Comparable<Payments> {
    public String payee;
    public String item;
    public double cost;
    public String inv;
    public Date deadline;
    public Status status;
    public String project;

    /**
     * Creates an instance of a Payment object.
     * @param project Name of project payment is being made in
     * @param payee The name of the payee of payment
     * @param item Item of the payment.
     * @param cost Cost of the item.
     * @param inv  Invoice for the payment.
     */
    public Payments(String project, String payee, String item, double cost, String inv) {
        this.payee = payee;
        this.item = item;
        this.cost = cost;
        this.inv = inv;
        this.status = Status.PENDING;
        Date currDate = new Date();
        this.deadline = new Date(currDate.getTime() + TimeUnit.DAYS.toMillis(30));
        this.project = project;
    }

    /**
     * Prints out the details of a Payment object.
     */
    public void printPayment() {
        System.out.println("\t" + "Payee: " + this.payee);
        System.out.println("\t" + "Item: " + this.item);
        System.out.println("\t" + "Cost: " + this.cost);
        System.out.println("\t" + "Invoice: " + this.inv);
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("\t" + "Deadline: " + ft.format(this.deadline));
        System.out.println("\t" + "Status: " + this.status);
    }

    /**
     * Adds payment details to dict when payment is added.
     * @param dict set of words to remove details from
     */
    public void paymentToDict(Set<String> dict) {
        String[] arr = {this.payee, this.item, "" + this.cost, this.inv, 
            "" + this.deadline, "" + this.status};
        ArrayList<String> arrNew = new ArrayList<>(Arrays.asList(arr));
        dict.addAll(arrNew);
    }

    /**
     * Removes payment details from dict when payment is deleted.
     * @param dict set of words to remove details from
     */
    public void cleanDict(Set<String> dict) {
        dict.remove(this.item);
        dict.remove("" + this.cost);
        dict.remove("" + this.deadline);
        dict.remove(this.inv);
    }

    //@@author lijiayu980606
    /**
     * TODO.
     * @param payments a Payments object
     */
    @Override
    public int compareTo(Payments payments) {
        return this.deadline.compareTo(payments.deadline);
    }
}