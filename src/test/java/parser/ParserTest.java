package parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
        public void verifyCheckValue() {
        assertEquals(1, Parser.handleCommand("bye", "bye"));
    }
}
