package duke.logic.command.order;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.Undoable;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.order.Customer;
import duke.model.order.Order;
import duke.model.product.Product;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static duke.logic.command.order.OrderCommandUtil.getProducts;
import static java.util.Objects.requireNonNull;

/**
 * A command to edit the details of an existing order.
 */
public class EditOrderCommand extends OrderCommand implements Undoable {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Order [%1$s]";

    private final Index index;
    private final OrderDescriptor orderDescriptor;
    private Order orderToEdit;

    /**
     * Creates an EditOrderCommand to modify the details of an {@code Order}.
     *
     * @param index               of the the order in the filtered order list
     * @param orderDescriptor details to edit the order with
     */
    public EditOrderCommand(Index index, OrderDescriptor orderDescriptor) {
        requireAllNonNull(index, orderDescriptor);

        this.index = index;
        this.orderDescriptor = new OrderDescriptor(orderDescriptor);
    }

    @Override
    public void undo(Model model) throws CommandException {
        requireNonNull(model);

        model.setOrder(index, orderToEdit);
    }

    @Override
    public void redo(Model model) throws CommandException {
        execute(model);
    }

    private static Order createEditedOrder(Order toEdit, OrderDescriptor orderDescriptor, List<Product> allProducts)
            throws CommandException {
        assert toEdit != null;

        Customer newCustomer = new Customer(
                orderDescriptor.getCustomerName().orElse(toEdit.getCustomer().name),
                orderDescriptor.getCustomerContact().orElse(toEdit.getCustomer().contact)
        );

        Date newDate = orderDescriptor.getDeliveryDate().orElse(toEdit.getDeliveryDate());

        Set<Item<Product>> newItems;
        if (orderDescriptor.getItems().isPresent()) {
            newItems = getProducts(allProducts, orderDescriptor.getItems().get());
        } else {
            newItems = toEdit.getItems();
        }

        String newRemarks = orderDescriptor.getRemarks().orElse(toEdit.getRemarks());
        Order.Status newStatus = orderDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Order(newCustomer, newDate, newStatus, newRemarks, newItems);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        orderToEdit = lastShownList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, orderDescriptor, model.getFilteredProductList());

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedOrder.getId()),
                CommandResult.DisplayedPage.ORDER);
    }

}
