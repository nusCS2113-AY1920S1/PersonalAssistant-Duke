package eggventory.commands.delete;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.Ui;
import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.items.Stock;

/**
 * Command objects for deleting stocks.
 * Requires the index (as listed by the system) of the stock. //TODO: Change this to the stock code.
 */
public class DeleteStockCommand extends Command {

    private String stockCode;

    public DeleteStockCommand(CommandType type, String stockCode) {
        super(type);
        this.stockCode = stockCode;
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) {

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
    }
}
