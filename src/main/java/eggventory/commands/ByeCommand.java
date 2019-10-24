package eggventory.commands;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.enums.CommandType;
import eggventory.ui.Ui;

public class ByeCommand extends Command {

    public ByeCommand(CommandType type) {
        super(type);
    }

    public String execute(StockList list, Ui ui, Storage storage) {
        storage.save(list);
        ui.printExitMessage();

        return null;
    }
}
