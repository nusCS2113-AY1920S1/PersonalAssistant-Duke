package duke.command;

import duke.commons.DukeException;
import duke.entities.Sale;
import duke.parser.CommandParser;
import duke.parser.TimeParser;
import duke.storage.SaleList;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class AddSaleCommand extends UndoableCommand {
    private Map<String, List<String>> params;
    private Sale sale;

    public AddSaleCommand(Map<String, List<String>> params) throws DukeException {
        this.params = params;
    }

    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        sale = new Sale();
        addSale(sale, bakingList);
        storage.serialize(bakingList);
        ui.refreshSaleList(bakingList.getSaleList(), bakingList.getSaleList());
    }

    @Override
    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        bakingList.getSaleList().remove(sale);
        storage.serialize(bakingList);
        ui.refreshSaleList(bakingList.getSaleList(), bakingList.getSaleList());
    }

    @Override
    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
        execute(bakingList, storage, ui);
    }

    private void addSale(Sale sale, BakingList bakingList) {
        bakingList.getSaleList().add(0, sale);
    }

}