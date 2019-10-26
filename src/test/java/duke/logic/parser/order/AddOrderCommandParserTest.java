package duke.logic.parser.order;

import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddOrderCommandParserTest {
    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void addOrder_invalidStatus_failure() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse("-status INVALID")
        );
    }

    @Test
    public void addOrder_invalidDate_failure() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse("-by my")
        );
    }

    /**
     * Invalid equivalence partitions for quantity of item:
     * - negative quantity and quantity more than 5000
     * Valid equivalence partitions for quantity:
     * - quantities in (0, 5000)
     * Boundary values:
     * - 0: valid input
     * - 5000: valid input
     * <p>
     * The four test cases below tests EP at a time.
     */

    @Test
    public void addOrder_invalid_quantity_failure() {
        Assertions.assertThrows(
            ParseException.class,
            () -> parser.parse("-item a, 10000")
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
    public void addOrder_zeroQuantity_success() {
        Assertions.assertAll(() -> parser.parse("-item a, 0"));
    }

    @Test
    public void addOrder_maxQuantity_success() {
        Assertions.assertAll(() -> parser.parse("-item a, 5000.0"));
    }



    @Test
    public void addOrder_noArgument_success() {
        Assertions.assertAll(() -> parser.parse(""));
    }
}
