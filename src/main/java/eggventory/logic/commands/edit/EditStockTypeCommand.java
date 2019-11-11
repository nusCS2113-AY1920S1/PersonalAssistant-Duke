package eggventory.logic.commands.edit;

import eggventory.commons.exceptions.DuplicateEntryException;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.logic.commands.Command;
import eggventory.commons.enums.CommandType;
import eggventory.model.items.StockType;
import eggventory.ui.Ui;

//@@author patwaririshab
/**
 * Edits the attributes of StockType.
 */
public class EditStockTypeCommand extends Command {

    private String stockType;
    private String newName;

    /**
     * Initializes the attributes of StockType.
     * @param type The type of Command.
     * @param stockType The String that uniquely identifies a StockType.
     * @param newValue The new value of the property of StockType.
     */
    public EditStockTypeCommand(CommandType type, String stockType, String newValue) {
        super(type);
        this.stockType = stockType;
        this.newName = newValue;
    }

    /**
     * Executes the actual edit of the StockType attribute.
     * @param list The StockList containing all the StockType.
     * @param ui Ui implementation to display output to.
     * @param storage Storage object to handle saving and loading of any data.
     * @return
     */
    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws DuplicateEntryException {
        String output;

        if (list.isExistingStockType(newName)) {
            throw new DuplicateEntryException(String.format("Sorry, \"%s\" is already an existing stock type.",
                    newName));

        } else {
            StockType edited = list.setStockType(stockType, newName);
            output = String.format("Awesome! I have successfully updated the following stockType name: %s\n",
                    edited.getName());
            storage.save(list);
        }
        ui.print(output);
        // Drawing stock data in GUI table.
        ui.drawTable(list.getAllStockTypesStruct());
        return output;
    }
}
