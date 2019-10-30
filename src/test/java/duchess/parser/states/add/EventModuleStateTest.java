package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddEventCommand;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.DefaultState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EventModuleStateTest {
    private final Parser parser;
    private final EventModuleState eventModuleState;

    EventModuleStateTest() throws DuchessException {
        this.parser = new Parser();
        this.eventModuleState = new EventModuleState(
                this.parser, "test", Util.parseDateTime("12/12/2019 1200"), Util.parseDateTime("12/12/2019 1400")
        );
    }

    @Test
    void parse() throws DuchessException {
        parser.setParserState(eventModuleState);
        assertTrue(parser.parse("nil") instanceof AddEventCommand);
        assertTrue(parser.getParserState() instanceof DefaultState);

        parser.setParserState(eventModuleState);
        assertTrue(parser.parse("CS1231") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventWeightageState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(eventModuleState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("test")
        ) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventModuleState);
    }
}