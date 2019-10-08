package duke.model.order;

import java.util.Objects;

import static duke.commons.util.AppUtil.checkEmpty;
import static java.util.Objects.requireNonNull;

/**
 * Represents a customer of an order.
 */
public class Customer {
    private static final String MESSAGE_CONSTRAINTS = "Customer name and contact can take any values, "
            + "and should not be blank";

    //Identity field
    public final String name;

    //Data field
    public final String contact;

    /**
     * Every field must be present and not null.
     */
    public Customer(String name, String contact) {
        requireNonNull(name);
        requireNonNull(contact);

        checkEmpty(name, MESSAGE_CONSTRAINTS);
        checkEmpty(contact, MESSAGE_CONSTRAINTS);

        this.name = name;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return String.format("Name: %s Contact: %s",
                name,
                contact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
