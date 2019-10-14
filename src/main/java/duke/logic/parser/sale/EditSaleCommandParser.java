package duke.logic.parser.sale;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.sale.EditSaleCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.ParserUtil;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.*;
import static duke.logic.parser.sale.SaleParserUtil.createDescriptor;

public class EditSaleCommandParser implements Parser<EditSaleCommand> {
    @Override
    public EditSaleCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_SALE_DESCRIPTION,
                PREFIX_SALE_VALUE,
                PREFIX_SALE_DATE,
                PREFIX_SALE_REMARKS
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_ORDER_INDEX).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        return new EditSaleCommand(index, createDescriptor(map));
    }

}