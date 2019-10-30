package duke.logic.parser.sale;

import duke.commons.core.Message;
import duke.logic.command.sale.ShowSaleCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

public class ShowSaleCommandParser implements Parser<ShowSaleCommand> {
    @Override
    public ShowSaleCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);

        if (!map.getPreamble().isBlank()) {
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }

        return new ShowSaleCommand();
    }
}
