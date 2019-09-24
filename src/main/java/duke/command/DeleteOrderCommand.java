package duke.command;

import duke.commons.DukeException;
import duke.entities.Order;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

public class DeleteOrderCommand extends UndoableCommand {

    private Order order;
    private int index;

    public DeleteOrderCommand(int index) {
        this.index = index - 1;
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {

    }

    @Override
    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        if (index < 0 || index >= bakingList.getOrderList().size()) {
            throw new DukeException("Index out of bound.");
        }
        this.order = bakingList.getOrderList().get(index);
        bakingList.getOrderList().remove(index);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }
}
