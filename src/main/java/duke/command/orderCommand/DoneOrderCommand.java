package duke.command.orderCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Command} used to mark a {@link Order} as done.
 */
public class DoneOrderCommand extends Command {
    private int orderIndex;

    /**
     * the constructor method of {@link DoneOrderCommand}
     *
     * @param orderNumber order number in the order list
     */
    public DoneOrderCommand(int orderNumber) {
        this.orderIndex = orderNumber;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        if (orderList.size()==0) {
            throw new DukeException("No order in the list! No order can be done!");
        }
        if (orderIndex < orderList.size() && orderIndex >= 0) {
            Order doneOrder = orderList.getEntry(orderIndex);
            if (doneOrder.isDone()) {
                int number = orderIndex+1;
                throw new DukeException("Order "+number+" has already been done!");
            }
            os.changeContent(orderIndex+1);

            // TODO: update chef's to do list


            
            orderList.markOrderDone(orderIndex);
            ui.showLine();
            ui.showMarkDoneOrder(orderList.getEntry(orderIndex).toString());
            ui.showLine();
        } else {
            throw new DukeException("Must enter a valid order number, between 1 and " + orderList.size() + " to be done");
        }
    }
}
