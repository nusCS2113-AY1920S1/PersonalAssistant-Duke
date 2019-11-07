package duke.logic.parser.order;

import duke.commons.core.Message;
import duke.logic.command.order.AddOrderCommand;
import duke.logic.command.order.OrderDescriptor;
import duke.logic.parser.CommandParserTestUtil;
import duke.model.commons.Quantity;
import duke.model.order.Customer;
import duke.model.order.Remark;
import duke.testutil.TypicalOrderDescriptors;
import org.junit.jupiter.api.Test;

import static duke.logic.command.order.OrderCommandTestUtil.DESC_CONTACT_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_CONTACT_RORY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_DATE_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_DATE_RORY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_CONTACT_BLANK;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_CONTACT_TOO_LONG;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_DATE_NOT_EXIST;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_DATE_WRONG_FORMAT;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_NAME_BLANK;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_NAME_TOO_LONG;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_QUANTITY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_QUANTITY_BOUNDARY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_REMARKS_TOO_LONG;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_INVALID_STATUS;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_ITEMS_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_NAME_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_NAME_RORY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_RMK_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_RMK_RORY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_STATUS_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_STATUS_RORY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_TOTAL_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_TOTAL_RORY;
import static duke.logic.command.order.OrderCommandTestUtil.PREAMBLE_NON_EMPTY;
import static duke.logic.command.order.OrderCommandTestUtil.PREAMBLE_WHITESPACE;

public class AddOrderCommandParserTest {
    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        OrderDescriptor expectedDescriptor = TypicalOrderDescriptors.ORDER_DESCRIPTOR_ALICE;

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESC_NAME_ALICE + DESC_CONTACT_ALICE + DESC_RMK_ALICE + DESC_DATE_ALICE + DESC_ITEMS_ALICE
                + DESC_TOTAL_ALICE + DESC_STATUS_ALICE,
            new AddOrderCommand(expectedDescriptor));

        // change order of params
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESC_CONTACT_ALICE + DESC_NAME_ALICE + DESC_RMK_ALICE + DESC_DATE_ALICE + DESC_ITEMS_ALICE
                + DESC_TOTAL_ALICE + DESC_STATUS_ALICE,
            new AddOrderCommand(expectedDescriptor));

        // multiple names - last accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESC_CONTACT_ALICE + DESC_NAME_RORY + DESC_NAME_ALICE + DESC_RMK_ALICE + DESC_DATE_ALICE + DESC_ITEMS_ALICE
                + DESC_TOTAL_ALICE + DESC_STATUS_ALICE,
            new AddOrderCommand(expectedDescriptor));

        // multiple contact - last accepted
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESC_CONTACT_RORY + DESC_CONTACT_ALICE + DESC_NAME_RORY + DESC_NAME_ALICE + DESC_RMK_ALICE + DESC_DATE_ALICE + DESC_ITEMS_ALICE
                + DESC_TOTAL_ALICE + DESC_STATUS_ALICE,
            new AddOrderCommand(expectedDescriptor));
    }

    @Test
    public void parse_missingFields_success() {
        //Missing one field
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESC_CONTACT_RORY + DESC_NAME_RORY + DESC_RMK_RORY + DESC_DATE_RORY
                + DESC_TOTAL_RORY + DESC_STATUS_RORY,
            new AddOrderCommand(TypicalOrderDescriptors.ORDER_DESCRIPTOR_RORY));

        //Missing multiple fields
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESC_CONTACT_ALICE + DESC_NAME_ALICE + DESC_RMK_ALICE
                + DESC_TOTAL_ALICE + DESC_STATUS_ALICE,
            new AddOrderCommand(TypicalOrderDescriptors.ORDER_DESCRIPTOR_ALICE_MISSING_FIELDS));

        //Missing all fields
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE,
            new AddOrderCommand(TypicalOrderDescriptors.ORDER_DESCRIPTOR_MISSING_ALL_FIELDS));
    }

    @Test
    public void parse_invalidValue_failure() {
        //Non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, PREAMBLE_NON_EMPTY, Message.MESSAGE_INVALID_COMMAND_FORMAT);

        //Invalid name
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_NAME_BLANK, Customer.MESSAGE_CONSTRAINTS); //blank
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_NAME_TOO_LONG, Customer.MESSAGE_CONSTRAINTS); //too long

        //Invalid contact
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_CONTACT_BLANK, Customer.MESSAGE_CONSTRAINTS); //blank
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_CONTACT_TOO_LONG, Customer.MESSAGE_CONSTRAINTS); //too long

        //Invalid remarks
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_REMARKS_TOO_LONG, Remark.MESSAGE_CONSTRAINTS); //too long

        //Invalid quantity
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_QUANTITY, Quantity.MESSAGE_LIMIT_QUANTITY); //too big
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_QUANTITY_BOUNDARY, Quantity.MESSAGE_LIMIT_QUANTITY); //boundary

        //Invalid status
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_STATUS, Message.MESSAGE_INVALID_STATUS); //unknown status

        //Invalid dates
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_DATE_NOT_EXIST, Message.MESSAGE_INVALID_DATE); //date does not exist. Such as 32nd day of a month
        CommandParserTestUtil.assertParseFailure(parser, DESC_INVALID_DATE_WRONG_FORMAT, Message.MESSAGE_INVALID_DATE); //date is not given in correct format
    }
}
