package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddGradeCommand;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import duchess.parser.states.DefaultState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GradeModuleStateTest {
    private final Parser parser;
    private final GradeModuleState gradeModuleState;

    GradeModuleStateTest() {
        this.parser = new Parser();
        this.gradeModuleState = new GradeModuleState(
                this.parser, "test", 15, 20, 10
        );
    }

    @Test
    void parse() throws DuchessException {
        parser.setParserState(gradeModuleState);
        assertTrue(parser.parse("nil") instanceof AddGradeCommand);
        assertTrue(parser.getParserState() instanceof DefaultState);

        parser.setParserState(gradeModuleState);
        assertTrue(parser.parse("CS1231") instanceof AddGradeCommand);
        assertTrue(parser.getParserState() instanceof DefaultState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(gradeModuleState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("test")
        ) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeModuleState);
    }
}
