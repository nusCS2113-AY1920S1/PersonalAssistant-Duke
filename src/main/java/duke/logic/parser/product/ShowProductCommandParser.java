package duke.logic.parser.product;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.product.ShowProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INDEX;
import static duke.logic.parser.product.ProductParserUtil.createProductDescriptor;

public class ShowProductCommandParser implements Parser<ShowProductCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ShowProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_PRODUCT_INDEX
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_PRODUCT_INDEX).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new ShowProductCommand(index, createProductDescriptor(map));
    }
}
