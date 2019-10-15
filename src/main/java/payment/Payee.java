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

class Payments {
    String item;
    double cost;
    String inv;
    Date deadline;
    Status status;

    public Payments(String item, double cost, String inv) {
        this.item = item;
        this.cost = cost;
        this.inv = inv;
        this.status = Status.PENDING;
        Date currDate = new Date();
        this.deadline = new Date(currDate.getTime() + TimeUnit.DAYS.toMillis( 30 ));
    }
}

enum Status {
    PENDING,
    APPROVED,
    OVERDUE
}

enum Field {
    PAYEE,
    EMAIL,
    MATRIC,
    PHONE,
    ITEM,
    COST,
    INV,
    DEADLINE,
    STATUS
}
