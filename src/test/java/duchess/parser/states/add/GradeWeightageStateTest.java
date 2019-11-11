package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GradeWeightageStateTest {
    private final Parser parser;
    private final GradeWeightageState gradeWeightageState;

    GradeWeightageStateTest() {
        this.parser = new Parser();
        this.gradeWeightageState = new GradeWeightageState(parser, "test", 15, 30);
    }

    @Test
    void parse() throws DuchessException {
        parser.setParserState(gradeWeightageState);
        assertTrue(parser.parse("40") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeModuleState);

        parser.setParserState(gradeWeightageState);
        assertTrue(parser.parse("101") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeWeightageState);

        parser.setParserState(gradeWeightageState);
        assertTrue(parser.parse("100") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeModuleState);

        parser.setParserState(gradeWeightageState);
        assertTrue(parser.parse("0") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeModuleState);

        parser.setParserState(gradeWeightageState);
        assertTrue(parser.parse("-1") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeWeightageState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(gradeWeightageState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("test /weightage 20")
        ) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeModuleState);
    }
}
