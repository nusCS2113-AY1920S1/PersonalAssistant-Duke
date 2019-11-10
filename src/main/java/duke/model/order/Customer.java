package duke.model.order;

import java.util.Objects;

import static duke.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a customer of an order.
 * Guarantees: immutable; is valid as declared in {@link #isValidCustomer(String, String)}
 */
public class Customer {
    public static final String MESSAGE_CONSTRAINTS
        = "Customer name/contact should be non-blank and no more than 20 characters.";

    //Identity field
    public final String name;

    //Data field
    public final String contact;

    /**
     * Creates a {@code Customer}.
     * @param name a valid name.
     * @param contact a valid contact.
     */
    public Customer(String name, String contact) {
        requireNonNull(name);
        requireNonNull(contact);

        checkArgument(isValidCustomer(name, contact), MESSAGE_CONSTRAINTS);

        this.name = name;
        this.contact = contact;
    }

    public static boolean isValidCustomer(String name, String contact) {
        return isValidCustomerName(name) && isValidCustomerContact(contact);
    }

    /**
     * Returns true if the {@code name} is valid.
     */
    public static boolean isValidCustomerName(String name) {
        assert (name != null);
        return !name.isBlank() && name.length() <= 20;
    }

    /**
     * Returns true if the {@code contact} is valid.
     */
    public static boolean isValidCustomerContact(String contact) {
        assert (contact != null);
        return !contact.isBlank() && contact.length() <= 20;
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
