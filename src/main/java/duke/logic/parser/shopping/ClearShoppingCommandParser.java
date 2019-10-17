package duke.logic.parser.shopping;

import duke.logic.command.shopping.ClearShoppingCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

public class ClearShoppingCommandParser implements Parser<ClearShoppingCommand> {

    @Override
    public ClearShoppingCommand parse(String args) throws ParseException {
        return new ClearShoppingCommand();
    }
}