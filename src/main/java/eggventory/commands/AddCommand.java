package eggventory.commands;

import eggventory.StockType;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.items.DateTime;
import eggventory.exceptions.BadInputException;
import eggventory.enums.CommandType;

/**
 * Command objects for adding Stocks.
 */
public class AddCommand extends Command {

    private String stockType;
    private String stockCode;
    private int quantity;
    private String description;
    private String details;
    private DateTime[] dateTimes = new DateTime[2];

    /**
     * Initialises all the attributes of the details of the stock to be added.
     * @param type The type of command.
     * @param stockType The category/type of the stock.
     * @param stockCode The unique identifier code for the stock.
     * @param quantity The total quantity of the stock.
     * @param description User input description of the task to add.
     */
    public AddCommand(CommandType type, String stockType, String stockCode, int quantity, String description) {
        super(type);
        this.stockType = stockType;
        this.stockCode = stockCode;
        this.quantity = quantity;
        this.description = description;
    }

    /**
     * Executes the actual adding of stock to the StockType.
     * @param list StockType to add the item to.
     * @param ui Ui object to display output to.
     * @param storage Storage object to handle saving and loading of any data.
     */
    @Override
    public void execute(StockType list, Ui ui, Storage storage) {
        list.addStock(stockType, stockCode, quantity, description);
        ui.print("I have added the following stock to your list:\n"
                + list.getStock(list.getSize() - 1).toString());
    }

    /**
     * Executes the actual adding of task to the StockType.
     * Only to be used by Storage.load() - handles the adding without showing UI output.
     * @param list StockType to add the item to.
     */
    public void execute(StockType list) {
        list.addStock(stockType, stockCode, quantity, description);
    }
}
