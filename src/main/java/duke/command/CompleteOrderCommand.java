package duke.command;

import duke.commons.DukeException;
import duke.entities.Order;
import duke.parser.CommandParser;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class CompleteOrderCommand extends UndoableCommand {
    private Order.Status status;
    private Map<String, List<String>> params;

    public CompleteOrderCommand(Map<String, List<String>> params) throws DukeException {
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
        Order order = CommandParser.getOrderByIndexOrId(bakingList.getOrderList(), params);
        order.setStatus(Order.Status.COMPLETED);
        storage.serialize(bakingList);
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
    }
}
