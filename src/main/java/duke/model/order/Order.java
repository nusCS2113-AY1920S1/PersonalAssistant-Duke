package duke.model.order;

import duke.model.commons.Customer;
import duke.model.commons.Product;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class Order {
    public static enum Status {
        ACTIVE,
        COMPLETED,
        CANCELED
    }

    private Customer customer;
    private final long id;
    private final Date deliveryDate;
    private final Map<Product, Integer> items;
    private final String remarks;
    private final Status status;

    public Order(Customer customer, Date deliveryDate, Status status, String remarks, Map<Product, Integer> items) {
        requireAllNonNull(customer, deliveryDate, status, remarks, items);

        this.customer = customer;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.remarks = remarks;
        this.id = System.currentTimeMillis();
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getId() {
        return id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public String getRemarks() {
        return remarks;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Customer: [%s] Date: %s Status: %s Items: %s",
                id, customer, deliveryDate, status, items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
