package duke.command.order;

import duke.command.Undoable;
import duke.commons.DukeException;
import duke.commons.Message;
import duke.entities.Order;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * A command to remove an <code>Order</code> object from an <code>OrderList</code> object.
 */
public class DeleteOrderCommand extends OrderCommand implements Undoable {
    public static final String COMMAND_WORD = "remove";

    private List<Order> toDelete = new ArrayList<>();
    private List<Integer> toDeleteIndexes;

    public DeleteOrderCommand(int... index) {
        for (int i = 0; i < index.length; i++) {
            toDeleteIndexes.add(index[i]);
        }
    }

    public DeleteOrderCommand(int start, int end) {
        for (int i = start; i <= end; i++) {
            toDeleteIndexes.add(i);
        }
    }

    public DeleteOrderCommand(List<Integer> toDeleteIndexes) {
        this.toDeleteIndexes = toDeleteIndexes;
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        for (int i = 0; i < toDeleteIndexes.size(); i++) {
            bakingList.getOrderList().add(toDeleteIndexes.get(i), toDelete.get(i));
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
        for (int i : toDeleteIndexes) {
            if (i >= bakingList.getOrderList().size() || i < 0) {
                throw new DukeException(Message.MESSAGE_INVALID_RANGE);
            }
            toDelete.add(bakingList.getOrderList().get(i));
        }
        bakingList.getOrderList().removeAll(toDelete);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
        ui.showMessage("Order removed");
    }


}