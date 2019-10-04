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
    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    private final Order toAdd;

    public AddOrderCommand(Order toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    public CommandResult execute(Model model) throws CommandException {
        model.addOrder(toAdd);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void undo(Model model) throws CommandException {
//        model.getOrderList().remove(toAdd);
//        storage.serialize(model);
//        ui.refreshOrderList(model.getOrderList(), model.getOrderList());
//        ui.showMessage("Undo: Add order");
    }

    @Override
    public void redo(Model model) throws CommandException {
//        try {
//            execute(bakingList);
//        } catch (CommandException e) {
//            e.printStackTrace();
//        }
//        ui.showMessage("Redo: Add order");
    }

//    private void addOrder(Order order, BakingList bakingList) {
//        bakingList.getOrderList().add(0, order);
//    }

}
