package duke.orderCommand;

import duke.order.Order;
import duke.exception.DukeException;
import duke.parser.Convert;
import duke.storage.Storage;
import duke.order.OrderList;
import duke.ui.Ui;

import java.util.Date;

public class ChangeOrderDate extends OrderCommand {

    private int orderNb;
    private String date;

    /**
     * The constructor method for {@link ChangeOrderDate}.
     *
     * @param number order number
     * @param newDate new serving date of the {@link Order}
     */
    public ChangeOrderDate(int number, String newDate) {
        this.orderNb = number;
        this.date = newDate;
    }

    @Override
    public void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException {
        if (orderNb < orderList.size() && orderNb >= 0) {
            if (orderList.getOrder(orderNb).isDone()) {
                throw new DukeException("Order done already. No alteration is expected.");
            }
            orderList.changeOrderDate(orderNb, date);
            ui.showOrderChangedDate(date,orderList.getOrder(orderNb).toString());
            storage.changeContent(orderNb);
        } else {
            throw new DukeException("Please enter a valid order number between 1 and "
                    + orderList.size() + " to alter the serving date");
        }
    }
}

