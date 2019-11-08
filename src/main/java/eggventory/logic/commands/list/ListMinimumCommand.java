package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.logic.QuantityManager;
import eggventory.logic.commands.Command;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

public class ListMinimumCommand extends Command {

    public ListMinimumCommand() {
        super(CommandType.LIST);
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output = QuantityManager.printMinimumOutput(list);

        ui.drawTable(QuantityManager.printMinimumTable(list));
        ui.print(output);
        return output;
    }
}
