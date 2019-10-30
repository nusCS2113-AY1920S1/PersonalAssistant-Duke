package duke.logic.parser.sale;

import duke.logic.command.sale.ShowSaleCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

public class ShowSaleCommandParser implements Parser<ShowSaleCommand> {
    @Override
    public ShowSaleCommand parse(String args) throws ParseException {
        return new ShowSaleCommand();
    }
}
