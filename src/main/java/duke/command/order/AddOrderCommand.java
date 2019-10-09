package duke.command.order;

import duke.command.Undoable;
import duke.commons.DukeException;
import duke.entities.Order;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * A command to add an <code>Order</code> object to an <code>OrderList</code> object.
 */
public class AddOrderCommand extends OrderCommand implements Undoable {

    public static final String COMMAND_WORD = "add";
    private final Order toAdd;

    public AddOrderCommand(Order toAdd) {
        this.toAdd = toAdd;
    }

    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        addOrder(toAdd, bakingList);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
        ui.showMessage("Order added");
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        bakingList.getOrderList().remove(toAdd);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
        ui.showMessage("Undo: Add order");
    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        execute(bakingList, storage, ui);
        ui.showMessage("Redo: Add order");
    }

    private void addOrder(Order order, BakingList bakingList) {
        bakingList.getOrderList().add(0, order);
    }

}
