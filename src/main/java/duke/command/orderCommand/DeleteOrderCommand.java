package duke.command.orderCommand;

import duke.command.Cmd;
import duke.command.ingredientCommand.DeleteCommand;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific {@link Cmd} used to cancel/delete a {@link Order} from the {@link OrderList}.
 */
public class DeleteOrderCommand extends DeleteCommand<Order> {
    private int orderNb;

    /**
     * the constructor method of class {@link DeleteOrderCommand}
     *
     * @param index order number in the order list
     */
    public DeleteOrderCommand(int index) {
        super(index);
        this.orderNb = index;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        if (orderList.size()==0) {
            throw new DukeException("No order in the list! No order can be cancelled!");
        }
        if (orderNb < orderList.size() && orderNb >= 0) {
            Order removed = orderList.removeEntry(orderNb);
            List<String> fileContent = null;
            try {
                fileContent = new ArrayList<>(Files.readAllLines(storage.getPath(), StandardCharsets.UTF_8));
                fileContent.remove(orderNb); // changing the file content
                Files.write(storage.getPath(), fileContent, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new DukeException("Error while cancelling the order from the hard disc.");
            }
            ui.showRemovedOrder(removed.toString(), orderList.size());
        } else {
            throw new DukeException("Please enter a valid order number between 1 and " + orderList.size() + " to cancel.");
        }
    }

}
