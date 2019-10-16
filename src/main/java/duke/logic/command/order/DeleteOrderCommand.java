package duke.logic.command.order;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A command to delete orders from Order List.
 */
public class DeleteOrderCommand extends OrderCommand {
    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_COMMIT = "Delete order";
    private static final String MESSAGE_DELETE_SUCCESS = "%s order(s) removed.";
    private static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index [%d] is out of bound.";
    private final Set<Index> indices;

    /**
     * Creates a {@code DeleteOrderCommand}.
     *
     * @param indices of the orders to delete
     */
    public DeleteOrderCommand(Set<Index> indices) {
        requireNonNull(indices);

        this.indices = indices;
    }


    public CommandResult execute(Model model) throws CommandException {
        List<Order> toDelete = new ArrayList<>();
        for (Index index : indices) {
            if (index.getZeroBased() >= model.getFilteredOrderList().size()) {
                throw new CommandException(String.format(MESSAGE_INDEX_OUT_OF_BOUND, index.getOneBased()));
            }
            toDelete.add(model.getFilteredOrderList().get(index.getZeroBased()));
        }
        for (Order order : toDelete) {
            model.deleteOrder(order);
        }

        model.commit(MESSAGE_COMMIT);

        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, indices.size()),
            CommandResult.DisplayedPage.ORDER);

    }

}

