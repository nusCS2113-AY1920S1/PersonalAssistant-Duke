package duke.logic.parser.order;

import duke.logic.command.order.AddOrderCommand;
import duke.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddOrderCommandParserTest {
    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void addOrder_invalidStatus_failure() {
        Assertions.assertThrows(
            ParseException.class,
            () -> {
                parser.parse("-status INVALID");
            }
        );
    }

    @Test
    public void addOrder_invalidDate_failure() {
        Assertions.assertThrows(
            ParseException.class,
            () -> {
                parser.parse("-by my");
            }
        );
    }

    @Test
    public void addOrder_noArgument_success() {
        AddOrderCommand command = parser.parse("");
    }
}
