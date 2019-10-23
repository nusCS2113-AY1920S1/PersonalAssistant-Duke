package duke.logic.command.order;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.order.Order;

import java.util.Set;

import static duke.logic.command.order.OrderCommandUtil.deductInventory;
import static java.util.Objects.requireNonNull;

/**
 * A command to set {@code Status} of order(s) to {@code COMPLETED} and
 * creates a corresponding sale entry.
 *
 * @see Order#getStatus()
 */
public class CompleteOrderCommand extends OrderCommand {
    public static final String COMMAND_WORD = "done";

    private static final String MESSAGE_COMMIT = "Complete order";
    private static final String MESSAGE_COMPLETE_SUCCESS = "%s order(s) completed.";
    private static final String MESSAGE_COMPLETE_INSUFFICIENT_INVENTORY = "%s order(s) completed. "
        + "Insufficient ingredients are deducted to zero in inventory.";
    private static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index [%d] out of bound.";
    private final Set<Index> indices;


    /**
     * Creates a {@code CompleteOrderCommand}.
     *
     * @param indices of orders in order list to set to {@code COMPLETED}
     */
    public CompleteOrderCommand(Set<Index> indices) {
        requireNonNull(indices);

        this.indices = indices;
    }


    public CommandResult execute(Model model) throws CommandException {
        String executeResult = MESSAGE_COMPLETE_SUCCESS;

        for (Index index : indices) {
            if (index.getZeroBased() >= model.getFilteredOrderList().size()) {
                throw new CommandException(String.format(MESSAGE_INDEX_OUT_OF_BOUND, index.getOneBased()));
            }

            OrderDescriptor descriptor = new OrderDescriptor();
            descriptor.setStatus(Order.Status.COMPLETED);

            //deducts ingredients used in this order from inventory.
            boolean isIngredientsUsedUp = deductInventory(
                model.getFilteredOrderList().get(index.getZeroBased()),
                model
            );

            if (isIngredientsUsedUp) {
                executeResult = MESSAGE_COMPLETE_INSUFFICIENT_INVENTORY;
            }

            model.setOrder(index,
                    OrderCommandUtil.modifyOrder(
                        model.getFilteredOrderList().get(index.getZeroBased()),
                        descriptor,
                        model.getFilteredProductList(),
                        model.getFilteredInventoryList()
                    )
            );

            //Add new sale entry
            model.addSaleFromOrder(model.getFilteredOrderList().get(index.getZeroBased()));

        }

        model.commit(MESSAGE_COMMIT);

        return new CommandResult(String.format(executeResult, indices.size()),
                CommandResult.DisplayedPage.ORDER);

    }
}
