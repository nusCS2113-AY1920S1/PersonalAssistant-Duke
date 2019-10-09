package duke.parser.inventory;

import duke.command.inventory.AddInventoryCommand;
import duke.entities.inventory.Inventory;
import duke.parser.ArgumentMultimap;
import duke.parser.ArgumentTokenizer;
import duke.parser.CliSyntax;
import duke.parser.Parser;
import duke.parser.exceptions.ParseException;

public class AddInventoryCommandParser implements Parser<AddInventoryCommand> {

    @Override
    public AddInventoryCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_ORDER_NAME,
                CliSyntax.PREFIX_INVENTORY_QUANTITY
        );
        Inventory inventory = new Inventory();
        inventory.setName(map.getValue(CliSyntax.PREFIX_ORDER_NAME).orElse(""));
        inventory.setQuantity(Integer.parseInt(map.getValue(CliSyntax.PREFIX_INVENTORY_QUANTITY).orElse("")));

        return new AddInventoryCommand(inventory);
    }
}
