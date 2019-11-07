package duke.testutil;

import duke.logic.command.order.OrderDescriptor;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.order.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import static duke.logic.command.order.OrderCommandTestUtil.VALID_NAME_ALICE;

/**
 * A utility class containing a list of {@code OrderDescriptor} objects to be used in tests.
 */
public class TypicalOrderDescriptors {
    /**
     * {@code OrderDescriptor} with all fields present.
     */
    public static final OrderDescriptor ORDER_DESCRIPTOR_ALICE = new OrderDescriptorBuilder()
        .withName("Alice Wang")
        .withContact("Alice@gmail.com")
        .withRemarks("No nuts.")
        .withTotal(50.0)
        .withStatus(Order.Status.ACTIVE)
        .withDeliveryDate(new Date(2019, Calendar.NOVEMBER, 10, 18, 0))
        .withItems(new HashSet<>() {{
            add(new Item<>("Cake", new Quantity(5)));
        }})
        .build();

    /**
     * {@code OrderDescriptor} with one field missing.
     */
    public static final OrderDescriptor ORDER_DESCRIPTOR_RORY = new OrderDescriptorBuilder()
        .withName("Rory Liu")
        .withContact("12345678")
        .withRemarks("no")
        .withStatus(Order.Status.ACTIVE)
        .withTotal(60.0)
        .withDeliveryDate(new Date(2018, Calendar.DECEMBER, 11, 18, 0))
        .build();

    /**
     * {@code OrderDescriptor} with multiple fields missing.
     */
    public static final OrderDescriptor ORDER_DESCRIPTOR_ALICE_MISSING_FIELDS = new OrderDescriptorBuilder()
        .withName(VALID_NAME_ALICE)
        .withContact("Alice@gmail.com")
        .withRemarks("No nuts.")
        .withStatus(Order.Status.ACTIVE)
        .withTotal(50.0)
        .build();

    /**
     * {@code OrderDescriptor} with all fields missing.
     */
    public static final OrderDescriptor ORDER_DESCRIPTOR_MISSING_ALL_FIELDS = new OrderDescriptorBuilder()
        .build();

    private TypicalOrderDescriptors() {
    } // prevents instantiation
}
