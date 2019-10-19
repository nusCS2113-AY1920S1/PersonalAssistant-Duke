package duke.logic.command.order;

import duke.model.commons.Item;
import duke.model.order.Order;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A class that stores the details an order.
 */
public class OrderDescriptor {
    private String customerName;
    private String customerContact;
    private Date deliveryDate;
    private Set<Item<String>> items;
    private String remarks;
    private Order.Status status;
    private Double total;

    public OrderDescriptor() {
    }

    /**
     * Copy constructor.
     *
     * @param toCopy the OrderDescriptor to copy from
     */
    public OrderDescriptor(OrderDescriptor toCopy) {
        setCustomerName(toCopy.customerName);
        setCustomerContact(toCopy.customerContact);
        setDeliveryDate(toCopy.deliveryDate);
        setItems(toCopy.items);
        setRemarks(toCopy.remarks);
        setStatus(toCopy.status);
        setTotal(toCopy.total);
    }

    Optional<String> getCustomerName() {
        return Optional.ofNullable(customerName);
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    Optional<String> getCustomerContact() {
        return Optional.ofNullable(customerContact);
    }

    public void setCustomerContact(String contact) {
        this.customerContact = contact;
    }

    Optional<Date> getDeliveryDate() {
        return Optional.ofNullable(deliveryDate);
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Optional<Set<Item<String>>> getItems() {
        return Optional.ofNullable(items);
    }

    public void setItems(Set<Item<String>> items) {
        this.items = (items != null) ? new HashSet<>(items) : null;
    }

    public Optional<String> getRemarks() {
        return Optional.ofNullable(remarks);
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Optional<Order.Status> getStatus() {
        return Optional.ofNullable(status);
    }

    public void setStatus(Order.Status status) {
        this.status = status;
    }

    Optional<Double> getTotal() {
        return Optional.ofNullable(total);
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDescriptor that = (OrderDescriptor) o;
        return Objects.equals(customerName, that.customerName)
                && Objects.equals(customerContact, that.customerContact)
                && Objects.equals(deliveryDate, that.deliveryDate)
                && Objects.equals(items, that.items)
                && Objects.equals(remarks, that.remarks)
                && status == that.status
                && total.equals(that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, customerContact, deliveryDate, items, remarks, status, total);
    }
}
