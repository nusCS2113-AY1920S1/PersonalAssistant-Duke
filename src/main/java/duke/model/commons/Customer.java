package duke.model.commons;

import static duke.commons.util.AppUtil.checkEmpty;
import static java.util.Objects.requireNonNull;

public class Customer {
    public static final String MESSAGE_CONSTRAINTS = "Customer name and contact can take any values, "
            + "and should not be blank";

    public final String name;
    public final String contact;

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
        return String.format("Customer name %s \ncontact %s",
                name,
                contact);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Customer // instanceof handles nulls
                && name.equals(((Customer) other).name)
                && contact.equals(((Customer) other).contact)); // state check
    }

    @Override
    public int hashCode() {
        return (name + contact).hashCode();
    }


}
