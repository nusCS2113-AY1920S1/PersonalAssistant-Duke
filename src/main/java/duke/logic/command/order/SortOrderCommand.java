package duke.logic.command.order;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;

public class SortOrderCommand extends OrderCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String AUTO_COMPLETE_INDICATOR = OrderCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_ORDER_SORT_DECREASE
    };
    private static final String COMMIT_MESSAGE = "Sort orders";
    private static final String MESSAGE_SUCCESS = "Orders are sorted.";
    private final SortCriteria criteria;
    private final boolean isIncreasing;

    /**
     * Creates a {@code SortOrderCommand}.
     *
     * @param criteria     to sort the orders.
     * @param isIncreasing true if orders are to be sorted in increasing order.
     */
    public SortOrderCommand(SortCriteria criteria, boolean isIncreasing) {
        assert (criteria != null);
        this.criteria = criteria;
        this.isIncreasing = isIncreasing;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.sortOrders(criteria, isIncreasing);

        model.commit(COMMIT_MESSAGE);

        return new CommandResult(MESSAGE_SUCCESS, CommandResult.DisplayedPage.ORDER);
    }

    /**
     * The criteria by which to sort order list.
     */
    public enum SortCriteria {
        STATUS,
        TOTAL,
        DEADLINE,
        CREATION
    }
}
