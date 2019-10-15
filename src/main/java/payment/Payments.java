package payment;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Payments {
    public String item;
    public double cost;
    public String inv;
    public Date deadline;
    public Status status;

    public Payments(String item, double cost, String inv) {
        this.item = item;
        this.cost = cost;
        this.inv = inv;
        this.status = Status.PENDING;
        Date currDate = new Date();
        this.deadline = new Date(currDate.getTime() + TimeUnit.DAYS.toMillis( 30 ));
    }
}