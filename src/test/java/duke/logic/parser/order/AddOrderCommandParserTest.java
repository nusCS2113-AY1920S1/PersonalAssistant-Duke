package duke.logic.parser.order;

import duke.logic.command.exceptions.CommandException;
import duke.logic.command.order.AddOrderCommand;
import duke.logic.parser.exceptions.ParseException;
import duke.model.order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

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
    public void parse_validStatus_success() {
        OrderModelStub modelStub = new OrderModelStub();
        AddOrderCommand command = parser.parse(USER_INPUT_WITH_STATUS_VALID);
        try {
            command.execute(modelStub);
            Assertions.assertEquals(Order.Status.ACTIVE, modelStub.getOrder(0).getStatus());
        } catch (CommandException e) {
            fail();
        }
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
