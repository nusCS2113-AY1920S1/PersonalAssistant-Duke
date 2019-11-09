package duke.testutil;

import duke.logic.command.order.OrderDescriptor;
import duke.model.commons.Item;
import duke.model.order.Order;
import duke.model.order.Remark;
import duke.model.order.TotalPrice;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building OrderDescriptor objects.
 */
public class OrderDescriptorBuilder {
    private OrderDescriptor descriptor;

    public OrderDescriptorBuilder(OrderDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public OrderDescriptorBuilder() {
        this.descriptor = new OrderDescriptor();
    }

    /**
     * Sets the {@code customerName} of the {@code OrderDescriptor} that we are building.
     */
    public OrderDescriptorBuilder withName(String name) {
        this.descriptor.setCustomerName(name);
        return this;
    }

    /**
     * Sets the {@code customerContact} of the {@code OrderDescriptor} that we are building.
     */
    public OrderDescriptorBuilder withContact(String contact) {
        this.descriptor.setCustomerContact(contact);
        return this;
    }

    /**
     * Sets the {@code remarks} of the {@code OrderDescriptor} that we are building.
     */
    public OrderDescriptorBuilder withRemarks(String remarks) {
        this.descriptor.setRemarks(new Remark(remarks));
        return this;
    }

    /**
     * Sets the {@code deliveryDate} of the {@code OrderDescriptor} that we are building.
     */
    public OrderDescriptorBuilder withDeliveryDate(Date date) {
        this.descriptor.setDeliveryDate(date);
        return this;
    }

    /**
     * Sets the {@code total} of the {@code OrderDescriptor} that we are building.
     */
    public OrderDescriptorBuilder withTotal(double total) {
        this.descriptor.setTotal(new TotalPrice(total));
        return this;
    }

    /**
     * Sets the {@code status} of the {@code OrderDescriptor} that we are building.
     */
    public OrderDescriptorBuilder withStatus(Order.Status status) {
        this.descriptor.setStatus(status);
        return this;
    }

    /**
     * Sets the {@code items} of the {@code OrderDescriptor} that we are building.
     */
    public OrderDescriptorBuilder withItems(Set<Item<String>> items) {
        this.descriptor.setItems(new HashSet<>(items));
        return this;
    }

    public OrderDescriptor build() {
        return new OrderDescriptor(descriptor);
    }

}
