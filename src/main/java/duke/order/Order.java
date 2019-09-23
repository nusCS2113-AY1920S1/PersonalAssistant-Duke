package duke.order;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private String customerName;
    private String customerContact;
    private Date deliveryDate;
    private Map<String, Integer> items = new HashMap<>();
    private String remarks = "";

    public Order(String customerName, String customerContact, Date deliveryDate) {
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.deliveryDate = deliveryDate;
        //System.out.println("Order: " + this.customerName + this.customerContact + this.deliveryDate);
    }

    public void addItem(String itemName, int quantity) {
        //System.out.println("Item: " + itemName + quantity);
        items.put(itemName, quantity);
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public String getRemarks() {
        return remarks;
    }
}
