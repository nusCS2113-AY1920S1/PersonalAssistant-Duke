package duke.logic.parser.inventory;

import duke.commons.core.index.Index;
import duke.logic.command.inventory.EditInventoryCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_INVENTORY_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_INVENTORY_QUANTITY;
import static duke.logic.parser.commons.CliSyntax.PREFIX_INVENTORY_REMARKS;
import static duke.logic.parser.inventory.InventoryParserUtil.createInventoryDescriptor;

public class EditInventoryCommandParser implements Parser<EditInventoryCommand> {

    private static final String MESSAGE_EMPTY_INDEX = "Index cannot be empty.";
    private static final String MESSAGE_INVALID_INDEX = "Please enter a valid index in the list";

    @Override
    public EditInventoryCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_INVENTORY_NAME,
                PREFIX_INVENTORY_QUANTITY,
                PREFIX_INVENTORY_REMARKS
        );

        Index index;

        if (map.getPreamble().isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_INDEX);
        }

        try {
            index = ParserUtil.parseIndex(map.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        return new EditInventoryCommand(index, createInventoryDescriptor(map));
    }
}
