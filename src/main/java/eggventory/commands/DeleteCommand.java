package eggventory.commands;

import eggventory.StockType;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.enums.CommandType;
import eggventory.items.Stock;

/**
 * Command objects for deleting stocks.
 * Requires the index (as listed by the system) of the stock. //TODO: Change this to the stock code.
 */
public class DeleteCommand extends Command {

    private int itemIndex;

    public DeleteCommand(CommandType type, int index) {
        super(type);
        this.itemIndex = index - 1;
    }

    @Override
    public void execute(StockType list, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        try {
            Stock item = list.getStock(itemIndex);
            list.deleteStock(itemIndex);
            ui.print("Okay! I've deleted this stock:\n" + item.toString());

        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("That stock doesn't exist! Please check"
                    + " the available stocks again: ");
        }
    }
}
