package duke.orderCommand;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.order.Order;
import duke.order.OrderList;
import duke.ui.Ui;

/**
 * Represents a specific {@link OrderCommand} used to mark a {@link Order} as done.
 */
public class DoneOrder extends OrderCommand {
    private int orderNb;

    public DoneOrder(String orderNb) throws DukeException{
        int x;
        try { x = Integer.parseInt(orderNb) - 1; }
        catch (Exception e) { throw new DukeException(e.getMessage()); }
        this.orderNb = x;
    }

    @Override
    public void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException {
        if (orderNb < orderList.size() && orderNb >= 0) {
            orderList.markOrderDone(orderNb);
            ui.showMarkDone(orderList.getOrder(orderNb).toString());
            storage.changeContent(orderNb);

            //To do
            //Trigger getFeedback here

        } else {
            throw new DukeException("Enter a valid task number after done, between 1 and " + orderList.size());
        }
    }
}
