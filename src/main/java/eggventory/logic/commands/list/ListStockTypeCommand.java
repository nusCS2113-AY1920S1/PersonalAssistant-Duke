package eggventory.logic.commands.list;

import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.logic.commands.Command;
import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.ui.Ui;

public class ListStockTypeCommand extends Command {
    private String query;

    public ListStockTypeCommand(CommandType type, String query) {
        super(type);
        this.query = query;
    }


    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output = "";

        if (query.equals("all")) { // list stocktype all command
            String listString = "";
            listString = list.toStocktypeString(); //Should contain all the stockTypes already, need not iterate.
            output = listString;
            ui.print(output);
            // Drawing stock data in GUI table.
            ui.drawTable(list.getAllStockTypesStruct());
        } else { // list stocktype <Stock Type> command
            String listString = "";
            listString = list.findStock(query);
            output = listString;

            if (listString.equals("")) {
                ui.print("Invalid command: No such stocktype exists!");
            } else {
                ui.print(output);
                // Drawing data on stocks under specific stocktype in GUI table.
                ui.drawTable(list.getAllStocksInStockTypeStruct(query));
            }
        }

        return output;
    }
}
