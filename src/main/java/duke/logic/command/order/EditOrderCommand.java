package duke.logic.command.order;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.order.Order;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * A command to edit the details of an existing order.
 */
public class EditOrderCommand extends OrderCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String AUTO_COMPLETE_INDICATOR = OrderCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_CUSTOMER_NAME,
        CliSyntax.PREFIX_CUSTOMER_CONTACT,
        CliSyntax.PREFIX_ORDER_DEADLINE,
        CliSyntax.PREFIX_ORDER_ITEM,
        CliSyntax.PREFIX_ORDER_REMARKS,
        CliSyntax.PREFIX_ORDER_TOTAL
    };

    private static final String MESSAGE_COMMIT = "Edit order";
    private static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Order [%1$s]";
    private static final String MESSAGE_CANNOT_EDIT_COMPLETED_ORDER = "Completed orders cannot be modified.";

    private final Index index;
    private final OrderDescriptor orderDescriptor;

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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Order orderToEdit = lastShownList.get(index.getZeroBased());

        //Completed order cannot be modified.
        if (orderToEdit.getStatus().equals(Order.Status.COMPLETED)) {
            throw new CommandException(MESSAGE_CANNOT_EDIT_COMPLETED_ORDER);
        }

        Order editedOrder = OrderCommandUtil.modifyOrder(
            orderToEdit,
            orderDescriptor,
            model.getFilteredProductList(),
            model.getFilteredInventoryList());

        model.setOrder(orderToEdit, editedOrder);

        //Deduct inventory if order is set to complete.
        if (editedOrder.getStatus() == Order.Status.COMPLETED) {
            OrderCommandUtil.deductInventory(editedOrder, model);
        }

        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);

        model.commit(MESSAGE_COMMIT);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedOrder.getId()),
                CommandResult.DisplayedPage.ORDER);
    }

}
