package eggventory.logic.commands.list;

import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.logic.commands.Command;
import eggventory.commons.enums.CommandType;
import eggventory.ui.Ui;

public class ListStockCommand extends Command {

    public ListStockCommand(CommandType type) {
        super(type);
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output = "";

        //Outstanding case when list is empty
        int max = list.getTotalNumberOfStocks();
        String listString = "";
        if (max == 0) {
            output = "The list is currently empty.";
            ui.print(output);
            return output;
        }

        listString = list.toString(); //Should contain all the stockTypes already, need not iterate.
        output = listString;
        ui.print(output);
        // Drawing stock data in GUI table.
        ui.drawTable(list.getAllStocksStruct());

        return output;
    }
}
