package duke.logic.parser.order;

import duke.logic.command.order.ShowOrderCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;


public class ShowOrderCommandParser implements Parser<ShowOrderCommand> {

    @Override
    public ShowOrderCommand parse(String args) throws ParseException {

        return new ShowOrderCommand();
    }

}
