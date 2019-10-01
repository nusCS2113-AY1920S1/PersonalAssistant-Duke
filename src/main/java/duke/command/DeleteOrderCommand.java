package duke.command;

import duke.commons.DukeException;
import duke.entities.Order;
import duke.parser.CommandParser;
import duke.storage.BakingList;
import duke.storage.SaleList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class DeleteOrderCommand extends UndoableCommand {

    private Order order;
    private int index;
    private Map<String, List<String>> params;

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

    private void checkParameters() throws DukeException {
        if (!(params.containsKey("secondary")
                ^ params.containsKey("i")
                ^ params.containsKey("id"))) {
            throw new DukeException("Too many parameters");
        }
        if (!params.containsKey("secondary")
                && !params.containsKey("i")
                && !params.containsKey("id")) {
            throw new DukeException("Too few parameters");
        }
    }

    private Order getOrder(List<Order> orders) throws DukeException {
        if (params.containsKey("secondary") || params.containsKey("i")) {
            return getOrderByIndexParameter(orders);
        } else if (params.containsKey("id")) {

        } else {
            throw new DukeException("Please specify an order");
        }
        return null;
    }

    private Order getOrderByIndexParameter(List<Order> orders) throws DukeException {
        String indexParameter;
        if (params.containsKey("secondary")) {
            indexParameter = params.get("secondary").get(0);
        } else {
            indexParameter = params.get("i").get(0);
        }
        try {
            index = Integer.parseInt(indexParameter) - 1;
            return orders.get(index);
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid index.");
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Index out of bound");
        }
    }

}
