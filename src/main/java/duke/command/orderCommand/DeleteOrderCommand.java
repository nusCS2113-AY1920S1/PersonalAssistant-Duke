package duke.command.orderCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Cmd} used to cancel/delete a {@link Order} from the {@link OrderList}.
 */
public class DeleteOrderCommand extends Cmd<Order> {
    private int orderIndex;

    /**
     * the constructor method of class {@link DeleteOrderCommand}
     *
     * @param orderNumber order number in the order list
     */
    public DeleteOrderCommand(int orderNumber) {
        this.orderIndex = orderNumber;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        if (orderList.size()==0) { throw new DukeException("No order in the list! No order can be removed!"); }
        if (orderIndex < orderList.size() && orderIndex >= 0) {
            Order removed = orderList.getEntry(orderIndex);

            // Need to update after menu is created
//            Dish dish;
//            int amount;
//            for (Map.Entry<Dish, Integer> entry : removed.getOrderContent().entrySet()) {
//                dish = entry.getKey();
//                amount = entry.getValue();
//                int dishIndex;
//                for (int i=0; i<amount ;i++) {
//                    //decrement dish amount from the menu
//                    //new DeleteDishCommand(dishIndex)
//                }
//            }

            orderList.removeEntry(orderIndex);
            ui.showRemovedOrder(removed.toString(), orderList.size());

//            List<String> fileContent = null;
//            try {
//                fileContent = new ArrayList<>(Files.readAllLines(storage.getPath(), StandardCharsets.UTF_8));
//                fileContent.remove(orderNb); // changing the file content
//                Files.write(storage.getPath(), fileContent, StandardCharsets.UTF_8);
//            } catch (IOException e) {
//                throw new DukeException("Error while cancelling the order from the hard disc.");
//            }
        } else {
            throw new DukeException("Please enter a valid order number between 1 and " + orderList.size() + " to remove.");
        }
    }

}
