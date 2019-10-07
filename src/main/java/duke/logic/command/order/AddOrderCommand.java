package duke.logic.command.order;


import duke.logic.command.CommandResult;
import duke.logic.command.Undoable;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.order.Order;

import static java.util.Objects.requireNonNull;

/**
 * A command to add an order to BakingHome.
 */
public class AddOrderCommand extends OrderCommand implements Undoable {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New order added [Order ID: %s]";
    private final Order toAdd;

    /**
     * Creates an AddOrderCommand to add the specified {@code Order}.
     *
     * @param toAdd the {@code Order} to be added
     */
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
        model.deleteOrder(toAdd);
    }

    @Override
    public void redo(Model model) throws CommandException {
        model.addOrder(toAdd);
    }

}
