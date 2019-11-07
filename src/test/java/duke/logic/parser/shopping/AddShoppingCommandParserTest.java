package duke.logic.parser.shopping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddShoppingCommandParserTest {

    private AddShoppingCommandParser parser = new AddShoppingCommandParser();

    @Test
    public void parseQuantity() {
        Assertions.assertAll(() -> parser.parse("-name egg -qty 1000"));
        Assertions.assertAll(() -> parser.parse("-name bread -qty 50000"));
        Assertions.assertAll(() -> parser.parse("-name fish -qty 0"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parse("-name egg -qty 50001"));
    }
}