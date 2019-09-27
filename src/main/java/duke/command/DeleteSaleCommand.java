package duke.command;

import duke.commons.DukeException;
import duke.entities.Sale;
import duke.storage.BakingList;
import duke.storage.SaleList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.Map;

public class DeleteSaleCommand extends Command {

    private Sale sale;
    private int index;
    private Map<String, List<String>> params;

    public DeleteSaleCommand(Map<String, List<String>> params) throws DukeException {
        this.params = params;
        checkParameters();
    }

    public void execute(SaleList saleList, Storage storage, Ui ui) throws DukeException {
        this.sale = getSale(saleList.getSaleList());
        saleList.getSaleList().remove(sale);
        storage.serializeSaleList(saleList);
        ui.refreshSaleList(saleList.getSaleList(), saleList.getSaleList());
    }

    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {}

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

    private Sale getSale(List<Sale> sales) throws DukeException {
        if (params.containsKey("secondary") || params.containsKey("i")) {
            return getSaleByIndexParameter(sales);
        } else if (params.containsKey("id")) {

        } else {
            throw new DukeException("Please specify an order");
        }
        return null;
    }

    private Sale getSaleByIndexParameter(List<Sale> sales) throws DukeException {
        String indexParameter;
        if (params.containsKey("secondary")) {
            indexParameter = params.get("secondary").get(0);
        } else {
            indexParameter = params.get("i").get(0);
        }
        try {
            index = Integer.parseInt(indexParameter) - 1;
            return sales.get(index);
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid index.");
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Index out of bound");
        }
    }

}