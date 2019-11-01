package parser;

import duke.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
        public void verifyCheckValue() {
        assertEquals(1, Parser.handleCommand("bye", "bye"));
        assertEquals(0, Parser.handleCommand("fixx", "fixx 2"));
        assertEquals(0, Parser.handleCommand("event", "event add /at tenta"));
    }
}
