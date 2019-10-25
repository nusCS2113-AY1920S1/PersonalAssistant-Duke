package duke.logic.parser.inventory;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.inventory.DeleteInventoryCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_INVENTORY_INDEX;

public class DeleteInventoryCommandParser implements Parser<DeleteInventoryCommand> {

    private static final String EMPTY_STRING = "";

    @Override
    public DeleteInventoryCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_INVENTORY_INDEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_INVENTORY_INDEX).orElse(EMPTY_STRING));
        } catch (ParseException e) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new DeleteInventoryCommand(index);
    }
}
