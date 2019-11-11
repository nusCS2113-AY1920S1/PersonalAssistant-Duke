package cube.logic.parser;

import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DeleteCommandParserTest {

    @Test
    public void execute_not_enough_parameter_only_command() {
        String[] inputs = {"delete"};
        try {
            new DeleteCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

    @Test
    public void execute_not_enough_parameter_not_all() {
        String[] inputs = {"delete","test"};
        try {
            new DeleteCommandParser().parse(inputs);
            fail("Fail to detect not enough parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.NOT_ENOUGH_PARAMETER, e.getMessage());
        }
    }

    @Test
    public void execute_invalid_parameter() {
        String[] inputs = {"delete", "-x", "field"};
        try {
            new DeleteCommandParser().parse(inputs);
            fail("Fail to detect invalid parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.INVALID_PARAMETER, e.getMessage());
        }

    }

    @Test
    public void execute_repetitive_parameter() {
        String[] inputs = {"delete", "-i", "1", "-i", "2"};
        try {
            new DeleteCommandParser().parse(inputs);
            fail("Fail to detect repetitive parameter");
        } catch (ParserException e) {
            assertEquals(ParserErrorMessage.REPETITIVE_PARAMETER, e.getMessage());
        }
    }

}