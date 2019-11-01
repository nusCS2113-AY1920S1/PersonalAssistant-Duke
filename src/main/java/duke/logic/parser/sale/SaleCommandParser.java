package duke.logic.parser.sale;

import duke.commons.core.Message;
import duke.logic.command.sale.SaleCommand;
import duke.logic.command.sale.AddSaleCommand;
import duke.logic.command.sale.DeleteSaleCommand;
import duke.logic.command.sale.EditSaleCommand;
import duke.logic.command.sale.FilterSaleCommand;
import duke.logic.command.sale.ShowSaleCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code SaleCommand}.
 */
public class SaleCommandParser implements SubCommandParser<SaleCommand> {

    @Override
    public SaleCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
        case AddSaleCommand.COMMAND_WORD:
            return new AddSaleCommandParser().parse(args);
        case DeleteSaleCommand.COMMAND_WORD:
            return new DeleteSaleCommandParser().parse(args);
        case EditSaleCommand.COMMAND_WORD:
            return new EditSaleCommandParser().parse(args);
        case FilterSaleCommand.COMMAND_WORD:
            return new FilterSaleCommandParser().parse(args);
        case ShowSaleCommand.COMMAND_WORD:
            return new ShowSaleCommandParser().parse(args);
        default:
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}