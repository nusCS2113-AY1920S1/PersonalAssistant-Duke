package duke.logic.command.order;

import duke.logic.command.CommandResult;
import duke.logic.command.Undoable;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.order.Customer;
import duke.model.order.Order;
import duke.model.product.Product;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A command to add an order to BakingHome.
 */
public class AddOrderCommand extends OrderCommand implements Undoable {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_SUCCESS = "New order added [Order ID: %s]";

    private static final String DEFAULT_CUSTOMER_NAME = "customer";
    private static final String DEFAULT_CUSTOMER_CONTACT = "N/A";
    private static final String DEFAULT_DELIVERY_DATE = "now";
    private static final String DEFAULT_REMARKS = "N/A";
    private static final String DEFAULT_STATUS = "ACTIVE";
    private final OrderDescriptor addOrderDescriptor;
    private Order toAdd;

    /**
     * Creates an AddOrderCommand to add the specified {@code Order}.
     *
     * @param addOrderDescriptor details of the order to add
     */
    public AddOrderCommand(OrderDescriptor addOrderDescriptor) {
        requireNonNull(addOrderDescriptor);
        this.addOrderDescriptor = addOrderDescriptor;
    }


    public CommandResult execute(Model model) throws CommandException {

        Order toAdd = createOrder(addOrderDescriptor, model.getFilteredProductList());
        model.addOrder(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getId()), CommandResult.DisplayedPage.ORDER);
    }

    @Override
    public void undo(Model model) throws CommandException {
        model.deleteOrder(toAdd);
    }

    @Override
    public void redo(Model model) throws CommandException {
        model.addOrder(toAdd);
    }

    private Order createOrder(OrderDescriptor descriptor, List<Product> allProducts) throws CommandException {
        Order order = new Order(new Customer(descriptor.getCustomerName().orElse(DEFAULT_CUSTOMER_NAME),
                descriptor.getCustomerContact().orElse(DEFAULT_CUSTOMER_CONTACT)),
                descriptor.getDeliveryDate().orElse(Calendar.getInstance().getTime()),
                descriptor.getStatus().orElse(Order.Status.ACTIVE),
                descriptor.getRemarks().orElse(DEFAULT_REMARKS),
                OrderCommandUtil.getProducts(
                        allProducts, descriptor.getItems()
                                .orElse(new HashSet<Item<String>>()))
        );
        return order;
    }

    /**
     * Stores the details of the order to add.
     */
    public static class AddOrderDescriptor {
        public final String customerName;
        public final String customerContact;
        public final Date deliveryDate;
        public final Set<Item<String>> items;
        public final String remarks;
        public final Order.Status status;

        /**
         * Creates an {@code AddOrderDescriptor}.
         */
        public AddOrderDescriptor(String customerName, String customerContact, Date deliveryDate,
                                  Set<Item<String>> items, String remarks, Order.Status status) {
            this.customerName = customerName;
            this.customerContact = customerContact;
            this.deliveryDate = deliveryDate;
            this.items = items;
            this.remarks = remarks;
            this.status = status;
        }
    }
}
