package duke.command;

import duke.commons.DukeException;
import duke.entities.Sale;
import duke.parser.CommandParser;
import duke.parser.TimeParser;
import duke.storage.BakingList;
import duke.storage.SaleList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class EditSaleCommand extends Command {
    private Map<String, List<String>> params;

    public EditSaleCommand(Map<String, List<String>> params) throws DukeException {
        if (!(params.containsKey("i") == !params.containsKey("id"))) {
            throw new DukeException("Please specify order ID or index");
        }

        this.params = params;
    }

    public void execute(SaleList saleList, Storage storage, Ui ui) throws DukeException {
        Sale sale = getSale(saleList);
        modifySale(sale, params);
        storage.serializeSaleList(saleList);
        ui.refreshSaleList(saleList.getSaleList(), saleList.getSaleList());
    }

    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {}

    private Sale getSale(SaleList saleList) throws DukeException {
        if (params.containsKey(("i"))) {
            return getSaleByIndex(saleList, params.get("i").get(0));
        } else {
            return getSaleById(saleList, params.get("id").get(0));
        }
    }

    private Sale getSaleById(SaleList saleList, String i) throws DukeException {
        long id;
        try {
            id = Long.parseLong(params.get("id").get(0));
        } catch (NumberFormatException e) {
            throw new DukeException("Please provide a valid order ID");
        }

        for (Sale sale : saleList.getSaleList()) {
            if (sale.getId() == id) {
                return sale;
            }
        }

        throw new DukeException("Unknown ID");
    }

    private Sale getSaleByIndex(SaleList saleList, String i) throws DukeException {
        int index;
        try {
            index = Integer.parseInt(params.get("i").get(0)) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please provide a valid index");
        }

        if (index < 0 || index >= saleList.getSaleList().size()) {
            throw new DukeException("Index out of bound.");
        }

        return saleList.getSaleList().get(index);
    }

    private void modifySale(Sale sale, Map<String, List<String>> params) throws DukeException {
        if (params.containsKey("desc")) {
            sale.setDescription(params.get("desc").get(0));
        }
        if (params.containsKey("contact")) {
            sale.setValue(Double.parseDouble(params.get("value").get(0)));
        }
        if (params.containsKey("rmk")) {
            sale.setRemarks(params.get("rmk").get(0));
        }
        if (params.containsKey("at")) {
            sale.setSaleDate(TimeParser.convertStringToDate(params.get("at").get(0)));
        }
    }
}