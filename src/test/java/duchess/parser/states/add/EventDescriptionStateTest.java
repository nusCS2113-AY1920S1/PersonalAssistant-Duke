package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EventDescriptionStateTest {
    private final Parser parser = new Parser();
    private final EventDescriptionState eventDescriptionState =
            new EventDescriptionState(parser);

    @Test
    void parse() throws DuchessException {
        parser.setParserState(eventDescriptionState);
        assertTrue(parser.parse("some description") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventFromState);

        parser.setParserState(eventDescriptionState);
        assertTrue(parser.parse("") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventDescriptionState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(eventDescriptionState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("event /name test")) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventFromState);

        parser.setParserState(eventDescriptionState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("event /rubbish test")) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventDescriptionState);
    }
}