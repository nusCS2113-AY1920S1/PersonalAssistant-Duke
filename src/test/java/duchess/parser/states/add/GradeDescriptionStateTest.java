package duchess.parser.states.add;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.DisplayCommand;
import duchess.parser.Parser;
import duchess.parser.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GradeDescriptionStateTest {
    private final Parser parser = new Parser();
    private final GradeDescriptionState gradeDescriptionState =
            new GradeDescriptionState(parser);

    @Test
    void parse() throws DuchessException {
        parser.setParserState(gradeDescriptionState);
        assertTrue(parser.parse("test") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeMarksState);

        parser.setParserState(gradeDescriptionState);
        assertTrue(parser.parse("") instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeDescriptionState);
    }

    @Test
    void continueParsing() throws DuchessException {
        parser.setParserState(gradeDescriptionState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("grade /name test")) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeMarksState);

        parser.setParserState(gradeDescriptionState);
        assertTrue(parser.continueParsing(
                Util.parameterizeWithoutCommand("grade /rubbish test")) instanceof DisplayCommand);
        assertTrue(parser.getParserState() instanceof GradeDescriptionState);
    }
}
