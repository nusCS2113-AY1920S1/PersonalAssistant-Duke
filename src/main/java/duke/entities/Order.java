package duke.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {

    public static enum Status {
        ACTIVE,
        COMPLETED,
        CANCELED
    }

    private long id = System.currentTimeMillis();
    private String customerName = "customer";
    private String customerContact = "N/A";
    private Date deliveryDate = Calendar.getInstance().getTime();
    private Map<String, Integer> items = new HashMap<>();
    private String remarks = "N/A";
    private Status status = Status.ACTIVE;

    public Order() {

    }

    public Order(@JsonProperty("customerName") String customerName,
                 @JsonProperty("customerContact") String customerContact,
                 @JsonProperty("deliveryDate") Date deliveryDate) {
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.deliveryDate = deliveryDate;
    }

    public void addItem(String itemName, int quantity) {
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

    public void setCustomerName(String customerName) {
        if (customerName.equals("")) {
            this.customerName = "N/A";
        } else {
            this.customerName = customerName;
        }
    }

    public void setCustomerContact(String customerContact) {
        if (customerContact.equals("")) {
            this.customerContact = "N/A";
        } else {
            this.customerContact = customerContact;
        }
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public void setRemarks(String remarks) {
        if (remarks.equals("")) {
            this.remarks = "N/A";
        } else {
            this.remarks = remarks;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
