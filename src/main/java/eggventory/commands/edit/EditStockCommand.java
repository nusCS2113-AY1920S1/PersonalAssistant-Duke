package eggventory.commands.edit;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.enums.Property;
import eggventory.items.Stock;
import eggventory.ui.Cli;

/**
 * Command objects for editing stocks
 * Requires the stockCode (as listed by the system) of the stock.
 */
public class EditStockCommand extends Command {

    private String stockCode;
    private Property property; //Stores the property you want to edit
    private String newValue; //Stores the newValue you want

    /**
     * Initializes all the attributes of the details of the stock to be edited.
     * @param type The type of command.
     * @param stockCode The unique String that identifies a Stock.
     * @param property The property of the Stock that is to be changed.
     * @param newValue The newValue of the property.
     */
    public EditStockCommand(CommandType type, String stockCode, Property property, String newValue) {
        super(type);
        this.stockCode = stockCode;
        this.property = property;
        this.newValue = newValue;
    }

    /**
     * Executes the actual editing of the stock's property.
     * @param list StockList containing all the StockTypes.
     * @param cli Cli object instance to display output to.
     * @param storage  Storage object to handle saving and loading of any data.
     * @return String of the output, for JUnit testing.
     */
    @Override
    public String execute(StockList list, Cli cli, Storage storage) {
        String output;

        if (property == Property.STOCKCODE && list.isExistingStockCode(newValue)) {
            output = String.format("Sorry, the stock code \"%s\" is already assigned to a stock in the system. "
                    + "Please enter a different stock code.", newValue);
            cli.print(output);
            return output;
        }

        Stock edited = list.setStock(stockCode, property, newValue);
        output = String.format("Awesome! I have successfully updated the following stock: %s | %s | %d | %s\n",
                edited.getStockType(), edited.getStockCode(), edited.getQuantity(),
                edited.getDescription());
        storage.save(list);
        cli.print(output);
        return output;
    }
}
