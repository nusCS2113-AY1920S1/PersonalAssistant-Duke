//package duke.logic.command.decrypted;
//
//import duke.logic.command.Undoable;
//import duke.commons.DukeException;
//import duke.entities.Sale;
//import duke.logic.parser.decrypted.CommandParser;
//import duke.storage.decrpted.BakingList;
//import duke.storage.decrpted.Storage;
//import duke.ui.Ui;
//
//import java.util.List;
//import java.util.Map;
//
//public class DeleteSale implements Undoable {
//
//    private Sale sale;
//    private int index;
//    private Map<String, List<String>> params;
//
//    public DeleteSale(Map<String, List<String>> params) throws DukeException {
//        this.params = params;
//        checkParameters();
//    }
//
//    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        this.sale = CommandParser.getSaleByIndexOrId(bakingList.getSaleList(), params);
//        this.index = CommandParser.getSaleIndex(bakingList.getSaleList(), params);
//        bakingList.getSaleList().remove(sale);
//        storage.serialize(bakingList);
//        ui.refreshSaleList(bakingList.getSaleList(), bakingList.getSaleList());
//    }
//
//    @Override
//    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        bakingList.getSaleList().add(index, sale);
//        storage.serialize(bakingList);
//        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
//    }
//
//    @Override
//    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        execute(bakingList, storage, ui);
//    }
//
//    private void checkParameters() throws DukeException {
//        if (!(params.containsKey("secondary")
//                ^ params.containsKey("i")
//                ^ params.containsKey("id"))) {
//            throw new DukeException("Too many parameters");
//        }
//        if (!params.containsKey("secondary")
//                && !params.containsKey("i")
//                && !params.containsKey("id")) {
//            throw new DukeException("Too few parameters");
//        }
//    }
//
//    private Sale getSale(List<Sale> sales) throws DukeException {
//        if (params.containsKey("secondary") || params.containsKey("i")) {
//            return getSaleByIndexParameter(sales);
//        } else if (params.containsKey("id")) {
//
//        } else {
//            throw new DukeException("Please specify an order");
//        }
//        return null;
//    }
//
//    private Sale getSaleByIndexParameter(List<Sale> sales) throws DukeException {
//        String indexParameter;
//        if (params.containsKey("secondary")) {
//            indexParameter = params.get("secondary").get(0);
//        } else {
//            indexParameter = params.get("i").get(0);
//        }
//        try {
//            index = Integer.parseInt(indexParameter) - 1;
//            return sales.get(index);
//        } catch (NumberFormatException e) {
//            throw new DukeException("Please enter a valid index.");
//        } catch (IndexOutOfBoundsException i) {
//            throw new DukeException("Index out of bound");
//        }
//    }
//
//}