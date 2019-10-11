package duke.logic.command.order;

import duke.model.commons.Item;
import duke.model.order.Order;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Stores the details an order.
 */
public class OrderDescriptor {
    private String customerName;
    private String customerContact;
    private Date deliveryDate;
    private Set<Item<String>> items;
    private String remarks;
    private Order.Status status;

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
    }

    public Optional<String> getCustomerName() {
        return Optional.ofNullable(customerName);
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public Optional<String> getCustomerContact() {
        return Optional.ofNullable(customerContact);
    }

    public void setCustomerContact(String contact) {
        this.customerContact = contact;
    }

    public Optional<Date> getDeliveryDate() {
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
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, customerContact, deliveryDate, items, remarks, status);
    }
}
