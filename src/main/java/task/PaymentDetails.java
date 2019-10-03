package task;

import java.io.Serializable;

public class PaymentDetails implements Serializable{
    String item;
    double cost;

    /**
     * Creates a PaymentDetails instance and initiates the required attributes.
     * @param item Item that was bought.
     * @param cost Cost of the item in dollars.
     */
    public PaymentDetails(String item, double cost){
        this.item = item;
        this.cost = cost;
    }

}
