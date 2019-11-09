package duke.model.order;

import java.util.Objects;

/**
 * Represents ID of an order.
 * Guarantees: immutable.
 */
public class OrderId {
    public final long value;

    /**
     * Constructs a {@code OrderId}.
     *
     * @param value order id.
     */
    public OrderId(long value) {
        this.value = value;
    }

    /**
     * Returns an {@code OrderId} based on current time.
     */
    public static OrderId getOrderId() {
        return new OrderId(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderId orderId = (OrderId) o;
        return value == orderId.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
