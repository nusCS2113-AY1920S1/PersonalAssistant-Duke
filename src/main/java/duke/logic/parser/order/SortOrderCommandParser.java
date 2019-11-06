package duke.logic.parser.order;

import duke.commons.core.Message;
import duke.logic.command.order.SortOrderCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code SortOrderCommand}
 */
public class SortOrderCommandParser implements Parser<SortOrderCommand> {

    @Override
    public SortOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
            CliSyntax.PREFIX_ORDER_SORT_REVERSE
        );

        if (map.getPreamble().isBlank()) {
            throw new ParseException(Message.MESSAGE_INVALID_CRITERIA);
        }

        //Throws exception if value of reverse prefix is not empty / blank
        if (map.getValue(CliSyntax.PREFIX_ORDER_SORT_REVERSE).isPresent()
            && !map.getValue(CliSyntax.PREFIX_ORDER_SORT_REVERSE).get().isBlank()) {
            throw new ParseException(String.format(
                Message.MESSAGE_INVALID_PREFIX_VALUE,
                map.getValue(CliSyntax.PREFIX_ORDER_SORT_REVERSE).get())
            );
        }


        try {
            SortOrderCommand.SortCriteria criteria = SortOrderCommand.SortCriteria
                .valueOf(map.getPreamble().toUpperCase());
            boolean isReversed = map.getValue(CliSyntax.PREFIX_ORDER_SORT_REVERSE).isEmpty();
            return new SortOrderCommand(criteria, isReversed);

        } catch (IllegalArgumentException e) {
            throw new ParseException(Message.MESSAGE_INVALID_CRITERIA);
        }

    }

}
