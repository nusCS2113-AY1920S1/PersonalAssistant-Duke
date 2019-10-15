package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ModuleNameStateTest {
    private final Parser parser = new Parser();
    private final ModuleNameState moduleNameState = new ModuleNameState(parser);

    @Test
    void parse() throws DuchessException {
        parser.setParserState(moduleNameState);
        assertTrue(parser.parse("Module Name") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof ModuleCodeState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(moduleNameState);
        assertTrue(
                parser.continueParsing(
                        Util.parameterizeWithoutCommand("/name Test")) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof ModuleCodeState);
    }
}