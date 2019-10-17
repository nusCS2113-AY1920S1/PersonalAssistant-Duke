package eggventory.commands;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.ui.Cli;
import eggventory.enums.CommandType;

public class ListCommand extends Command {
    public ListCommand(CommandType type) {
        super(type);
    }

    @Override
    public String execute(StockList list, Cli cli, Storage storage) {
        String output;
        int max = list.getStockQuantity();
        String listString = "";

        if (max == 0) {
            output = "The list is currently empty.";
            cli.print(output);
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
        cli.print(output);
        return output;
    }
}
