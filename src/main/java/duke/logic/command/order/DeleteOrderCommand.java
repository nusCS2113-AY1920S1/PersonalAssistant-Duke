package duke.logic.command.order;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

//@@author liujiajun
/**
 * A command to delete orders from Order List.
 */
public class DeleteOrderCommand extends OrderCommand {
    public static final String COMMAND_WORD = "remove";

    private static final String MESSAGE_COMMIT = "Delete order";
    private static final String MESSAGE_DELETE_SUCCESS = "%s order(s) removed.";
    private final Set<Index> indices;

    public static final String AUTO_COMPLETE_INDICATOR = OrderCommand.COMMAND_WORD + " " + COMMAND_WORD;

    /**
     * Creates a {@code DeleteProductCommand}.
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
                throw new CommandException(Message.MESSAGE_INDEX_OUT_OF_BOUND);
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

