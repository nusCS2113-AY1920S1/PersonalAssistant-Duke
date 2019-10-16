package duke.logic.parser.inventory;

import duke.logic.command.inventory.ClearInventoryCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.List;

public class ClearInventoryCommandParser implements Parser<ClearInventoryCommand> {

    @Override
    public ClearInventoryCommand parse(String args) throws ParseException {
        return new ClearInventoryCommand();
    }
}
