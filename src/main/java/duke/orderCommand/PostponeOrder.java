package duke.orderCommand;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Convert;
import duke.storage.Storage;
import duke.order.OrderList;
import duke.ui.Ui;

import java.util.Date;

public class PostponeOrder extends OrderCommand {

    private int orderNb;
    private String until;
    private Date date;

    /**
     * The constructor method for snooze.
     *
     * @param orderNb order number
     * @param until snooze until when
     */
    public PostponeOrder(int orderNb, String until) {
        this.orderNb = orderNb;
        this.until = until;
        this.date = Convert.stringToDate(until);
    }

    @Override
    public void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException {
        if (orderNb < orderList.size() && orderNb >= 0) {
            if (orderList.getOrder(orderNb).isDone()) {
                throw new DukeException("Seems like you've already finished that order, no need to snooze it now");
            }
            orderList.changeOrderDate(orderNb, until);
            ui.showChangedDate(Convert.getDateString(date, until),orderList.getOrder(orderNb).toString());
            storage.changeContent(orderNb);
        } else {
            throw new DukeException("Enter a valid order number after snooze, between 1 and " + orderList.size());
        }
    }
}

