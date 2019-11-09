package duke.logic.command.order;

import duke.model.commons.Item;
import duke.model.order.Order;
import duke.model.order.Remark;
import duke.model.order.TotalPrice;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static duke.commons.util.AppUtil.checkArgument;
import static duke.model.order.Customer.MESSAGE_CONSTRAINTS;
import static duke.model.order.Customer.isValidCustomerContact;
import static duke.model.order.Customer.isValidCustomerName;

/**
 * A class that stores the details an order.
 */
public class OrderDescriptor {
    private String customerName;
    private String customerContact;
    private Date deliveryDate;
    private Set<Item<String>> items;
    private Remark remarks;
    private Order.Status status;
    private TotalPrice total;

    /**
     * Creates an {@code OrderDescriptor}.
     */
    public OrderDescriptor() {
    }

    /**
     * Copy constructor.
     *
     * @param toCopy the OrderDescriptor to copy from
     */
    public OrderDescriptor(OrderDescriptor toCopy) {
        if (toCopy.getCustomerName().isPresent()) {
            this.setCustomerName(toCopy.getCustomerName().get());
        }

        if (toCopy.getCustomerContact().isPresent()) {
            this.setCustomerContact(toCopy.getCustomerContact().get());
        }

        setDeliveryDate(toCopy.deliveryDate);
        setItems(toCopy.items);
        setRemarks(toCopy.remarks);
        setStatus(toCopy.status);
        setTotal(toCopy.total);
    }

    public Optional<String> getCustomerName() {
        return Optional.ofNullable(customerName);
    }

    public void setCustomerName(String name) {
        checkArgument(isValidCustomerName(name), MESSAGE_CONSTRAINTS);
        this.customerName = name;
    }

    public Optional<String> getCustomerContact() {
        return Optional.ofNullable(customerContact);
    }

    public void setCustomerContact(String contact) {
        checkArgument(isValidCustomerContact(contact), MESSAGE_CONSTRAINTS);
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

    public Optional<Remark> getRemarks() {
        return Optional.ofNullable(remarks);
    }

    public void setRemarks(Remark remarks) {
        this.remarks = remarks;
    }

    public Optional<Order.Status> getStatus() {
        return Optional.ofNullable(status);
    }

    public void setStatus(Order.Status status) {
        this.status = status;
    }

    public Optional<TotalPrice> getTotal() {
        return Optional.ofNullable(total);
    }

    public void setTotal(TotalPrice total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDescriptor{"
            + "customerName='" + customerName + '\''
            + ", customerContact='" + customerContact + '\''
            + ", deliveryDate=" + deliveryDate
            + ", items=" + items
            + ", remarks='" + remarks + '\''
            + ", status=" + status
            + ", total=" + total
            + '}';
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
            && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, customerContact, deliveryDate, items, remarks, status, total);
    }
}
