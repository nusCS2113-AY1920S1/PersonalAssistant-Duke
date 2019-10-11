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
    public void execute(StockList list, Ui ui, Storage storage) {
        int max = list.getQuantity();
        String listString = "";
        if (max == 0) {
            ui.print("The list is currently empty.");
            return;
        }

        for (int i = 0; i < max; i++) { //Index starts from 0.
            // TODO: Change to StringBuilder - Raghav
            listString += (i + 1 + ". " + list.toString() + "\n");
        }
        ui.print(listString);
    }
}
