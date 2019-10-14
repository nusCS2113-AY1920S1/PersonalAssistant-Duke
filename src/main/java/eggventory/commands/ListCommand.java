package eggventory.commands;

import eggventory.StockList;
import eggventory.items.StockType;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.enums.CommandType;

public class ListCommand extends Command {
    public ListCommand(CommandType type) {
        super(type);
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output;
        int max = list.getQuantity();
        String listString = "";
        if (max == 0) {
            output = "The list is currently empty.";
            ui.print(output);
            return output;
        }

        for (int i = 0; i < max; i++) { //Index starts from 0.
            // TODO: Change to StringBuilder - Raghav
            listString += (i + 1 + ". " + list.toString() + "\n");
        }
        output = listString;
        ui.print(output);
        return output;
    }
}
