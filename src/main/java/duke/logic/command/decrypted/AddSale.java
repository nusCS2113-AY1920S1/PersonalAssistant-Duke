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
//public class AddSale implements Undoable {
//    private Map<String, List<String>> params;
//    private Sale sale;
//
//    public AddSale(Map<String, List<String>> params) throws DukeException {
//        this.params = params;
//    }
//
//    public void execute(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        sale = new Sale();
//        CommandParser.modifySale(params, sale);
//        addSale(sale, bakingList);
//        storage.serialize(bakingList);
//        ui.refreshSaleList(bakingList.getSaleList(), bakingList.getSaleList());
//    }
//
//    @Override
//    public void undo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        bakingList.getSaleList().remove(sale);
//        storage.serialize(bakingList);
//        ui.refreshSaleList(bakingList.getSaleList(), bakingList.getSaleList());
//    }
//
//    @Override
//    public void redo(BakingList bakingList, Storage storage, Ui ui) throws DukeException {
//        execute(bakingList, storage, ui);
//    }
//
//    private void addSale(Sale sale, BakingList bakingList) {
//        bakingList.getSaleList().add(0, sale);
//    }
//
//}