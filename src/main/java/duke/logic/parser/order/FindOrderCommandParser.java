package duke.logic.parser.order;

import duke.logic.command.order.CompleteOrderCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;


public class FindOrderCommandParser implements Parser<CompleteOrderCommand> {

    @Override
    public CompleteOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
            CliSyntax.PREFIX_ORDER_STATUS

        );

        if (map.getPreamble().isBlank()) {
            throw new ParseException("");
        }


        return new CompleteOrderCommand(ParserUtil.getIndices(map.getPreamble()));
    }

}
