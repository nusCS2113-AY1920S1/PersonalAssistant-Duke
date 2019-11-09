package duke.logic.parser.shopping;

import duke.commons.core.index.Index;
import duke.logic.command.shopping.EditShoppingCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_QUANTITY;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_REMARKS;
import static duke.logic.parser.shopping.ShoppingParserUtil.createShoppingDescriptor;

public class EditShoppingCommandParser implements Parser<EditShoppingCommand> {

    private static final String MESSAGE_EMPTY_INDEX = "Index cannot be empty.";
    private static final String MESSAGE_INVALID_INDEX = "Please enter a valid index in the list";

    @Override
    public EditShoppingCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_SHOPPING_NAME,
                PREFIX_SHOPPING_QUANTITY,
                PREFIX_SHOPPING_REMARKS,
                PREFIX_SHOPPING_COST
        );

        Index index;

        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDEX);
        }

        try {
            index = ParserUtil.parseIndex(map.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        return new EditShoppingCommand(index, createShoppingDescriptor(map));
    }
}
