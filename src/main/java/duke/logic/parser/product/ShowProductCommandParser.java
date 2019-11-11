package duke.logic.parser.product;

import duke.commons.core.index.Index;
import duke.logic.command.product.ShowProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.message.ProductMessageUtils.MESSAGE_EMPTY_INDICES;

/**
 * A parser that parses {@code ShowProductCommand}.
 */
public class ShowProductCommandParser implements Parser<ShowProductCommand> {


    @Override
    public ShowProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);

        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDICES);
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(map.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }

        return new ShowProductCommand(index);
    }
}
