package duke.parser.order;

import duke.command.DeleteOrder;
import duke.parser.*;
import duke.parser.exceptions.ParseException;


public class DeleteOrderCommandParser implements Parser<DeleteOrder> {
    @Override
    public DeleteOrder parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_ORDER_INDEX
        );
        return new DeleteOrder(ParserUtil.getIndexes(map.getPreamble()));
    }

}
