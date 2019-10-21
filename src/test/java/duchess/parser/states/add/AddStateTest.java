package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AddStateTest {
    private final Parser parser = new Parser();
    private final AddState addState = new AddState(parser);

    @Test
    void parse() throws DuchessException {
        parser.setParserState(addState);

        assertTrue(
                addState.parse("rubbish") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof AddState);

        assertTrue(
                addState.parse("module") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof ModuleNameState);

        assertTrue(
                addState.parse("event") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof EventDescriptionState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(addState);

        assertTrue(
                addState.continueParsing(Util.parameterizeWithoutCommand("nonsense")) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof AddState);

        assertTrue(
                addState.continueParsing(Util.parameterizeWithoutCommand("todo")) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof AddState);

        assertTrue(
                addState.continueParsing(Util.parameterizeWithoutCommand("module")) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof ModuleNameState);
    }
}