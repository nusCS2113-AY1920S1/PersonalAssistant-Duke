package eggventory.logic.commands.delete;

import eggventory.commons.exceptions.BadInputException;
import eggventory.ui.Ui;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.logic.commands.Command;
import eggventory.commons.enums.CommandType;
import eggventory.model.items.Stock;

//@@author cyanoei

/**
 * Command objects for deleting stocks.
 * Requires the StockCode of the Stock.
 */
public class DeleteStockCommand extends Command {

    private String stockCode;

    public DeleteStockCommand(CommandType type, String stockCode) {
        super(type);
        this.stockCode = stockCode;
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {

        Stock deleted = list.deleteStock(stockCode);
        String output;
        if (deleted == null) {

            output = String.format("Sorry, I cannot find the stock that stock code \"%s\" refers to. "
                    + "Please try again.", stockCode);
            throw new BadInputException(output);
        } else {
            output = String.format("I deleted the following stock: StockType: %s StockCode: %s Quantity: %d "
                    + "Description: %s", deleted.getStockType(), stockCode,
                    deleted.getQuantity(), deleted.getDescription());
            storage.save(list);
            ui.print(output);
            // Drawing stock data in GUI table.
            ui.drawTable(list.getAllStocksStruct());
            return output;
        }
    }
}
