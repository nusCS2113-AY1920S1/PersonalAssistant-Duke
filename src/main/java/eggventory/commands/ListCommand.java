package eggventory.commands;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.Ui;
import eggventory.enums.CommandType;

public class ListCommand extends Command {
    public ListCommand(CommandType type) {
        super(type);
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output;
        int max = list.getStockQuantity();
        String listString = "";

        if (max == 0) {
            output = "The list is currently empty.";
            ui.print(output);
            return output;
        }

        listString = list.toString(); //Should contain all the stockTypes already, need not iterate.

        /*
        for (int i = 0; i < max; i++) { //Index starts from 0.
            // TODO: Change to StringBuilder - Raghav
            listString += (i + 1 + ". " + list.toString() + "\n");
        }
         */

        output = listString;
        ui.print(output);
        return output;
    }
}
