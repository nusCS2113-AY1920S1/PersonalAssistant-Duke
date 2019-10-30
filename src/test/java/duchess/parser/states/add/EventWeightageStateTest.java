package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddEventCommand;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.DefaultState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventWeightageStateTest {
    private final Parser parser;
    private final EventWeightageState eventWeightageState;

    EventWeightageStateTest() throws DuchessException {
        this.parser = new Parser();
        this.eventWeightageState = new EventWeightageState(
                this.parser, "test", Util.parseDateTime("12/12/2019 1200"),
                Util.parseDateTime("12/12/2019 1400"), "CS1231"
        );
    }

    @Test
    void parse() throws DuchessException {
        parser.setParserState(eventWeightageState);
        assertTrue(parser.parse("nil") instanceof AddEventCommand);
        assertTrue(parser.getParserState() instanceof DefaultState);

        parser.setParserState(eventWeightageState);
        assertTrue(parser.parse("40") instanceof AddEventCommand);
        assertTrue(parser.getParserState() instanceof DefaultState);

        parser.setParserState(eventWeightageState);
        assertTrue(parser.parse("101") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventWeightageState);

        parser.setParserState(eventWeightageState);
        assertTrue(parser.parse("100") instanceof AddEventCommand);
        assertTrue(parser.getParserState() instanceof DefaultState);

        parser.setParserState(eventWeightageState);
        assertTrue(parser.parse("0") instanceof AddEventCommand);
        assertTrue(parser.getParserState() instanceof DefaultState);

        parser.setParserState(eventWeightageState);
        assertTrue(parser.parse("-1") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventWeightageState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(eventWeightageState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("test")
        ) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventWeightageState);
    }
}
