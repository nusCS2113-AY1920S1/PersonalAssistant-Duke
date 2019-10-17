package eggventory.commands.delete;

import eggventory.ui.Cli;
import eggventory.StockList;
import eggventory.Storage;
import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.items.StockType;

public class DeleteStockTypeCommand extends Command {

    private String stockTypeName;

    public DeleteStockTypeCommand(CommandType type, String stockTypeName) {
        super(type);
        this.stockTypeName = stockTypeName;
    }

    @Override
    public String execute(StockList list, Cli cli, Storage storage) {

        String output;

        if (stockTypeName.equals("Uncategorised")) {
            output = "Sorry, Uncategorised is the default category, and cannot be deleted.";
            cli.print(output);
            return output;
        }

        StockType deleted = list.deleteStockType(stockTypeName);

        if (deleted == null) {
            output = String.format("Sorry, I cannot find the stock type \"%s\" refers to. "
                    + "Please try again.", stockTypeName);
            cli.print(output);
        } else {
            output = String.format("I deleted the following stockType: %s. "
                    + "I also deleted the following stocks of that type: \n"
                    + deleted.toString(), stockTypeName);
            storage.save(list);
            cli.print(output);
        }

        return output;
    }
}
