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
public class DoneOrderCmd extends Cmd<Order> {
    private int orderNb;

    /**
     * the constructor method of {@link DoneOrderCmd}
     *
     * @param number order number in the order list
     * @throws DukeException if input cannot be converted into number
     */
    public DoneOrderCmd(String number) throws DukeException{
        int index;
        try { index = Integer.parseInt(number) - 1; }
        catch (Exception e) { throw new DukeException("Please enter a valid order number."); }
        this.orderNb = index;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        if (orderList.size()==0) {
            throw new DukeException("No order in the list! No order can be done!");
        }
        if (orderNb < orderList.size() && orderNb >= 0) {
            (( OrderList)orderList).markOrderDone(orderNb);
            ui.showMarkDone(orderList.getEntry(orderNb).toString());
            storage.changeContent(orderNb);
        } else {
            throw new DukeException("Please enter a valid order number after done, between 1 and " + orderList.size());
        }
    }
}
