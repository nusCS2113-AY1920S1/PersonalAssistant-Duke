package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EventFromStateTest {
    private final Parser parser = new Parser();
    private final EventFromState eventFromState = new EventFromState(parser, "test");

    @Test
    void parse() throws DuchessException {
        parser.setParserState(eventFromState);
        assertTrue(parser.parse("12/12/2019 1200") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventToState);

        parser.setParserState(eventFromState);
        assertTrue(parser.parse("12/13/2019 1200") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventFromState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(eventFromState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("test /from 12/12/2019 1200")
        ) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventToState);
    }
}