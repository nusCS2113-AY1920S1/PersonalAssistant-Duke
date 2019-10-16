package eggventory.commands;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.Ui;
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
    public String execute(StockList list, Ui ui, Storage storage) throws IndexOutOfBoundsException {

        Stock deleted = list.deleteStock(stockCode);
        String output;
        if (deleted == null) {
            output = String.format("Sorry, I cannot find the stock that stock code \"%s\" refers to. "
                    + "Please try again.", stockCode);
            ui.print(output);
            return output;
        } else {
            output = String.format("I deleted the following stock: StockType: %s StockCode: %s Quantity: %d "
                    + "Description: %s", deleted.getStockType(), stockCode,
                    deleted.getQuantity(), deleted.getDescription());
            storage.save(list);
            ui.print(output);
            return output;
        }

        /*
            try {
                list.deleteStock(stockCode);

                storage.save(list);

            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException("That stock doesn't exist! Please check"
                        + " the available stocks again: ");
            }
            return null;
         */
    }
}
