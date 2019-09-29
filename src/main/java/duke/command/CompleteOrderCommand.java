package duke.command;

import duke.commons.DukeException;
import duke.entities.Order;
import duke.parser.CommandParser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

/**
 * A command to set the status of an <code>Order</code> object to <code>COMPLETED</code>.
 */
public class CompleteOrderCommand extends UndoableCommand {
    private List<Order> orders;
    private Map<String, List<String>> params;

    /**
     * Class constructor.
     *
     * @param params The parameters specifying details of the order.
     */
    public CompleteOrderCommand(Map<String, List<String>> params) {
        this.params = params;
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        orders = CommandParser.getOrders(bakingList.getOrderList(), params);
        for (Order order : orders) {
            order.setStatus(Order.Status.COMPLETED);
        }
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }
}
