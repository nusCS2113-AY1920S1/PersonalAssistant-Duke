package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddModuleCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ModuleCodeStateTest {
    private final Parser parser = new Parser();
    private final ModuleCodeState moduleCodeState = new ModuleCodeState(parser, "Discrete Math");

    @Test
    void parse() throws DuchessException {
        parser.setParserState(moduleCodeState);
        assertTrue(parser.parse("CS1231") instanceof AddModuleCommand);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(moduleCodeState);
        assertTrue(
                parser.continueParsing(
                        Util.parameterizeWithoutCommand("/code CS1231")) instanceof AddModuleCommand);
    }
}