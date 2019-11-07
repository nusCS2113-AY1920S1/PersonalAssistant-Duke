package payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Payments Class to record details of an item payment.
 */
public class Payments implements Comparable<Payments>{
    public String item;
    public double cost;
    public String inv;
    private Date deadline;
    private Status status;

    /**
     * Creates an instance of a Payment object.
     * @param item Item of the payment.
     * @param cost Cost of the item.
     * @param inv Invoice for the payment.
     */
    public Payments(String item, double cost, String inv) {
        this.item = item;
        this.cost = cost;
        this.inv = inv;
        this.status = Status.PENDING;
        Date currDate = new Date();
        this.deadline = new Date(currDate.getTime() + TimeUnit.DAYS.toMillis(30));
    }

    /**
     * return the deadline of the payment
     * @return The current status of the payment
     */
    public Date getDeadline(){
        return this.deadline;
    }

    /**
     * return the status of the payment
     * @return The current status of the payment
     */
    public Status getStatus(){
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String givePayments() {

        return item + "\n" + cost + "\n" + inv + "\n" + status + "\n" + deadline;
    }

    @Override
    public int compareTo(Payments payments) {
        return getDeadline().compareTo(payments.getDeadline());
    }
}