package duke.logic.command.order;


import duke.logic.command.commons.CommandResult;
import duke.logic.command.commons.Undoable;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.order.Order;

import static java.util.Objects.requireNonNull;

/**
 * A command to add an <code>Order</code> object to an <code>OrderList</code> object.
 */
public class AddOrderCommand extends OrderCommand implements Undoable {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New order added [Order ID: %s]";
    private final Order toAdd;

    public AddOrderCommand(Order toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    public CommandResult execute(Model model) throws CommandException {
        model.addOrder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getId()), CommandResult.DisplayedPage.ORDER);
    }

    @Override
    public void undo(Model model) throws CommandException {
    }

    @Override
    public void redo(Model model) throws CommandException {

    }

}
