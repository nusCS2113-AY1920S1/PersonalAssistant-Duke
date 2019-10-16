package duke.logic.parser.inventory;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.inventory.EditInventoryCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.*;
import static duke.logic.parser.inventory.InventoryParserUtil.createInventoryDescriptor;

public class EditInventoryCommandParser implements Parser<EditInventoryCommand> {

    @Override
    public EditInventoryCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_INVENTORY_INDEX,
                PREFIX_INVENTORY_NAME,
                PREFIX_INVENTORY_QUANTITY,
                PREFIX_INVENTORY_REMARKS
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_INVENTORY_INDEX).orElse(""));
        } catch (ParseException e) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new EditInventoryCommand(index, createInventoryDescriptor(map));
    }
}
