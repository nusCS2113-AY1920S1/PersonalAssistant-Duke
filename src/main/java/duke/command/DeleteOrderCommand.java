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
 * A command to remove an <code>Order</code> object from an <code>OrderList</code> object.
 */
public class DeleteOrderCommand extends UndoableCommand {

    private List<Order> orders;
    private List<Integer> indexes;
    private Map<String, List<String>> params;

    /**
     * Class constructor.
     *
     * @param params The parameters specifying details of the order.
     */
    public DeleteOrderCommand(Map<String, List<String>> params) throws DukeException {
        this.params = params;
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        for (int i = 0; i < indexes.size(); i++) {
            bakingList.getOrderList().add(indexes.get(i), orders.get(i));
        }
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
        ui.showMessage("Undo: Remove order");
    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        execute(bakingList, storage, ui);
        ui.showMessage("Redo: Remove order");
    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        this.orders = CommandParser.getOrders(bakingList.getOrderList(), params);
        this.indexes = CommandParser.getOrderIndexes(params);
        bakingList.getOrderList().removeAll(orders);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
        ui.showMessage("Order removed");
    }


}