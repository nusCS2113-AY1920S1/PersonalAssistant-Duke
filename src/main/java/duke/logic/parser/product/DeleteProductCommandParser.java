package duke.logic.parser.product;

import duke.commons.core.index.Index;
import duke.logic.command.product.DeleteProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import java.util.Set;

public class DeleteProductCommandParser implements Parser<DeleteProductCommand> {
    private static final String MESSAGE_EMPTY_INDICES = "Indices cannot be empty.";
    private static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index 0 is out of bound";

    @Override
    public DeleteProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);
        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDICES);
        }
        Set<Index> indices;
        try {
            indices = ParserUtil.getIndices(map.getPreamble());
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INDEX_OUT_OF_BOUND);
        }
       // return new DeleteProductCommand(ParserUtil.getIndices(map.getPreamble()));
        return new DeleteProductCommand(indices);
    }
}
