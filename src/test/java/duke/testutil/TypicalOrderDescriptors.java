package duke.testutil;

import duke.logic.command.order.OrderDescriptor;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.order.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * A utility class containing a list of {@code OrderDescriptor} objects to be used in tests.
 */
public class TypicalOrderDescriptors {
    public static final OrderDescriptor ORDER_DESCRIPTOR_A = new OrderDescriptorBuilder()
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

    private TypicalOrderDescriptors() {
    } // prevents instantiation
}
