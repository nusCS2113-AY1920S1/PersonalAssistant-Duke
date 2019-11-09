package duke.logic.parser.shopping;

import duke.commons.core.index.Index;
import duke.logic.command.shopping.DeleteShoppingCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import java.util.Set;

public class DeleteShoppingCommandParser implements Parser<DeleteShoppingCommand> {

    private static final String MESSAGE_EMPTY_INDICES = "Indices cannot be empty.";
    private static final String MESSAGE_INVALID_INDEX = "Please enter a valid index in the list";

    @Override
    public DeleteShoppingCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);

        Set<Index> indices;

        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDICES);
        }

        try {
            indices = ParserUtil.getIndices(map.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        return new DeleteShoppingCommand(indices);
    }
}
