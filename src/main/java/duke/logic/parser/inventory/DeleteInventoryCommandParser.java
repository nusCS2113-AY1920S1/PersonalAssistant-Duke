package duke.logic.parser.inventory;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.inventory.DeleteInventoryCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import java.util.Set;

public class DeleteInventoryCommandParser implements Parser<DeleteInventoryCommand> {

    private static final String MESSAGE_EMPTY_INDICES = "Indices cannot be empty.";
    private static final String MESSAGE_INVALID_INDEX = "Please enter a valid index in the list";

    @Override
    public DeleteInventoryCommand parse(String args) throws ParseException {
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

        return new DeleteInventoryCommand(indices);
    }
}
