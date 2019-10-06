package duke.logic.parser.order;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.order.EditOrderCommand;
import duke.logic.parser.commons.*;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.*;


public class EditOrderCommandParser implements Parser<EditOrderCommand> {
    @Override
    public EditOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_ORDER_INDEX,
                PREFIX_CUSTOMER_NAME,
                PREFIX_CUSTOMER_CONTACT,
                PREFIX_ORDER_ITEM,
                PREFIX_ORDER_DEADLINE,
                PREFIX_ORDER_STATUS,
                PREFIX_ORDER_REMARKS
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(map.getValue(PREFIX_ORDER_INDEX).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        EditOrderCommand.EditOrderDescriptor editOrderDescriptor = new EditOrderCommand.EditOrderDescriptor();
        if (map.getValue(PREFIX_CUSTOMER_NAME).isPresent()) {
            editOrderDescriptor.setCustomerName(map.getValue(PREFIX_CUSTOMER_NAME).get());
        }
        if (map.getValue(PREFIX_CUSTOMER_CONTACT).isPresent()) {
            editOrderDescriptor.setCustomerContact(map.getValue(PREFIX_CUSTOMER_CONTACT).get());
        }
        if (map.getValue(PREFIX_ORDER_DEADLINE).isPresent()) {
            editOrderDescriptor.setDeliveryDate(TimeParser.convertStringToDate(
                    map.getValue(PREFIX_ORDER_DEADLINE).get()));
        }
        if (map.getValue(PREFIX_ORDER_REMARKS).isPresent()) {
            editOrderDescriptor.setRemarks(map.getValue(PREFIX_ORDER_REMARKS).get());
        }
        if (map.getValue(PREFIX_ORDER_ITEM).isPresent()) {
            editOrderDescriptor.setItems(ParserUtil.parseItems(map.getAllValues(PREFIX_ORDER_ITEM)));
        }
        if (map.getValue(PREFIX_ORDER_STATUS).isPresent()) {
            editOrderDescriptor.setStatus(ParserUtil.parseStatus(map.getValue(PREFIX_ORDER_STATUS).get()));
        }
        return new EditOrderCommand(index, editOrderDescriptor);
    }

}
