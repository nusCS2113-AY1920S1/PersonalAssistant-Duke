package payment;

import java.util.ArrayList;

public class PaymentList {
    
    private ArrayList<Payments> paymentlist;

    public PaymentList(ArrayList<Payments> paymentlist) {
        this.paymentlist = paymentlist;
    }

    public PaymentList() {
        this.paymentlist = new ArrayList<>();
    }

    public void addPayments(Payments payment) {
        paymentlist.add(payment);
    }

    public int size() {
        return paymentlist.size();
    }

    public Payments get(int number) {
        return paymentlist.get(number);
    }
}
