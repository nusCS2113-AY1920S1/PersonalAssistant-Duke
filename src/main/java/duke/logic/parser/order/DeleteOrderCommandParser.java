package duke.logic.parser.order;

import duke.logic.command.order.DeleteOrderCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;


public class DeleteOrderCommandParser implements Parser<DeleteOrderCommand> {
    private static final String MESSAGE_EMPTY_INDICES = "Indices cannot be empty.";

    @Override
    public DeleteOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);

        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDICES);
        }


        return new DeleteOrderCommand(ParserUtil.getIndices(map.getPreamble()));
    }

}

