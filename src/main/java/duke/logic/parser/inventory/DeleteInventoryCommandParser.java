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

import static duke.logic.parser.commons.CliSyntax.PREFIX_INVENTORY_INDEX;

public class DeleteInventoryCommandParser implements Parser<DeleteInventoryCommand> {

    private static final String MESSAGE_EMPTY_INDICES = "Indices cannot be empty.";
    private static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index 0 is out of bound";

    @Override
    public DeleteInventoryCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args);

        Set<Index> indices;

        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDICES);
        }

        try {
            indices = ParserUtil.getIndices(map.getPreamble());
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INDEX_OUT_OF_BOUND);
        }

        return new DeleteInventoryCommand(indices);
    }
}
