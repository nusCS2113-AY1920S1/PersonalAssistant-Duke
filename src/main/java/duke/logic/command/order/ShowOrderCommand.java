package duke.logic.command.order;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.order.Order;

/**
 * A command that displays orders of specified status and their indices.
 */
public class ShowOrderCommand extends OrderCommand {
    public static final String COMMAND_WORD = "";

    public static final String AUTO_COMPLETE_INDICATOR = OrderCommand.COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_ORDER_STATUS
    };

    private static final String MESSAGE_LIST_SUCCESS = "Showing %d order(s).";
    private Order.Status status;

    /**
     * Creates a ShowOrderCommand that displays orders with specified {@code status}.
     */
    public ShowOrderCommand(Order.Status status) {
        this.status = status;
    }

    /**
     * Creates a ShowOrderCommand that displays all orders.
     */
    public ShowOrderCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (status == null) {
            model.updateFilteredOrderList(model.PREDICATE_SHOW_ALL_ORDERS);

        } else if (Order.Status.COMPLETED.equals(status)) {
            model.updateFilteredOrderList(model.PREDICATE_SHOW_COMPLETED_ORDERS);

        } else if (Order.Status.ACTIVE.equals(status)) {
            model.updateFilteredOrderList(model.PREDICATE_SHOW_ACTIVE_ORDERS);

        } else if (Order.Status.CANCELED.equals(status)) {
            model.updateFilteredOrderList(model.PREDICATE_SHOW_CANCELED_ORDERS);
        }

        return new CommandResult(
            String.format(MESSAGE_LIST_SUCCESS, model.getFilteredOrderList().size()),
            CommandResult.DisplayedPage.ORDER);
    }
}
