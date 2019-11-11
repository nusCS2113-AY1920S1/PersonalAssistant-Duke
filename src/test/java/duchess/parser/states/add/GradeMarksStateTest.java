package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GradeMarksStateTest {
    private final Parser parser = new Parser();
    private final GradeMarksState gradeMarksState = new GradeMarksState(parser, "test");

    @Test
    void parse() throws DuchessException {
        parser.setParserState(gradeMarksState);
        assertTrue(parser.parse("20/30") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeWeightageState);

        parser.setParserState(gradeMarksState);
        assertTrue(parser.parse("30/20") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeMarksState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(gradeMarksState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("test /marks 15.5/20")
        ) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeWeightageState);
    }
}
