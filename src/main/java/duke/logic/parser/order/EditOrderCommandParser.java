package duke.logic.parser.order;

import duke.commons.core.index.Index;
import duke.logic.command.order.EditOrderCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_CONTACT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_DEADLINE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_ITEM;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_REMARKS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_STATUS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_ORDER_TOTAL;
import static duke.logic.parser.order.OrderParserUtil.createDescriptor;

/**
 * A parser that parses {@code EditOrderCommand}.
 */
public class EditOrderCommandParser implements Parser<EditOrderCommand> {
    @Override
    public EditOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
            PREFIX_CUSTOMER_NAME,
            PREFIX_CUSTOMER_CONTACT,
            PREFIX_ORDER_ITEM,
            PREFIX_ORDER_DEADLINE,
            PREFIX_ORDER_REMARKS,
            PREFIX_ORDER_TOTAL,
            PREFIX_ORDER_STATUS
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(e.getMessage());
        }

        return new EditOrderCommand(index, createDescriptor(map));
    }

}
