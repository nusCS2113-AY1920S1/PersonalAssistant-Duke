package eggventory.logic.commands.add;

import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.commons.exceptions.BadInputException;
import eggventory.ui.Ui;
import eggventory.logic.commands.Command;
import eggventory.commons.enums.CommandType;

//@@author Deculsion

public class AddStockTypeCommand extends Command {
    private String name;

    /**
     * Creates a new StockType in a StorageList.
     * @param type The type of command this is.
     * @param name Name of StockType to add.
     */
    public AddStockTypeCommand(CommandType type, String name) {
        super(type);
        this.name = name;
    }

    /**
     * Executes actual adding of StockType.
     * @param list StockType to add the item to.
     * @param ui Ui implementation to display output to.
     * @param storage Storage object to handle saving and loading of any data.
     */
    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output;

        if (list.isExistingStockType(name)) {
            throw new BadInputException(String.format("Sorry, \"%s\" is already an existing stock type.", name));

        } else {
            list.addStockType(name);
            output = String.format("Nice! I have successfully added the stocktype: %s", name);
        }
        ui.print(output);
        // Drawing stock data in GUI table.
        ui.drawTable(list.getAllStockTypesStruct());
        return output;
    }

    //@@author patwaririshab
    /**
     * Executes the actual adding of task to the StockType.
     * Only to be used by Storage.load() - handles the adding without showing UI output.
     * @param list StockList to add the item to.
     */
    public void execute(StockList list) {
        if (list.isExistingStockType(name)) {
            //Do Nothing
            //This is to prevent Uncategorised from being loaded multiple times
        } else {
            list.addStockType(name);
            System.out.println(name);
        }
    }
    //@@author
}
