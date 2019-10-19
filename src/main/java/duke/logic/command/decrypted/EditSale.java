//package duke.logic.command.decrypted;
//
//import duke.logic.command.Undoable;
//import duke.commons.DukeException;
//import duke.entities.Sale;
//import duke.logic.parser.commons.TimeParser;
//import duke.logic.parser.decrypted.CommandParser;
//import duke.storage.decrpted.BakingList;
//import duke.storage.decrpted.Storage;
//import duke.ui.Ui;
//
//import java.util.List;
//import java.util.Map;
//
//public class EditSale implements Undoable {
//
//    private Map<String, List<String>> params;
//    private Sale sale;
//    private Sale unmodifiedSale = new Sale();
//
//    public EditSale(Map<String, List<String>> params) throws DukeException {
//        if (!(params.containsKey("i") == !params.containsKey("id"))) {
//            throw new DukeException("Please specify order ID or index");
//        }
//
//        this.params = params;
//    }
//
//    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        sale = getSale(bakingList);
//        copySale(unmodifiedSale, sale);
//        CommandParser.modifySale(params, sale);
//        storage.serialize(bakingList);
//        ui.refreshSaleList(bakingList.getSaleList(), bakingList.getSaleList());
//    }
//
//    @Override
//    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        copySale(sale, unmodifiedSale);
//        storage.serialize(bakingList);
//        ui.refreshSaleList(bakingList.getSaleList(), bakingList.getSaleList());
//    }
//
//    @Override
//    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        sale = getSale(bakingList);
//        CommandParser.modifySale(params, sale);
//        storage.serialize(bakingList);
//        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
//    }
//
//    private Sale getSale(BakingList bakingList) throws DukeException {
//        if (params.containsKey(("i"))) {
//            return getSaleByIndex(bakingList, params.get("i").get(0));
//        } else {
//            return getSaleById(bakingList, params.get("id").get(0));
//        }
//    }
//
//    private Sale getSaleById(BakingList bakingList, String i) throws DukeException {
//        long id;
//        try {
//            id = Long.parseLong(params.get("id").get(0));
//        } catch (NumberFormatException e) {
//            throw new DukeException("Please provide a valid order ID");
//        }
//
//        for (Sale sale : bakingList.getSaleList()) {
//            if (sale.getId() == id) {
//                return sale;
//            }
//        }
//
//        throw new DukeException("Unknown ID");
//    }
//
//    private Sale getSaleByIndex(BakingList bakingList, String i) throws DukeException {
//        int index;
//        try {
//            index = Integer.parseInt(params.get("i").get(0)) - 1;
//        } catch (NumberFormatException e) {
//            throw new DukeException("Please provide a valid index");
//        }
//
//        if (index < 0 || index >= bakingList.getSaleList().size()) {
//            throw new DukeException("Index out of bound.");
//        }
//
//        return bakingList.getSaleList().get(index);
//    }
//
//    private void modifySale(Sale sale, Map<String, List<String>> params) throws DukeException {
//        if (params.containsKey("desc")) {
//            sale.setDescription(params.get("desc").get(0));
//        }
//        if (params.containsKey("contact")) {
//            sale.setValue(Double.parseDouble(params.get("value").get(0)));
//        }
//        if (params.containsKey("rmk")) {
//            sale.setRemarks(params.get("rmk").get(0));
//        }
//        if (params.containsKey("at")) {
//            sale.setSaleDate(TimeParser.convertStringToDate(params.get("at").get(0)));
//        }
//    }
//
//    private void copySale(Sale to, Sale from) {
//        to.setId(from.getId());
//        to.setDescription(from.getDescription());
//        to.setValue(from.getValue());
//        to.setSaleDate(from.getSaleDate());
//        to.setRemarks(from.getRemarks());
//    }
//}