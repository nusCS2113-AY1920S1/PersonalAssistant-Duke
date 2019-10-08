package eggventory.commands;

import eggventory.StockType;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.enums.CommandType;

/**
 * Currently not used.
 */
public class NumCommand extends Command {

    private int itemIndex;

    public NumCommand(CommandType type, int index) {
        super(type);
        this.itemIndex = index - 1; //Because of 0-indexing, user's request for item 1 means item 0.
    }

    public int getItemIndex() {
        return itemIndex;
    }

    @Override
    public void execute(StockType list, Ui ui, Storage storage) {
    }
}
