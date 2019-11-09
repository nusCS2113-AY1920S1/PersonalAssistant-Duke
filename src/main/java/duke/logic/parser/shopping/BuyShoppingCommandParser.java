package duke.logic.parser.shopping;

import duke.commons.core.index.Index;
import duke.logic.command.shopping.BuyShoppingCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import java.util.Set;

public class BuyShoppingCommandParser implements Parser<BuyShoppingCommand> {

    private static final String MESSAGE_EMPTY_INDICES = "Indices cannot be empty.";
    private static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index 0 is out of bound";

    @Override
    public BuyShoppingCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);

        Set<Index> indices;

        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDICES);
        }

        try {
            indices = ParserUtil.getIndices(map.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INDEX_OUT_OF_BOUND);
        }

        return new BuyShoppingCommand(indices);
    }
}
