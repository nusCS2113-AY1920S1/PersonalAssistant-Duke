package duke.logic.parser.order;

import duke.logic.command.order.AddOrderCommand;
import duke.logic.command.order.OrderDescriptor;
import duke.logic.parser.CommandParserTestUtil;
import duke.logic.parser.exceptions.ParseException;
import duke.model.order.Order;
import duke.testutil.OrderDescriptorBuilder;
import duke.testutil.TypicalOrderDescriptors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static duke.logic.command.order.OrderCommandTestUtil.DESC_CONTACT_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_CONTACT_RORY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_DATE_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_ITEMS_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_NAME_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_NAME_RORY;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_RMK_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_STATUS_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.DESC_TOTAL_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.PREAMBLE_WHITESPACE;
import static duke.logic.command.order.OrderCommandTestUtil.VALID_CONTACT_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.VALID_DELIVERY_DATE_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.VALID_NAME_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.VALID_REMARKS_ALICE;
import static duke.logic.command.order.OrderCommandTestUtil.VALID_TOTAL_ALICE;

public class AddOrderCommandParserTest {

    private static final String USER_INPUT_WITH_STATUS_VALID = "-status active";
    private static final String USER_INPUT_WITH_STATUS_INVALID = "-status random";
    private static final String USER_INPUT_WITH_DATE_INVALID = "-by 32/10/1999";
    private static final String USER_INPUT_EMPTY = "";
    private static final String USER_INPUT_WITH_ITEM = "-item apple, %s";

    private static final Double ITEM_QUANTITY_OUT_OF_RANGE = 50000000.0;
    private static final Double ITEM_QUANTITY_NEGATIVE = -10.0;
    private static final Double ITEM_QUANTITY_VALID = 2000.0;
    private static final Double ITEM_QUANTITY_LOWER_BOUNDARY_VALID = 0.0;
    private static final Double ITEM_QUANTITY_LOWER_BOUNDARY_POSITIVE_VALID = 0.1;
    private static final Double ITEM_QUANTITY_LOWER_BOUNDARY_NEGATIVE_INVALID = -0.1;
    private static final Double ITEM_QUANTITY_UPPER_BOUNDARY_VALID = 50000.0;
    private static final Double ITEM_QUANTITY_UPPER_BOUNDARY_WITHIN_RANGE_VALID = 49999.9;
    private static final Double ITEM_QUANTITY_UPPER_BOUNDARY_INVALID = 50000.1;

    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        OrderDescriptor expectedDescriptor = TypicalOrderDescriptors.ORDER_DESCRIPTOR_A;

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
        OrderDescriptor expectedDescriptorMissingOneField = new OrderDescriptorBuilder().withName(VALID_NAME_ALICE)
            .withContact(VALID_CONTACT_ALICE)
            .withRemarks(VALID_REMARKS_ALICE)
            .withStatus(Order.Status.ACTIVE)
            .withTotal(VALID_TOTAL_ALICE)
            .withDeliveryDate(VALID_DELIVERY_DATE_ALICE)
            .build();
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESC_CONTACT_ALICE + DESC_NAME_ALICE + DESC_RMK_ALICE + DESC_DATE_ALICE
                + DESC_TOTAL_ALICE + DESC_STATUS_ALICE,
            new AddOrderCommand(expectedDescriptorMissingOneField));

        //Missing multiple fields
        OrderDescriptor expectedDescriptorMissingMultipleFields = new OrderDescriptorBuilder().withName(VALID_NAME_ALICE)
            .withContact(VALID_CONTACT_ALICE)
            .withRemarks(VALID_REMARKS_ALICE)
            .withStatus(Order.Status.ACTIVE)
            .withTotal(VALID_TOTAL_ALICE)
            .build();
        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + DESC_CONTACT_ALICE + DESC_NAME_ALICE + DESC_RMK_ALICE
                + DESC_TOTAL_ALICE + DESC_STATUS_ALICE,
            new AddOrderCommand(expectedDescriptorMissingMultipleFields));

        //Missing all fields
        OrderDescriptor expectedDescriptorMissingAllFields = new OrderDescriptorBuilder().build();

        OrderDescriptor actual = parser.parse(PREAMBLE_WHITESPACE).getAddOrderDescriptor();
        System.out.println(actual);
        System.out.println(expectedDescriptorMissingAllFields);

        CommandParserTestUtil.assertParseSuccess(parser,
            PREAMBLE_WHITESPACE,
            new AddOrderCommand(expectedDescriptorMissingAllFields));
    }

    @Test
    public void parse_invalidValue_failure() {

    }
    @Test
    public void parse_invalidStatus_throwsParseException() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse(USER_INPUT_WITH_STATUS_INVALID)
        );
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse(USER_INPUT_WITH_DATE_INVALID)
        );
    }

    @Test
    public void parse_noArgument_success() {
        Assertions.assertAll(() -> parser.parse(USER_INPUT_EMPTY));
    }

    /**
     * Invalid equivalence partition for quantity of item:
     * - negative quantity and quantity more than 50000
     * Valid equivalence partition for quantity:
     * - quantities in (0, 50000)
     * Boundary values:
     * - 0, 50000, 0.1, 49999.9 (valid input)
     * - 50000.1, -0.01 (invalid input)
     * <p>
     * The four test cases below tests EP one at a time.
     */

    @Test
    public void parse_invalid_quantity_throwsParseException() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_OUT_OF_RANGE))
        );

        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_NEGATIVE))
        );
    }

    @Test
    public void parse_quantityWithinValidRange_success() {
        Assertions.assertAll(() -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_VALID)));
    }

    @Test
    public void parse_boundaryQuantity_success() {
        Assertions.assertAll(() -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_LOWER_BOUNDARY_VALID)));
        Assertions.assertAll(() -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_UPPER_BOUNDARY_VALID)));
        Assertions.assertAll(() -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_UPPER_BOUNDARY_WITHIN_RANGE_VALID)));
        Assertions.assertAll(() -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_LOWER_BOUNDARY_POSITIVE_VALID)));
    }

    @Test
    public void parse_boundaryQuantity_throwsParseException() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_LOWER_BOUNDARY_NEGATIVE_INVALID)));
        Assertions.assertThrows(ParseException.class, () -> parser.parse(String.format(USER_INPUT_WITH_ITEM, ITEM_QUANTITY_UPPER_BOUNDARY_INVALID)));
    }

}
