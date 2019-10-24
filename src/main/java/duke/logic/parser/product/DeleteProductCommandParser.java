package duke.logic.parser.product;

import duke.logic.command.product.DeleteProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

public class DeleteProductCommandParser implements Parser<DeleteProductCommand> {
    private static final String MESSAGE_EMPTY_INDICES = "Indices cannot be empty.";

    @Override
    public DeleteProductCommand parse(String args) throws ParseException {

        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);

        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDICES);
        }

        return new DeleteProductCommand(ParserUtil.getIndices(map.getPreamble()));
    }
}
