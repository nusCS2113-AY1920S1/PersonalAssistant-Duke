package eggventory.commands.add;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.Ui;
import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.exceptions.BadInputException;

public class AddStockTypeCommand extends Command {
    private String name;
    private CommandType type;

    /**
     * Creates a new StockType in a StorageList.
     * @param type The type of command this is.
     * @param name Name of StockType to add.
     */
    public AddStockTypeCommand(CommandType type, String name) {
        this.name = name;
        this.type = type;
    }

    /**
     * Executes actual adding of StockType.
     * @param list StockType to add the item to.
     * @param ui Ui object to display output to.
     * @param storage Storage object to handle saving and loading of any data.
     */
    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        String output;

        list.addStockType(name);
        output = String.format("Nice! I have successfully added the stocktype: %s", name);

        ui.print(output);

        return output;
    }
}
