package duke.command.orderCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to mark a {@link Order} as done.
 */
public class DoneOrderCommand extends Cmd<Order> {
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
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        if (orderList.size()==0) {
            throw new DukeException("No order in the list! No order can be done!");
        }
        if (orderIndex < orderList.size() && orderIndex >= 0) {
            Order doneOrder = orderList.getEntry(orderIndex);
            if (doneOrder.isDone()) {
                int number = orderIndex+1;
                throw new DukeException("Order "+number+" has already been done!");
            }

            // to do
            // 1. update storage file
            // 2. Need to update after menu is created
//            String dish;
//            int amount;
//            for (Map.Entry<String, Integer> entry : doneOrder.getOrderContent().entrySet()) {
//                dish = entry.getKey();
//                amount = entry.getValue();
//                int dishIndex;
//                for (int i=0; i<amount ;i++) {
//                    //decrement dish amount from the menu
//                    //new DeleteDishCommand(dishIndex)
//                }
//            }

            ((OrderList)orderList).markOrderDone(orderIndex);
            ui.showMarkDoneOrder(orderList.getEntry(orderIndex).toString());


            //storage.changeContent(orderIndex);


        } else {
            throw new DukeException("Must enter a valid order number, between 1 and " + orderList.size());
        }
    }
}
