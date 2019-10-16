package duke.logic.command.order;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Customer;
import duke.model.order.Order;
import duke.model.product.Product;
import javafx.collections.ObservableList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A command to add an order to BakingHome.
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
    private static final Date DEFAULT_DELIVERY_DATE = Calendar.getInstance().getTime();
    private static final String DEFAULT_REMARKS = "N/A";
    private static final Order.Status DEFAULT_STATUS = Order.Status.ACTIVE;

    private final OrderDescriptor addOrderDescriptor;

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
     * Executes the add order command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model) throws CommandException {

        Order toAdd = createOrder(addOrderDescriptor,
            model.getFilteredProductList(),
            model.getFilteredInventoryList());
        model.addOrder(toAdd);
        model.commit(MESSAGE_COMMIT);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getId()), CommandResult.DisplayedPage.ORDER);
    }

    private Order createOrder(OrderDescriptor descriptor,
                              List<Product> productList, ObservableList<Item<Ingredient>> inventoryList)
        throws CommandException {
        Set<Item<Product>> productItems = OrderCommandUtil.getProducts(productList,
                descriptor.getItems().orElse(new HashSet<>()));
        double total = descriptor.getTotal().orElse(OrderCommandUtil.calculateTotal(productItems));
        return new Order(
            new Customer(descriptor.getCustomerName().orElse(DEFAULT_CUSTOMER_NAME),
                descriptor.getCustomerContact().orElse(DEFAULT_CUSTOMER_CONTACT)
            ),
            descriptor.getDeliveryDate().orElse(DEFAULT_DELIVERY_DATE),
            descriptor.getStatus().orElse(DEFAULT_STATUS),
            descriptor.getRemarks().orElse(DEFAULT_REMARKS),
            productItems,
            total,
            inventoryList
        );
    }
}
