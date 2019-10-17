package duke.orderCommand;

import duke.Duke;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.order.Order;
import duke.order.OrderList;
import duke.ui.Ui;

import javax.swing.border.Border;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific {@link OrderCommand} used to delete a {@link Order} from the {@link OrderList}.
 */
public class CancelOrder extends OrderCommand {
    private int orderNb;

    public CancelOrder(String number) throws DukeException {
        int orderNb;
        try { orderNb = Integer.parseInt(number) - 1; }
        catch (Exception e) { throw new DukeException(e.getMessage()); }
        this.orderNb = orderNb;
    }

    @Override
    public void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException {
        if (orderNb < orderList.size() && orderNb >= 0) {
            Order removed = orderList.removeOrder(orderNb);
            List<String> fileContent = null;
            try {
                fileContent = new ArrayList<>(Files.readAllLines(storage.getPath(), StandardCharsets.UTF_8));
                fileContent.remove(orderNb); // changing the file content
                Files.write(storage.getPath(), fileContent, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new DukeException("Error while deleting the order from the hard disc");
            }
            ui.showRemovedOrder(removed.toString(), orderList.size());
        } else {
            throw new DukeException("Enter a valid order number after delete, between 1 and " + orderList.size());
        }
    }

}
