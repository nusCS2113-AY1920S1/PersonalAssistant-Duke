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

public class AddSaleCommand extends Command {
    private Map<String, List<String>> params;
    private Sale sale;

    public AddSaleCommand(Map<String, List<String>> params) throws DukeException {
        this.params = params;
    }



    public void execute(SaleList saleList, Storage storage, Ui ui) throws DukeException {
        sale = getSale();
        addSale(sale, saleList);
        storage.serializeSaleList(saleList);
        ui.refreshSaleList(saleList.getSaleList(), saleList.getSaleList());
    }

    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {}

    private Sale getSale() throws DukeException {
        Sale sale = new Sale();

        if (params.containsKey("desc")) {
            sale.setDescription(params.get("name").get(0));
        }
        if (params.containsKey("value")) {
            sale.setValue(Double.parseDouble(params.get("value").get(0)));
        }

        if (params.containsKey("at")) {
            sale.setSaleDate(TimeParser.convertStringToDate(params.get("at").get(0)));
        }
        if (params.containsKey("rmk")) {
            sale.setRemarks(params.get("rmk").get(0));
        }

        return sale;
    }

    private void addSale(Sale sale, SaleList saleList) {
        saleList.getSaleList().add(0, sale);
    }

}