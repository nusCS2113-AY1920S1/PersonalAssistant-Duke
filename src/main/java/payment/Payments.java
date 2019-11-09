package payment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import command.Storage;
import common.AlphaNUSException;

//@@author karansarat
/**
 * Payments Class to record details of an item payment.
 */
public class Payments {
    public String payee;
    public String item;
    public double cost;
    public String inv;
    public Date deadline;
    public Status status;

    /**
     * Creates an instance of a Payment object.
     * 
     * @param item Item of the payment.
     * @param cost Cost of the item.
     * @param inv  Invoice for the payment.
     */
    public Payments(String payee, String item, double cost, String inv) {
        this.payee = payee;
        this.item = item;
        this.cost = cost;
        this.inv = inv;
        this.status = Status.PENDING;
        Date currDate = new Date();
        this.deadline = new Date(currDate.getTime() + TimeUnit.DAYS.toMillis(30));
    }

    
    /**
     * Prints out the details of a Payment object.
     */
    public void printPayment() {
        System.out.println("\t" + "Payee: " + this.payee);
        System.out.println("\t" + "Item: " + this.item);
        System.out.println("\t" + "Cost: " + this.cost);
        System.out.println("\t" + "Invoice: " + this.inv);
        System.out.println("\t" + "Deadline: " + this.deadline);
        System.out.println("\t" + "Status: " + this.status);
    }

    public void paymentToDict(Set<String> dict) {
        String[] arr = {this.payee, this.item, "" + this.cost, this.inv, 
            "" + this.deadline, "" + this.status};
        ArrayList<String> arrNew = new ArrayList<>(Arrays.asList(arr));
        dict.addAll(arrNew);
    }

    public void cleanDict(Set<String> dict) {
        dict.remove(this.item);
        dict.remove("" + this.cost);
        dict.remove("" + this.deadline);
        dict.remove(this.inv);
        dict.remove("" + this.status);
    }
}