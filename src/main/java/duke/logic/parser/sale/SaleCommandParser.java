package duke.logic.parser.sale;

import duke.commons.core.Message;
import duke.logic.command.sale.AddSaleCommand;
import duke.logic.command.sale.SaleCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

public class SaleCommandParser implements SubCommandParser<SaleCommand> {

    @Override
    public SaleCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
        case AddSaleCommand.COMMAND_WORD:
            return new AddSaleCommandParser().parse(args);
        default:
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}