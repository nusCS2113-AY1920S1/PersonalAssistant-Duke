package duke.logic.parser.order;

import duke.logic.command.exceptions.CommandException;
import duke.logic.command.order.AddOrderCommand;
import duke.logic.parser.exceptions.ParseException;
import duke.model.order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class AddOrderCommandParserTest {
    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void addOrder_activeStatus_success() {
        OrderModelStub modelStub = new OrderModelStub();
        AddOrderCommand command = parser.parse("-status active");
        try {
            command.execute(modelStub);
            Assertions.assertEquals(Order.Status.ACTIVE, modelStub.getOrder(0).getStatus());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void addOrder_invalidStatus_failure() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse("-status RANDOM")
        );
    }

    @Test
    public void addOrder_invalidDate_failure() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse("-by my")
        );
    }

    @Test
    public void addOrder_noArgument_success() {
        Assertions.assertAll(() -> parser.parse(""));
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
    public void addOrder_invalid_quantity_failure() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse("-item a, 50000000")
        );

        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse("-item a, -10")
        );
    }

    @Test
    public void addOrder_quantityWithinValidRange_success() {
        Assertions.assertAll(() -> parser.parse("-item a, 100"));
    }

    @Test
    public void addOrder_boundaryQuantity_success() {
        Assertions.assertAll(() -> parser.parse("-item a, 0"));
        Assertions.assertAll(() -> parser.parse("-item a, 50000.0"));
        Assertions.assertAll(() -> parser.parse("-item a, 49999.9"));
        Assertions.assertAll(() -> parser.parse("-item a, 0.1"));
    }

    @Test
    public void addOrder_boundaryQuantity_outOfRange() {
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-item a, -0.01"));
        Assertions.assertThrows(ParseException.class, () -> parser.parse("-item a, 50000.1"));
    }

}
