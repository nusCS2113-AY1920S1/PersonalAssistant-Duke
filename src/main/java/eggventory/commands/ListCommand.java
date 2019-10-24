package eggventory.commands;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.ui.Ui;
import eggventory.enums.CommandType;

public class ListCommand extends Command {
    private String query;

    public ListCommand(CommandType type, String query) {
        super(type);
        this.query = query;
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output = "";

        if (this.query.equals("stock")) { //list stock command
            //Outstanding case when list is empty
            int max = list.getStockQuantity();
            String listString = "";
            if (max == 0) {
                output = "The list is currently empty.";
                ui.print(output);
                return output;
            }

            listString = list.toString(); //Should contain all the stockTypes already, need not iterate.
            output = listString;
            ui.print(output);

        } else if (this.query.equals("stocktype")) { //list stocktype command
            String listString = "";
            listString = list.toStocktypeString(); //Should contain all the stockTypes already, need not iterate.
            output = listString;
            ui.print(output);

        } else { // list <stocktype> command
            String listString = "";
            listString = list.findStock(this.query);
            output = listString;
            if (listString.equals("")) {
                ui.print("Invalid command: No such stocktype. list stock / list stocktype/ list <stocktype>");
            } else {
                ui.print(output);
            }
        }
        return output;
    }
}
