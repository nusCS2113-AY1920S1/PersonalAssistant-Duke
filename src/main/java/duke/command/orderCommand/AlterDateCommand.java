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
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Date;

/**
 * @author VirginiaYu
 *
 * Represents a specific {@link Command} used to alter the {@link Order} serving date.
 */
public class AlterDateCommand extends Command {

    private int orderIndex;
    private Date date;

    /**
     * The constructor method for {@link AlterDateCommand}.
     *
     * @param orderNumber order index number, starting from 1, maximum orderList.size()
     * @param newDate new serving date of the {@link Order}
     */
    public AlterDateCommand(int orderNumber, Date newDate) {
        this.orderIndex = orderNumber;
        this.date = newDate;
    }

    /**
     * Public method used to alter a serving date of an existing order in the orderList, and update it on the hard disc.
     * Print out corresponding info if altering successfully
     *
     * @param orderList the {@link OrderList} to be expanded
     * @param ui       {@link Ui} used for printing the order output
     * @param orderStorage  {@link OrderStorage} writes in the file on the hard disc
     * @throws DukeException Error while altering the date, or writing to the hard disc
     */
    @Override
    public void execute(Fridge fridge, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage orderStorage, RecipeStorage rs) throws DukeException {
        if (orderList.size()==0) {
            throw new DukeException("No order in the list! No order can be altered!");
        }

        if (orderIndex < orderList.size() && orderIndex >= 0) {
            Order order = orderList.getEntry(orderIndex);
            if (order.isDone()) {
                throw new DukeException("Order done already. Date alteration is not expected.");
            }

            order.setDate(date);
            orderStorage.changeContent(orderIndex);
            if (order.isToday()) {
                orderList.getTodoList().addTodoFromOrder(order);
            }

            ui.showLine();
            ui.showOrderChangedDate(order.getDate(),orderList.getEntry(orderIndex).toString());
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

