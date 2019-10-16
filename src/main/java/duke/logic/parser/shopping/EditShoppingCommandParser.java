package duke.logic.parser.shopping;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.shopping.EditShoppingCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.*;
import static duke.logic.parser.shopping.ShoppingParserUtil.createShoppingDescriptor;

public class EditShoppingCommandParser implements Parser<EditShoppingCommand> {

    @Override
    public EditShoppingCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_SHOPPING_INDEX,
                PREFIX_SHOPPING_NAME,
                PREFIX_SHOPPING_QUANTITY,
                PREFIX_SHOPPING_REMARKS,
                PREFIX_SHOPPING_COST
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_SHOPPING_INDEX).orElse(""));
        } catch (ParseException e) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new EditShoppingCommand(index, createShoppingDescriptor(map));
    }
}
