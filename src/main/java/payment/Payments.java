package payment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import command.Storage;
import common.AlphaNUSException;

//@@author karansarat
/**
 * Payments Class to record details of an item payment.
 */

public class Payments implements Comparable<Payments>{
    public String payee;
    public String item;
    public double cost;
    public String inv;
    private Date deadline;
    public Status status;
    private String project;

    /**
     * Creates an instance of a Payment object.
     * 
     * @param item Item of the payment.
     * @param cost Cost of the item.
     * @param inv  Invoice for the payment.
     */
    public Payments(String project, String payee, String item, double cost, String in) {
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
     * return the deadline of the payment
     * @return The current status of the payment
     */
    public Date getDeadline(){
        return this.deadline;
    }

    /**
     * return the cost of the payment
     * @return The cost of the payment
     */
    public Double getCost(){
        return this.cost;
    }

    /**
     * return the cost of the payment
     * @return The cost of the payment
     */
    public String getItem(){
        return this.item;
    }

    /**
     * return the status of the payment
     * @return The current status of the payment
     */
    public Status getStatus(){
        return this.status;
    }

    /**
     * return the project name that the payment belongs to
     * @return The project that the payment belongs to
     */
    public String getProject(){
        return this.project;
    }

    public void setStatus(Status status) {
        this.status = status;
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
    
    @Override
    public int compareTo(Payments payments) {
        return getDeadline().compareTo(payments.getDeadline());
    }
}