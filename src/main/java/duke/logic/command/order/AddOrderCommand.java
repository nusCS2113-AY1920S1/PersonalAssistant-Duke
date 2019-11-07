package duke.logic.command.order;

import duke.commons.core.LogsCenter;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Customer;
import duke.model.order.Order;
import duke.model.order.Remark;
import duke.model.order.TotalPrice;
import duke.model.product.Product;
import javafx.collections.ObservableList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A command to add an order to order list.
 */
public class AddOrderCommand extends OrderCommand {

    public static final String COMMAND_WORD = "add";

    public static final String AUTO_COMPLETE_INDICATOR = OrderCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_CUSTOMER_NAME,
        CliSyntax.PREFIX_CUSTOMER_CONTACT,
        CliSyntax.PREFIX_ORDER_DEADLINE,
        CliSyntax.PREFIX_ORDER_STATUS,
        CliSyntax.PREFIX_ORDER_ITEM,
        CliSyntax.PREFIX_ORDER_REMARKS,
        CliSyntax.PREFIX_ORDER_TOTAL
    };

    private static final String MESSAGE_COMMIT = "Add order";
    private static final String MESSAGE_SUCCESS = "New order added [Order ID: %s]";

    private static final String DEFAULT_CUSTOMER_NAME = "customer";
    private static final String DEFAULT_CUSTOMER_CONTACT = "N/A";
    private static final Remark DEFAULT_REMARKS = new Remark("N/A");
    private static final Order.Status DEFAULT_STATUS = Order.Status.ACTIVE;

    private final OrderDescriptor addOrderDescriptor;

    private static final Logger logger = LogsCenter.getLogger(AddOrderCommand.class);

    /**
     * Creates an AddOrderCommand to add the specified {@code Order}.
     *
     * @param addOrderDescriptor details of the order to add
     */
    public AddOrderCommand(OrderDescriptor addOrderDescriptor) {
        requireNonNull(addOrderDescriptor);
        this.addOrderDescriptor = addOrderDescriptor;
    }

    /**
     * Returns the total retail price of {@code productItems}.
     */
    private static TotalPrice calculateTotal(Set<Item<Product>> productItems) {
        requireNonNull(productItems);

        double total = 0;
        for (Item<Product> productItem : productItems) {
            total += productItem.getItem().getRetailPrice() * productItem.getQuantity().getNumber();
        }
        return new TotalPrice(total);
    }

    /**
     * Executes the add order command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model) throws CommandException {

        Order toAdd = createOrder(addOrderDescriptor,
            model.getActiveProductList(),
            model.getFilteredInventoryList());
        model.addOrder(toAdd);

        model.commit(MESSAGE_COMMIT);

        logger.info(String.format("Added new order [%s]", toAdd.getId()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getId()), CommandResult.DisplayedPage.ORDER);
    }

    /**
     * Creates an order from {@code descriptor}.
     * {@code inventoryList} is used to detect if there are enough ingredients for the order.
     * @throws CommandException if items specified in the descriptor are not in {@code productList}.
     */
    private Order createOrder(OrderDescriptor descriptor,
                              List<Product> productList, ObservableList<Item<Ingredient>> inventoryList)
        throws CommandException {
        Set<Item<Product>> productItems = OrderCommandUtil.getProductItems(productList,
                descriptor.getItems().orElse(new HashSet<>()));
        TotalPrice total = descriptor.getTotal().orElse(calculateTotal(productItems));
        Order order = new Order(
            new Customer(descriptor.getCustomerName().orElse(DEFAULT_CUSTOMER_NAME),
                descriptor.getCustomerContact().orElse(DEFAULT_CUSTOMER_CONTACT)
            ),
            descriptor.getDeliveryDate().orElse(getDefaultDeliveryDate()),
            descriptor.getStatus().orElse(DEFAULT_STATUS),
            descriptor.getRemarks().orElse(DEFAULT_REMARKS),
            productItems,
            total
        );
        order.listenToInventory(inventoryList);
        return order;
    }

    /**
     * Returns the default delivery date.
     */
    private Date getDefaultDeliveryDate() {
        return Calendar.getInstance().getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddOrderCommand command = (AddOrderCommand) o;
        return Objects.equals(addOrderDescriptor, command.addOrderDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addOrderDescriptor);
    }

    public OrderDescriptor getAddOrderDescriptor() {
        return addOrderDescriptor;
    }
}
