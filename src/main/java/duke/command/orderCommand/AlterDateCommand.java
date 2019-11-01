package duke.command.orderCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Date;

/**
 * Represents a specific {@link Cmd} used to alter the {@link Order} serving date.
 */
public class AlterDateCommand extends Cmd<Order> {

    private int index;
    private Date date;

    /**
     * The constructor method for {@link AlterDateCommand}.
     *
     * @param orderNumber order index number, starting from 1, maximum orderList.size()
     * @param newDate new serving date of the {@link Order}
     */
    public AlterDateCommand(int orderNumber, Date newDate) {
        this.index = orderNumber-1;
        this.date = newDate;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        if (index <= orderList.size() && index > 0) {
            Order order = orderList.getEntry(index);
            if (order.isDone()) { throw new DukeException("Order done already. Date alteration is not expected."); }
            order.setDate(date);
            ui.showOrderChangedDate(order.getDate(),orderList.getEntry(index).toString());

            // to do:
            // 1. store the new order into file
            //     storage.changeContent(orderNb);
            // 2. update today's dish(task) list if new date is today
//            if (order.isToday()) {
//                String dishName;
//                int amount;
//                for (Map.Entry<String, Integer> entry : order.getOrderContent().entrySet()) {
//                    dishName = entry.getKey();
//                    amount = entry.getValue();
//                    int dishIndex;
//                    for (int i=0; i<amount ;i++) {
//                        //decrement dish amount from the menu
//                        //new AddDishCommand(dishIndex)
//                    }
//                }
//            }

        } else {
            throw new DukeException("Must enter a valid order index number between 1 and "+orderList.size());
        }
    }
}

