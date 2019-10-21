package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EventToStateTest {
    private final Parser parser;
    private final EventToState eventToState;

    public EventToStateTest() throws DuchessException {
        this.parser = new Parser();
        this.eventToState = new EventToState(parser, "test", Util.parseDateTime("12/12/2019 1200"));
    }

    @Test
    void parse() throws DuchessException {
        parser.setParserState(eventToState);
        assertTrue(parser.parse("12/12/2019 1200") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventModuleState);

        parser.setParserState(eventToState);
        assertTrue(parser.parse("12/13/2019 1200") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventToState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(eventToState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("test /to 12/12/2019 1200")
        ) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventModuleState);
    }
}