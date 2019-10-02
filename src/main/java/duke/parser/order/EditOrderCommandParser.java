package duke.parser.order;

import duke.command.EditOrderCommand;
import duke.parser.ArgumentMultimap;
import duke.parser.ArgumentTokenizer;
import duke.parser.CliSyntax;
import duke.parser.Parser;
import duke.parser.exceptions.ParseException;


public class EditOrderCommandParser implements Parser<EditOrderCommand> {
    @Override
    public EditOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_ORDER_INDEX,
                CliSyntax.PREFIX_ORDER_NAME,
                CliSyntax.PREFIX_ORDER_CONTACT,
                CliSyntax.PREFIX_ORDER_ITEM,
                CliSyntax.PREFIX_ORDER_DEADLINE,
                CliSyntax.PREFIX_ORDER_STATUS,
                CliSyntax.PREFIX_ORDER_REMARKS
        );
        return new EditOrderCommand();
    }

}
