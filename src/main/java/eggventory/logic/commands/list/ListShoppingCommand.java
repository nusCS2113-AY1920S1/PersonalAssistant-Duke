package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.logic.QuantityManager;
import eggventory.logic.commands.Command;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

public class ListShoppingCommand extends Command {

    public ListShoppingCommand() {
        super(CommandType.LIST);
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output = QuantityManager.printShoppingList(list);

        ui.drawTable(QuantityManager.printShoppingListTable(list));
        ui.print(output);
        return output;
    }
}
