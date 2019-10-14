package eggventory.commands;

import eggventory.items.StockType;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.StockList;
import eggventory.enums.CommandType;
import eggventory.items.Stock;

/**
 * Command objects for deleting stocks.
 * Requires the index (as listed by the system) of the stock. //TODO: Change this to the stock code.
 */
public class DeleteCommand extends Command {

    private String stockCode;

    public DeleteCommand(CommandType type, String stockCode) {
        super(type);
        this.stockCode = stockCode;
    }

    @Override
    public void execute(StockList list, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        try {
            list.deleteStock(stockCode);

            storage.save(list);

        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("That stock doesn't exist! Please check"
                    + " the available stocks again: ");
        }

    }
}
