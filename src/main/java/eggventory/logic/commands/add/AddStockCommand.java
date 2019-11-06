package eggventory.logic.commands.add;

import eggventory.model.LoanList;
import eggventory.model.StockList;
import eggventory.logic.commands.Command;
import eggventory.commons.exceptions.BadInputException;
import eggventory.ui.Ui;
import eggventory.storage.Storage;
import eggventory.model.items.DateTime;
import eggventory.commons.enums.CommandType;

/**
 * Command objects for adding Stocks.
 */
public class AddStockCommand extends Command {

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
    public AddStockCommand(CommandType type, String stockType, String stockCode, int quantity, String description) {
        super(type);
        this.stockType = stockType;
        this.stockCode = stockCode;
        this.quantity = quantity;
        this.description = description;
    }

    /**
     * Executes the actual adding of stock to the StockType.
     * @param list StockType to add the item to.
     * @param ui Ui implementation to display output to.
     * @param storage Storage object to handle saving and loading of any data.
     * @throws BadInputException if any of the inputs are not accepted.
     */
    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output;

        if (list.isExistingStockCode(stockCode)) {
            throw new BadInputException(String.format("Sorry, the stock code \"%s\" is already"
                    + " assigned to a stock in the system. Please enter a different stock code.", stockCode));
        } else {
            list.addStock(stockType, stockCode, quantity, description);
            storage.save(list);
            output = String.format("Nice! I have successfully added the stock: StockType: %s StockCode: %s "
                    + "Quantity: %d Description: %s", stockType, stockCode, quantity, description);
        }

        ui.print(output);
        // Drawing stock data in GUI table.
        ui.drawTable(list.getAllStocksStruct());
        return output;
    }

    /**
     * Executes the actual adding of task to the StockType.
     * Only to be used by Storage.load() - handles the adding without showing UI output.
     * @param list StockType to add the item to.
     * @throws BadInputException likely if user had edited the save file to have unaccepted values.
     */
    public void execute(StockList list) throws BadInputException {
        list.addStock(stockType, stockCode, quantity, description);
        LoanList.addStock(stockCode, quantity);
    }
}
