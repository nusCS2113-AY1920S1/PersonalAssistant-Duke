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

    private Order order;
    private int index;
    private Map<String, List<String>> params;

    /**
     * Class constructor.
     *
     * @param params the parameters specifying details of the order.
     */
    public DeleteOrderCommand(Map<String, List<String>> params) throws DukeException {
        this.params = params;
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        bakingList.getOrderList().add(index, order);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        execute(bakingList, storage, ui);
    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        this.order = CommandParser.getOrderByIndexOrId(bakingList.getOrderList(), params);
        this.index = CommandParser.getOrderIndex(bakingList.getOrderList(), params);
        bakingList.getOrderList().remove(order);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }


}
