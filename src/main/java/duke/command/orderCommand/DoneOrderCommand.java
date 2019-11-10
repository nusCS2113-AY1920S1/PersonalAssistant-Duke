package duke.command.orderCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

/**
 * @author VirginiaYu
 *
 * Represents a specific {@link Command} used to mark an {@link Order} as done.
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

    /**
     * Public method used to mark an existing order done in the orderList, and update it on the hard disc.
     * Print out corresponding info if marking done successfully
     *
     * @param orderList the {@link OrderList} to be expanded
     * @param ui       {@link Ui} used for printing the order output
     * @param orderStorage  {@link OrderStorage} writes in the file on the hard disc
     * @throws DukeException Error while updating the done status, or writing to the hard disc
     */
    @Override
    public void execute(Fridge fridge, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage orderStorage, RecipeStorage rs) throws DukeException {
        if (orderList.size()==0) {
            throw new DukeException("No order in the list! No order can be done!");
        }

        if (orderIndex <= orderList.size() && orderIndex > 0) {
            Order doneOrder = orderList.getEntry(orderIndex-1);
            if (doneOrder.isDone()) {
                throw new DukeException("Order "+orderIndex+" has already been done!");
            }
            if (!doneOrder.isToday()) {
                throw new DukeException("Order "+orderIndex+" is not serving today!\n\t You cannot done an order that is not today!");
            }
            orderList.markOrderDone(orderIndex-1);
            orderStorage.changeContent(orderIndex-1);

            orderList.getTodoList().deleteTodoFromOrder(doneOrder);

            ui.showLine();
            ui.showMarkDoneOrder(orderList.getEntry(orderIndex-1).toString());
            ui.showLine();
        } else {
            if (orderList.size()==1) {
                throw new DukeException("Got only 1 order in the order list.\n\t Enter '1' as order index");
            } else {
                throw new DukeException("Must enter a valid order index number between 1 and "+orderList.size());
            }
        }
    }
}
