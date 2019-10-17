package duke.logic.parser.shopping;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.shopping.DeleteShoppingCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.*;

public class DeleteShoppingCommandParser implements Parser<DeleteShoppingCommand> {

    @Override
    public DeleteShoppingCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_SHOPPING_INDEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_SHOPPING_INDEX).orElse(""));
        } catch (ParseException e) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new DeleteShoppingCommand(index);
    }
}
